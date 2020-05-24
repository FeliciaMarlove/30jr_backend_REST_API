package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "task_path", schema = "public", catalog = "_30jd")
public class TaskPath implements Serializable, Comparable<TaskPath> {

    @EmbeddedId
    private PK_Task_Path pkTaskPath;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @MapsId("pathId")
    @JoinColumn(name = "path_id")
    private Path path;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    // GETTERS AND SETTERS

    public PK_Task_Path getPkTaskPath() {
        return pkTaskPath;
    }

    public void setPkTaskPath(PK_Task_Path pkTaskPath) {
        this.pkTaskPath = pkTaskPath;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    // CONSTRUCTORS


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
