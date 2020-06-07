package be.iramps.florencemary._30jd_back.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Modèle : Clé primaire de la relation Tâche-Parcours
 * Clé composée
 */
@Embeddable
public class PK_Task_Path implements Serializable {

    /**
     * Integer l'ID du parcours
     * Non null
     */
    @Column(name = "path_id", nullable = false)
    private Integer pathId;

    /**
     * Integer l'ID de la tâche
     * Non null
     */
    @Column(name = "task_id", nullable = false)
    private Integer taskId;

    // JOINS

    // GETTERS AND SETTERS

    /**
     * Retourne l'ID du parcours
     * @return Integer l'ID
     */
    public Integer getPathId() {
        return pathId;
    }

    /**
     * Définit l'ID du parcours
     * @param pathId Integer l'ID
     */
    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    /**
     * Retourne l'ID de la tâche
     * @return Integer l'ID
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * Définit l'ID de la tâche
     * @param taskId Integer l'ID
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }


    // CONSTRUCTORS

    /**
     * Constructeur de la clé primaire composée
     * @param pathId l'ID du parcours
     * @param taskId l'ID de la tâche
     */
    public PK_Task_Path(Integer pathId, Integer taskId) {
        this.pathId = pathId;
        this.taskId = taskId;
    }

    public PK_Task_Path() {
    }

    // HASH EQUALS TOSTRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PK_Task_Path that = (PK_Task_Path) o;
        return pathId.equals(that.pathId) &&
                taskId.equals(that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathId, taskId);
    }

    @Override
    public String toString() {
        return "PK_Task_Path{" +
                "pathId=" + pathId +
                ", taskId=" + taskId +
                '}';
    }
}
