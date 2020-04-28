package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.DtoUtils;
import be.iramps.florencemary._30jd_back.DTO.TaskGet;
import be.iramps.florencemary._30jd_back.DTO.TaskPost;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements CRUDService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /*
   CRUD OPERATIONS
    */

    @Override
    public DTOEntity read(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.isPresent() ?
                new DtoUtils().convertToDto(optionalTask.get(), new TaskGet()) : null;
    }

    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(Task t: taskRepository.findAll()) list.add(new DtoUtils().convertToDto(t, new TaskGet()));
        return list;
    }

    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        if (taskRepository.findByTaskName(((TaskPost)dtoEntity).getTaskName()) == null) {
            Task t = (Task)new DtoUtils().convertToEntity(new Task(), dtoEntity);
            taskRepository.save(t);
            return new DtoUtils().convertToDto(t, new TaskGet());
        }
        return null;
    }

    @Override
    public DTOEntity update(Integer id, DTOEntity dtoEntity) {
        if(taskRepository.existsById(id)) {
            Task t = taskRepository.findById(id).get();
            t.setTaskName(((TaskPost)dtoEntity).getTaskName());
            t.setTaskShortDescription(((TaskPost)dtoEntity).getTaskShortDescription());
            t.setTaskLongDescription(((TaskPost)dtoEntity).getTaskLongDescription());
            taskRepository.save(t);
            return new DtoUtils().convertToDto(t, new TaskGet());
        }
        return null;
    }

    @Override
    public DTOEntity delete(Integer id) {
        Optional<Task> optTask = taskRepository.findById(id);
        if(optTask.isPresent()) {
            optTask.get().setTaskActive(false);
            taskRepository.save(optTask.get());
            return new DtoUtils().convertToDto(optTask.get(), new TaskGet());
        }
        return null;
    }

    public DTOEntity activate(Integer id) {
        Optional<Task> optTask = taskRepository.findById(id);
        if(optTask.isPresent()) {
            optTask.get().setTaskActive(true);
            taskRepository.save(optTask.get());
            return new DtoUtils().convertToDto(optTask.get(), new TaskGet());
        }
        return null;
    }
}
