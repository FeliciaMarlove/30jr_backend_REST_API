package be.iramps.florencemary._30jd_back.DTO;

public class PathGet implements DTOEntity {
    private Integer pathId;
    private String pathName;
    private String pathShortDescription;
    private String pathLongDescription;
    private boolean pathActive;

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathShortDescription() {
        return pathShortDescription;
    }

    public void setPathShortDescription(String pathShortDescription) {
        this.pathShortDescription = pathShortDescription;
    }

    public String getPathLongDescription() {
        return pathLongDescription;
    }

    public void setPathLongDescription(String pathLongDescription) {
        this.pathLongDescription = pathLongDescription;
    }

    public boolean isPathActive() {
        return pathActive;
    }

    public void setPathActive(boolean pathActive) {
        this.pathActive = pathActive;
    }

    public PathGet(Integer pathId, String pathName, String pathShortDescription, String pathLongDescription, boolean pathActive) {
        this.pathId = pathId;
        this.pathName = pathName;
        this.pathShortDescription = pathShortDescription;
        this.pathLongDescription = pathLongDescription;
        this.pathActive = pathActive;
    }

    public PathGet() {
    }

    @Override
    public String toString() {
        return "PathGet{" +
                "pathId=" + pathId +
                ", pathName='" + pathName + '\'' +
                ", pathShortDescription='" + pathShortDescription + '\'' +
                ", pathLongDescription='" + pathLongDescription + '\'' +
                ", pathActive=" + pathActive +
                '}';
    }
}
