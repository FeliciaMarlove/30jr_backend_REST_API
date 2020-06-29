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
    public TaskService(TaskRepository taskRepository, TaskPathRepository taskPathRepository) {
        this.taskRepository = taskRepository;
        this.taskPathRepository = taskPathRepository;
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
        return null;
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
        return null;
    }

    /**
     * Désactive une tâche
     * Vérifie que la tâche ne se trouve pas dans un parcours
     * @param id Integer l'ID de la tâche à désactiver
     * @return DTOEntity la tâche désactivée (DTO GET) ou un Message(String, booléen) en cas d'échec
     */
    @Override
    public DTOEntity delete(Integer id) {
       return null;
    }

    /**
     * Active une tâche
     * @param id Integer l'ID de la tâche à activer
     * @return DTOEntity la tâche activée (DTO GET) ou un Message(String, booléen) en cas d'échec
     */
    public DTOEntity activate(Integer id) {
        return null;
    }
}
