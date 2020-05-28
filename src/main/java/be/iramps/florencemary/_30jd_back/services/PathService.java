package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.models.TaskPath;
import be.iramps.florencemary._30jd_back.models.UserPath;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskPathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PathService implements CRUDService {
    private PathRepository pathRepository;
    private UserPathRepository userPathRepository;
    private TaskRepository taskRepository;
    private TaskPathRepository taskPathRepository;

    @Autowired
    public PathService(PathRepository pathRepository, UserPathRepository userPathRepository, TaskRepository taskRepository, TaskPathRepository taskPathRepository) {
        this.pathRepository = pathRepository;
        this.userPathRepository = userPathRepository;
        this.taskRepository = taskRepository;
        this.taskPathRepository = taskPathRepository;
    }

    /*
    BUSINESS LAYER
     */

    @Transactional
    public Message addTask(Integer pathId, Integer taskId, Integer index) {
        int size = listTasks(pathId).size();
        Optional<Path> optionalPath = pathRepository.findById(pathId);
        if (optionalPath.isPresent() && size < 30) {
            Path p = optionalPath.get();
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent() && optionalTask.get().isTaskActive() && !isAlreadyInPath(p, optionalTask.get())) {
                Task task = optionalTask.get();
                // index "-99" means "no index was specified"
                if (index == -99) {
                    if (taskPathRepository.findByPath(p).isEmpty()) {
                        TaskPath tp = new TaskPath(task, p, 0);
                        taskPathRepository.save(tp);
                        return new Message("Défi ajouté", true);
                    }
                    List<TaskPath> all = taskPathRepository.findByPathOrderByPosition(p);
                    Integer higherPosition = all.get(all.size() - 1).getPosition();
                    TaskPath tp = new TaskPath(task, p, higherPosition + 1);
                    taskPathRepository.save(tp);
                    return new Message("Défi ajouté", true);
                    // if index was specified
                } else {
                    if (listTasks(pathId).isEmpty() && index == 0) {
                        TaskPath tp = new TaskPath(task, p, 0);
                        taskPathRepository.save(tp);
                        return new Message("Défi ajouté", true);
                    }
                    if (index > 0 && index <= size) {
                        List<TaskPath> all = taskPathRepository.findByPath(p).get();
                        for (TaskPath each : all) {
                            if (each.getPosition() >= index) {
                                each.setPosition(each.getPosition() + 1);
                            }
                        }
                        TaskPath tp = new TaskPath(task, p, index);
                        taskPathRepository.save(tp);
                        return new Message("Défi ajouté", true);
                    }
                    return new Message("L'index n'est pas valide", false);
                }
            }
            return new Message("Le défi " + optionalTask.get().getTaskName() + " est déjà dans la liste", false);
        }
        return new Message("Le parcours n'a pas été trouvé ou est complet", false);
    }

    private boolean isAlreadyInPath(Path path, Task task) {
        for (DTOEntity t : listTasks(path.getPathId())) {
            if (((TaskGet) (t)).getTaskId().equals(task.getTaskId())) return true;
        }
        return false;
    }

    @Transactional
    public Message removeTask(Integer pathId, Integer taskId) {
        Optional<Path> optionalPath = pathRepository.findById(pathId);
        if (optionalPath.isPresent()) {
            Path p = optionalPath.get();
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent() && listTasks(pathId).contains(optionalTask.get())) {
                Task task = optionalTask.get();
                TaskPath toDelete = taskPathRepository.findByPathAndTask(p, task).get();
                taskPathRepository.delete(toDelete);
                return new Message("Défi supprimé de la liste", true);
            }
            return new Message("Le défi " + taskId + " n'est pas dans la liste", false);
        }
        return new Message("Le parcours " + pathId + " n'a pas été trouvé", false);
    }

    /*
    CRUD OPERATIONS
     */

    public List<DTOEntity> listTasks(Integer id) {
        List<DTOEntity> list = new ArrayList<>();
        List<TaskPath> tpList = new ArrayList<>();
        Optional<Path> optionalPath = pathRepository.findById(id);
        if (optionalPath.isPresent()) {
            for (TaskPath tp : taskPathRepository.findAll()) {
                if (tp.getPath().getPathId().equals(id)) {
                    tpList.add(tp);
                }
            }
        }
        Collections.sort(tpList);
        for (TaskPath tp : tpList) {
            list.add(new DtoUtils().convertToDto(tp.getTask(), new TaskGet()));
        }
        return list;
    }

    @Override
    public DTOEntity read(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        return optPath.isPresent() ?
                new DtoUtils().convertToDto(optPath.get(), new PathGet()) : new Message("Le parcours n'a pas été trouvé", false);
    }

    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for (Path p : pathRepository.findAll()) list.add(new DtoUtils().convertToDto(p, new PathGet()));
        return list;
    }

    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        if (pathRepository.findByPathName(((PathPost) dtoEntity).getPathName()) == null) {
            Path p = (Path) new DtoUtils().convertToEntity(new Path(), dtoEntity);
            try {
                pathRepository.save(p);
                return new DtoUtils().convertToDto(p, new PathGet());
            } catch (Exception e) {
                return new Message("Informations manquantes, l'enregistrement a échoué.", false);
            }
        }
        return new Message("Le nom du parcours existe déjà, l'enregistrement a échoué.", false);
    }

    @Override
    public DTOEntity update(Integer id, DTOEntity dtoEntity) {
        if (pathRepository.existsById(id)) {
            Path p = pathRepository.findById(id).get();
            try {
                p.setPathName(((PathPost) dtoEntity).getPathName());
                p.setPathShortDescription(((PathPost) dtoEntity).getPathShortDescription());
                p.setPathLongDescription(((PathPost) dtoEntity).getPathLongDescription());
                pathRepository.save(p);
                return new DtoUtils().convertToDto(p, new PathGet());
            } catch (Exception e) {
                e.printStackTrace();
                return new Message("Informations manquantes ou doublon, l'enregistrement a échoué.", false);
            }
        }
        return new Message("Le parcours avec l'ID " + id + " n'a pas été trouvé, l'enregistrement a échoué.", false);
    }

    @Override
    public DTOEntity delete(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        if (optPath.isPresent() && !pathIsUsed(id)) {
            optPath.get().setPathActive(false);
            pathRepository.save(optPath.get());
            return new DtoUtils().convertToDto(optPath.get(), new PathGet());
        }
        return new Message("La parcours avec l'ID " + id + " n'a pas été trouvé.", false);
    }

    public DTOEntity activate(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        if (optPath.isPresent()) {
            optPath.get().setPathActive(true);
            pathRepository.save(optPath.get());
            return new DtoUtils().convertToDto(optPath.get(), new PathGet());
        }
        return new Message("La parcours avec l'ID " + id + " n'a pas été trouvé.", false);
    }

    private boolean pathIsUsed(Integer id) {
        for (UserPath up : userPathRepository.findAll()) {
            if ((up.getPath().getPathId().equals(id) && up.isOngoing())) return true;
        }
        return false;
    }
}
