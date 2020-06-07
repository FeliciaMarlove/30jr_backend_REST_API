package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Modèle : tâche (déf!)
 */
@Entity
@Table(name = "task", schema = "public", catalog = "_30jd")
public class Task implements Serializable {

    /**
     * Integer ID auto-incrémenté
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
    @SequenceGenerator(name = "task_generator", allocationSize = 1, initialValue = 1)
    @Column(name = "task_id")
    private Integer taskId;

    /**
     * String nom
     * Non null
     * Unique
     * Longueur max = 255
     */
    @Column(name = "task_name", nullable = false, unique = true)
    private String taskName;

    /**
     * String description courte
     * Non null
     * Longueur max = 255
     */
    @Column(name = "task_short_desc", nullable = false)
    private String taskShortDescription;

    /**
     * String description longue
     * Longueur max = 1000
     */
    @Column(name = "task_long_desc", length = 1000)
    private String taskLongDescription;

    /**
     * boolean statut (actif/inactif)
     * Non null
     */
    @Column(name = "task_active", nullable = false)
    private boolean taskActive;

    // JOINS

    // GETTERS AND SETTERS

    /**
     * Retourne l'ID de la tâche
     * @return Integer l'ID
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * Retourne le nom de la tâche
     * @return String le nom
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Définit le nom de la tâche
     * @param taskName String le nom
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Retourne la description courte de la tâche
     * @return String la description courte
     */
    public String getTaskShortDescription() {
        return taskShortDescription;
    }

    /**
     * Définit la description courte de la tâche
     * @param taskShortDescription String la description courte
     */
    public void setTaskShortDescription(String taskShortDescription) {
        this.taskShortDescription = taskShortDescription;
    }

    /**
     * Retourne la description longue de la tâche
     * @return String la description longue
     */
    public String getTaskLongDescription() {
        return taskLongDescription;
    }

    /**
     * Définit la description longue de la tâche
     * @param taskLongDescription String la description longue
     */
    public void setTaskLongDescription(String taskLongDescription) {
        this.taskLongDescription = taskLongDescription;
    }

    /**
     * Retourne le statut (actif/inactif) de la tâche
     * @return boolean le statut (true = actif, false = inactif)
     */
    public boolean isTaskActive() {
        return taskActive;
    }

    /**
     * Définit le statut (actif/inactif) de la tâche
     * @param taskActive boolean le statut (true = actif, false = inactif)
     */
    public void setTaskActive(boolean taskActive) {
        this.taskActive = taskActive;
    }

    // CONSTRUCTORS

    /**
     * Constructeur de la tâche
     * Initialise taskActive = true
     * @param taskName String le nom de la tâche
     * @param taskShortDescription String la description courte de la tâche
     * @param taskLongDescription String le description longue de la tâche (nullable)
     */
    public Task(String taskName, String taskShortDescription, String taskLongDescription) {
        this.taskName = taskName;
        this.taskShortDescription = taskShortDescription;
        this.taskLongDescription = taskLongDescription;
        this.taskActive = true;
    }

    public Task() {
    }

    // HASH EQUALS STRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskActive == task.taskActive &&
                taskName.equals(task.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, taskShortDescription, taskLongDescription, taskActive);
    }

    @Override
    public String toString() {
        return "Task{" +
                //"taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
               // ", taskShortDescription='" + taskShortDescription + '\'' +
               // ", taskLongDescription='" + taskLongDescription + '\'' +
               // ", taskActive=" + taskActive +
                '}';
    }
}
