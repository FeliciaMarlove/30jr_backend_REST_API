package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.PK_Task_Path;
import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.models.TaskPath;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * DAO relation tâche-parcours
 */
public interface TaskPathRepository extends CrudRepository<TaskPath, PK_Task_Path> {
    /**
     * Retourne la liste des tâches-parcours correspondant à un parcours
     * @param path Path le parcours
     * @return Optional List TaskPath la liste des tâches-parcours
     */
    Optional<List<TaskPath>> findByPath(Path path);

    /**
     * Retourne la liste des tâches-parcours correspondant à un parcours et triées sur base de l'attribut position
     * @param path Path le parcours
     * @return List TaskPath la liste des tâches-parcours
     */
    List<TaskPath> findByPathOrderByPosition(Path path);

    /**
     * Retourne la relation tâche-parcours correspondant à un parcours et une position
     * @param path Path le parcours
     * @param postion Integer la position
     * @return TaskPath la relation tâche-parcours
     */
    TaskPath findByPathAndPosition(Path path, Integer postion);

    /**
     * Retourne la relation tâche-parcours correspondant à un parcours et une tâche
     * @param path Path le parcours
     * @param task Task la tâche
     * @return Optional TaskPath la relation tâche-parcours
     */
    Optional<TaskPath> findByPathAndTask(Path path, Task task);
}
