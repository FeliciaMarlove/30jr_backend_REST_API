package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Modèle : Relation Tâche-Parcours
 */
@Entity
@Table(name = "task_path", schema = "public", catalog = "_30jd")
public class TaskPath implements Serializable, Comparable<TaskPath> {

    /**
     * PK_Task_Path clé primaire composéé
     */
    @EmbeddedId
    private PK_Task_Path pkTaskPath;

    /**
     * Integer position de la tâche dans le parcours (exprimé en indice, commence à 0)
     */
    @Column(name = "position")
    private Integer position;

    /**
     * Relation N-1 parcours
     */
    @ManyToOne
    @MapsId("pathId")
    @JoinColumn(name = "path_id")
    private Path path;

    /**
     * Relation N-1 tâche
     */
    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    // GETTERS AND SETTERS

    /**
     * Retourne la clé primaire de la relation
     * @return PK_Task_Path la clé primaire
     */
    public PK_Task_Path getPkTaskPath() {
        return pkTaskPath;
    }

    /**
     * Définit la clé primaire de la relation
     * @param pkTaskPath la clé primaire
     */
    public void setPkTaskPath(PK_Task_Path pkTaskPath) {
        this.pkTaskPath = pkTaskPath;
    }

    /**
     * Retourne le parcours de la relation
     * @return Path le parcours
     */
    public Path getPath() {
        return path;
    }

    /**
     * Définit le parcours de la relation
     * @param path Path le parcours
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Retourne la tâche de la relation
     * @return Task la tâche
     */
    public Task getTask() {
        return task;
    }

    /**
     * Définit la tâche de la relation
     * @param task Task la tâche
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Retourne la position de la tâche dans le parcours (indice)
     * @return Integer l'indice
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Définit la position de la tâche dans le parcours (indice)
     * @param position Integer l'indice
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    // CONSTRUCTORS

    /**
     * Constructeur de la relation tâche-parcours
     * @param task Task la tâche
     * @param path Path le parcours
     * @param position Integer la position
     */
    public TaskPath(Task task, Path path, Integer position) {
        this.position = position;
        this.path = path;
        this.task = task;
        this.pkTaskPath = new PK_Task_Path(path.getPathId(), task.getTaskId());
    }

    public TaskPath() {
    }

    // HASH EQUALS STRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskPath taskPath = (TaskPath) o;
        return pkTaskPath.equals(taskPath.pkTaskPath) &&
                path.equals(taskPath.path) &&
                task.equals(taskPath.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkTaskPath, path, task);
    }

    @Override
    public String toString() {
        return "TaskPath{" +
                "pkTaskPath=" + pkTaskPath +
                ", path=" + path +
                ", task=" + task +
                '}';
    }

    // USED TO ORDER THE LIST OF TASKS BY PATH

    @Override
    public int compareTo(TaskPath o) {
       return this.getPosition().compareTo(o.getPosition());
    }
}
