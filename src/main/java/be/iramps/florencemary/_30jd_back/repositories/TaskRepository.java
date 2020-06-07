package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.Task;
import org.springframework.data.repository.CrudRepository;

/**
 * DAO tâche
 */
public interface TaskRepository extends CrudRepository<Task, Integer> {
    /**
     * Retourne la tâche
     * @param taskName String le nom de la tâche à retrouver
     * @return Task la tâche
     */
    Task findByTaskName(String taskName);
}
