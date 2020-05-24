package be.iramps.florencemary._30jd_back.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PK_Task_Path implements Serializable {

    @Column(name = "path_id", nullable = false)
    private Integer pathId;

    @Column(name = "task_id", nullable = false)
    private Integer taskId;

    // JOINS

    // GETTERS AND SETTERS

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }


    // CONSTRUCTORS

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
