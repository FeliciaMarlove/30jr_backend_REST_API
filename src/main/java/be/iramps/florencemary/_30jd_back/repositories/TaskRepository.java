package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
