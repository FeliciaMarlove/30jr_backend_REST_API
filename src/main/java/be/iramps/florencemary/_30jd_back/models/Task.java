package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "task", schema = "public", catalog = "_30jd")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
    @SequenceGenerator(name = "task_generator", allocationSize = 1, initialValue = 1)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "task_name", nullable = false, unique = true)
    private String taskName;

    @Column(name = "task_short_desc", nullable = false)
    private String taskShortDescription;

    @Column(name = "task_long_desc")
    private String taskLongDescription;

    @Column(name = "task_active")
    private boolean taskActive;

    // JOINS
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, mappedBy = "tasks")
    List<Path> paths;

    // GETTERS AND SETTERS

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskShortDescription() {
        return taskShortDescription;
    }

    public void setTaskShortDescription(String taskShortDescription) {
        this.taskShortDescription = taskShortDescription;
    }

    public String getTaskLongDescription() {
        return taskLongDescription;
    }

    public void setTaskLongDescription(String taskLongDescription) {
        this.taskLongDescription = taskLongDescription;
    }

    public boolean isTaskActive() {
        return taskActive;
    }

    public void setTaskActive(boolean taskActive) {
        this.taskActive = taskActive;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    // CONSTRUCTORS

    public Task(String taskName, String taskShortDescription, String taskLongDescription) {
        this.taskName = taskName;
        this.taskShortDescription = taskShortDescription;
        this.taskLongDescription = taskLongDescription;
        this.taskActive = true;
        this.paths = new ArrayList<>();
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
        return Objects.hash(taskId, taskName, taskShortDescription, taskLongDescription, taskActive, paths);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskShortDescription='" + taskShortDescription + '\'' +
                ", taskLongDescription='" + taskLongDescription + '\'' +
                ", taskActive=" + taskActive +
                ", paths=" + paths +
                '}';
    }
}
