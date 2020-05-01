package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.models.UserPath;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PathService implements CRUDService {
    private PathRepository pathRepository;
    private UserPathRepository userPathRepository;
    private TaskRepository taskRepository;

    @Autowired
    public PathService(PathRepository pathRepository, UserPathRepository userPathRepository, TaskRepository taskRepository) {
        this.pathRepository = pathRepository;
        this.userPathRepository = userPathRepository;
        this.taskRepository = taskRepository;
    }

    /*
    BUSINESS LAYER
     */

    @Transactional
    public List<DTOEntity> addTask(Integer pathId, Integer taskId, Integer index) {
        Optional<Path> optionalPath = pathRepository.findById(pathId);
        if (optionalPath.isPresent() && optionalPath.get().getTasks().size() < 30) {
            Path p = optionalPath.get();
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                p.getTasks().add(index, task);
                task.getPaths().add(p);
                taskRepository.save(task);
                pathRepository.save(p);
            }
            List<DTOEntity> tasksInPath = new ArrayList<>();
            for(Task t: p.getTasks()) {
                tasksInPath.add(new DtoUtils().convertToDto(t, new TaskGet()));
            }
            return tasksInPath;
        }
       return null;
    }

    @Transactional
    public List<DTOEntity> removeTask(Integer pathId, Integer taskId) {
        Optional<Path> optionalPath = pathRepository.findById(pathId);
        if (optionalPath.isPresent()) {
            Path p = optionalPath.get();
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                p.getTasks().remove(task);
                task.getPaths().remove(p);
                taskRepository.save(task);
                pathRepository.save(p);
            }
            List<DTOEntity> tasksInPath = new ArrayList<>();
            for(Task t: p.getTasks()) {
                tasksInPath.add(new DtoUtils().convertToDto(t, new TaskGet()));
            }
            return tasksInPath;
        }
        return null;
    }

    /*
    CRUD OPERATIONS
     */

    @Override
    public DTOEntity read(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        return optPath.isPresent() ?
                new DtoUtils().convertToDto(optPath.get(), new PathGet()) : null ;
    }

    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(Path p: pathRepository.findAll()) list.add(new DtoUtils().convertToDto(p, new PathGet()));
        return list;
    }

    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        if (pathRepository.findByPathName(((PathPost)dtoEntity).getPathName()) == null) {
            Path p = (Path)new DtoUtils().convertToEntity(new Path(), dtoEntity);
            pathRepository.save(p);
            return new DtoUtils().convertToDto(p, new PathGet());
        }
        return null;
    }

    @Override
    public DTOEntity update(Integer id, DTOEntity dtoEntity) {
        if(pathRepository.existsById(id)) {
            Path p = pathRepository.findById(id).get();
            p.setPathName(((PathPost)dtoEntity).getPathName());
            p.setPathShortDescription(((PathPost)dtoEntity).getPathShortDescription());
            p.setPathLongDescription(((PathPost)dtoEntity).getPathLongDescription());
            pathRepository.save(p);
            return new DtoUtils().convertToDto(p, new PathGet());
        }
        return null;
    }

    @Override
    public DTOEntity delete(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        if(optPath.isPresent() && !pathIsUsed(id)) {
            optPath.get().setPathActive(false);
            pathRepository.save(optPath.get());
            return new DtoUtils().convertToDto(optPath.get(), new PathGet());
        }
        return null;
    }

    public DTOEntity activate(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        if(optPath.isPresent()) {
            optPath.get().setPathActive(true);
            pathRepository.save(optPath.get());
            return new DtoUtils().convertToDto(optPath.get(), new PathGet());
        }
        return null;
    }

    private boolean pathIsUsed(Integer id) {
        for(UserPath up: userPathRepository.findAll()) {
            if ((up.getPath().getPathId().equals(id) && up.isOngoing())) return true;
        }
        return false;
    }
}
