package be.iramps.florencemary._30jd_back.DTO;

public class TaskPost implements DTOEntity {
    private String taskName;
    private String taskShortDescription;
    private String taskLongDescription;

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

    public TaskPost(String taskName, String taskShortDescription, String taskLongDescription) {
        this(taskName, taskShortDescription);
        this.taskLongDescription = taskLongDescription;
    }

    public TaskPost(String taskName, String taskShortDescription) {
        this.taskName = taskName;
        this.taskShortDescription = taskShortDescription;
    }

    public TaskPost() {
    }
}
