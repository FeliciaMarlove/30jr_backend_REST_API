package be.iramps.florencemary._30jd_back.DTO;

public class PathPost implements DTOEntity {
    private String pathName;
    private String pathShortDescription;
    private String pathLongDescription;

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

    public PathPost(String pathName, String pathShortDescription, String pathLongDescription) {
        this(pathName, pathShortDescription);
        this.pathLongDescription = pathLongDescription;
    }

    public PathPost(String pathName, String pathShortDescription) {
        this.pathName = pathName;
        this.pathShortDescription = pathShortDescription;
    }

    public PathPost() {
    }
}
