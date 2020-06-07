package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.models.TaskPath;
import be.iramps.florencemary._30jd_back.repositories.TaskPathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Couche logique tâche
 */
@Service
public class TaskService implements CRUDService {
    private TaskRepository taskRepository;
    private TaskPathRepository taskPathRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //Private methods
    private boolean taskIsUsed(Integer id) {
        for (TaskPath tp: taskPathRepository.findAll()) {
            if (tp.getTask().getTaskId().equals(id)) return true;
        }
        return false;
    }

    /*
   CRUD OPERATIONS
    */

    /**
     * Retourne une tâche sous forme de DTO GET
     * @param id Integer l'ID de la tâche
     * @return DTOEntity la tâche
     */
    @Override
    public DTOEntity read(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.isPresent() ?
                new DtoUtils().convertToDto(optionalTask.get(), new TaskGet()) : new Message("Le défi n'a pas été trouvé.", false);
    }

    /**
     * Retourne la liste des tâches en base de données sous forme de DTOs
     * @return List DTOEntity la liste des tâches
     */
    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(Task t: taskRepository.findAll()) list.add(new DtoUtils().convertToDto(t, new TaskGet()));
        return list;
    }

    /**
     * Crée une tâche en base de données
     * Gère les erreurs en cas de données manquantes ou de doublon
     * @param dtoEntity DTOEntity la tâche sous forme de DTO POST
     * @return DTOEntity la tâche créée (DTO GET) en cas de réussite ou un Message(String, booléen) en cas d'échec
     */
    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        if (taskRepository.findByTaskName(((TaskPost)dtoEntity).getTaskName()) == null) {
            try {
                Task t = (Task)new DtoUtils().convertToEntity(new Task(), dtoEntity);
                t.setTaskActive(true);
                taskRepository.save(t);
                return new DtoUtils().convertToDto(t, new TaskGet());
            } catch (Exception e) {
                return new Message("Informations manquantes, l'enregistrement a échoué.", false);
            }
        }
        return new Message("Le nom du défi existe déjà, l'enregistrement a échoué.", false);
    }

    /**
     * Mettre à jour une tâche
     * Gère les erreurs en cas de données manquantes ou de doublon
     * @param id Integer l'ID de la tâche à mettre à jour
     * @param dtoEntity DTOEntity la tâche à mettre à jour sous forme de DTO POST
     * @return DTOEntity la tâche mise à jour (DTO GET) en cas de réussite ou un Message(String, booléen) en cas d'échec
     */
    @Override
    public DTOEntity update(Integer id, DTOEntity dtoEntity) {
        if(taskRepository.existsById(id)) {
            Task t = taskRepository.findById(id).get();
            try {
                t.setTaskName(((TaskPost)dtoEntity).getTaskName());
                t.setTaskShortDescription(((TaskPost)dtoEntity).getTaskShortDescription());
                t.setTaskLongDescription(((TaskPost)dtoEntity).getTaskLongDescription());
                taskRepository.save(t);
                return new DtoUtils().convertToDto(t, new TaskGet());
            } catch (Exception e) {
                e.printStackTrace();
                return new Message("Informations manquantes ou doublon, l'enregistrement a échoué.", false);
            }
        }
        return new Message("Le défi avec l'ID " + id + " n'a pas été trouvé, l'enregistrement a échoué.", false);
    }

    /**
     * Désactive une tâche
     * Vérifie que la tâche ne se trouve pas dans un parcours
     * @param id Integer l'ID de la tâche à désactiver
     * @return DTOEntity la tâche désactivée (DTO GET) ou un Message(String, booléen) en cas d'échec
     */
    @Override
    public DTOEntity delete(Integer id) {
        Optional<Task> optTask = taskRepository.findById(id);
        if(optTask.isPresent() && !taskIsUsed(id)) {
            optTask.get().setTaskActive(false);
            taskRepository.save(optTask.get());
            return new DtoUtils().convertToDto(optTask.get(), new TaskGet());
        }
        return new Message("La défi avec l'ID " + id + " n'a pas été trouvé ou le défi est utilisé dans un parcours.", false);
    }

    /**
     * Active une tâche
     * @param id Integer l'ID de la tâche à activer
     * @return DTOEntity la tâche activée (DTO GET) ou un Message(String, booléen) en cas d'échec
     */
    public DTOEntity activate(Integer id) {
        Optional<Task> optTask = taskRepository.findById(id);
        if(optTask.isPresent()) {
            optTask.get().setTaskActive(true);
            taskRepository.save(optTask.get());
            return new DtoUtils().convertToDto(optTask.get(), new TaskGet());
        }
        return new Message("La défi avec l'ID " + id + " n'a pas été trouvé.", false);
    }
}
