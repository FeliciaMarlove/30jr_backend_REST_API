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

/**
 * Couche logique parcours
 */
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

    /**
     * Retourne la liste des tâches d'un parcours sous forme de DTO GET
     * Trie la liste sur base des positions dans la relation tâche-parcours
     * @param id Integer l'ID du parcours
     * @return List DTOEntity la liste
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

    // private methods

    private boolean isAlreadyInPath(Path path, Task task) {
        for (DTOEntity t : listTasks(path.getPathId())) {
            if (((TaskGet) (t)).getTaskId().equals(task.getTaskId())) return true;
        }
        return false;
    }

    /*
    CRUD OPERATIONS
     */

    /**
     * Retourne un parcours sous forme de DTO GET
     * @param id Integer l'ID du parcours
     * @return DTOEntity le parcours
     */
    @Override
    public DTOEntity read(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        return optPath.isPresent() ?
                new DtoUtils().convertToDto(optPath.get(), new PathGet()) : new Message("Le parcours n'a pas été trouvé", false);
    }

    /**
     * Retourne la liste des parcours en base de données sous forme de DTOs
     * @return List DTOEntity la liste des parcours
     */
    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for (Path p : pathRepository.findAll()) list.add(new DtoUtils().convertToDto(p, new PathGet()));
        return list;
    }

    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        return null;
    }

    @Override
    public DTOEntity update(Integer id, DTOEntity dtoEntity) {
        return null;
    }

    @Override
    public DTOEntity delete(Integer id) {
        return null;
    }

    // private methods
    private void sortTasks(Integer pathId) {
        List<TaskPath> taskPaths = new ArrayList<>();
        for (TaskPath tp : taskPathRepository.findAll()) {
            if (tp.getPath().getPathId().equals(pathId)) {
                taskPaths.add(tp);
            }
        }
        Collections.sort(taskPaths);
        for (int i = 1 ; i <= taskPaths.size() ; i++) {
            if (taskPaths.get(i).getPosition().equals(taskPaths.get(i-1).getPosition())) {
                taskPaths.get(i).setPosition(taskPaths.get(i).getPosition() + 1);
            }
        }
    }

}
