package be.iramps.florencemary._30jd_back.DTO;

/**
 * DTO GET tâche
 */
public class TaskGet implements DTOEntity {
    private Integer taskId;
    private String taskName;
    private String taskShortDescription;
    private String taskLongDescription;
    private boolean taskActive;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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

    public TaskGet(Integer taskId, String taskName, String taskShortDescription, String taskLongDescription, boolean taskActive) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskShortDescription = taskShortDescription;
        this.taskLongDescription = taskLongDescription;
        this.taskActive = taskActive;
    }

    public TaskGet() {
    }

    @Override
    public String toString() {
        return "TaskGet{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskShortDescription='" + taskShortDescription + '\'' +
                ", taskLongDescription='" + taskLongDescription + '\'' +
                ", taskActive=" + taskActive +
                '}';
    }
}
