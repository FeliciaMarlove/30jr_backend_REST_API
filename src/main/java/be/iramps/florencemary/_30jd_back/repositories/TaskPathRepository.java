package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.PK_Task_Path;
import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.models.TaskPath;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskPathRepository extends CrudRepository<TaskPath, PK_Task_Path> {
    Optional<TaskPath> findByPkTaskPath(PK_Task_Path pk_task_path);
    Optional<List<TaskPath>> findByPath(Path path);
    TaskPath findByPathAndPosition(Path path, Integer postion);
    Optional<TaskPath> findByPathAndTask(Path path, Task task);
}
