package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "path", schema = "public", catalog = "_30jd")
public class Path implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "path_generator")
    @SequenceGenerator(name = "path_generator", allocationSize = 1, initialValue = 1)
    @Column(name = "path_id")
    private Integer pathId;

    @Column(name = "path_name", nullable = false, unique = true)
    private String pathName;

    @Column(name = "path_short_desc")
    private String pathShortDescription;

    @Column(name = "path_long_desc", length = 1000)
    private String pathLongDescription;

    @Column(name = "path_active", nullable = false)
    private boolean pathActive;

    // JOINS

    // GETTERS AND SETTERS

    public Integer getPathId() {
        return pathId;
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


    // CONSTRUCTORS

    public Path(String pathName, String pathShortDescription, String pathLongDescription) {
        this.pathName = pathName;
        this.pathShortDescription = pathShortDescription;
        this.pathLongDescription = pathLongDescription;
    }

    public Path() {

    }

    // HASH EQUALS STRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return pathActive == path.pathActive &&
                pathName.equals(path.pathName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathId, pathName, pathShortDescription, pathLongDescription, pathActive);
    }

    @Override
    public String toString() {
        return "Path{" +
                "pathId=" + pathId +
                ", pathName='" + pathName + '\'' +
                //", pathShortDescription='" + pathShortDescription + '\'' +
                //", pathLongDescription='" + pathLongDescription + '\'' +
                ", pathActive=" + pathActive +
                '}';
    }
}
