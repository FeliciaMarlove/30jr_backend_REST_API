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
    public Message addTask(Integer pathId, Integer taskId, Integer index) {
        Optional<Path> optionalPath = pathRepository.findById(pathId);
        if (optionalPath.isPresent() && optionalPath.get().getTasks().size() < 30) {
            Path p = optionalPath.get();
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent() && optionalTask.get().isTaskActive() && !isAlreadyInPath(p, optionalTask.get())) {
                Task task = optionalTask.get();
                if (index >= 0 &&
                        (index <= p.getTasks().size()
                                ||
                                (p.getTasks().isEmpty() && index == 0))
                ) {
                    p.getTasks().add(index, task);
                } else {
                    return new Message("L'index n'est pas valide", false);
                }
                taskRepository.save(task);
                pathRepository.save(p);
                return new Message("Défi ajouté", true);
            }
            return new Message("Le défi n'existe pas, est inactif ou est déjà dans la liste", false);
        }
        return new Message("Le parcours n'a pas été trouvé ou est complet", false);
    }

    private boolean isAlreadyInPath(Path path, Task task) {
        for(Task t: path.getTasks()) {
            if (t.equals(task)) return true;
        }
        return false;
    }

    @Transactional
    public Message removeTask(Integer pathId, Integer taskId) {
        Optional<Path> optionalPath = pathRepository.findById(pathId);
        if (optionalPath.isPresent()) {
            Path p = optionalPath.get();
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent() && p.getTasks().size() > 0) {
                Task task = optionalTask.get();
                if (p.getTasks().remove(task)) {
                    pathRepository.save(p);
                    return new Message("Défi supprimé de la liste", true);
                }
                //task.getPaths().remove(p);
                //taskRepository.save(task);
            }
            return new Message("Le défi n'est pas dans la liste", false);
        }
        return new Message("L'ID du parcours n'a pas été trouvé", false);
    }

    /*
    CRUD OPERATIONS
     */

    public List<DTOEntity> listTasks(Integer id) {
        List<DTOEntity> list = new ArrayList<>();
        Optional<Path> optionalPath = pathRepository.findById(id);
        if (optionalPath.isPresent()) {
            for (Task t: optionalPath.get().getTasks()) {
                list.add(new DtoUtils().convertToDto(t, new TaskGet()));
            }
        }
        return list;
    }

    @Override
    public DTOEntity read(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        return optPath.isPresent() ?
                new DtoUtils().convertToDto(optPath.get(), new PathGet()) : new Message("Le parcours n'a pas été trouvé", false) ;
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
        if(pathRepository.existsById(id)) {
            Path p = pathRepository.findById(id).get();
            try {
                p.setPathName(((PathPost)dtoEntity).getPathName());
                p.setPathShortDescription(((PathPost)dtoEntity).getPathShortDescription());
                p.setPathLongDescription(((PathPost)dtoEntity).getPathLongDescription());
                pathRepository.save(p);
                return new DtoUtils().convertToDto(p, new PathGet());
            } catch (Exception e) {
                return new Message("Informations manquantes ou doublon, l'enregistrement a échoué.", false);
            }
        }
        return new Message("Le parcours avec l'ID " + id + " n'a pas été trouvé, l'enregistrement a échoué.", false);
    }

    @Override
    public DTOEntity delete(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        if(optPath.isPresent() && !pathIsUsed(id)) {
            optPath.get().setPathActive(false);
            pathRepository.save(optPath.get());
            return new DtoUtils().convertToDto(optPath.get(), new PathGet());
        }
        return new Message("La parcours avec l'ID " + id + " n'a pas été trouvé.", false);
    }

    public DTOEntity activate(Integer id) {
        Optional<Path> optPath = pathRepository.findById(id);
        if(optPath.isPresent()) {
            optPath.get().setPathActive(true);
            pathRepository.save(optPath.get());
            return new DtoUtils().convertToDto(optPath.get(), new PathGet());
        }
        return new Message("La parcours avec l'ID " + id + " n'a pas été trouvé.", false);
    }

    private boolean pathIsUsed(Integer id) {
        for(UserPath up: userPathRepository.findAll()) {
            if ((up.getPath().getPathId().equals(id) && up.isOngoing())) return true;
        }
        return false;
    }
}
