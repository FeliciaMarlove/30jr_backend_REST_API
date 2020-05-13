package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_path", schema = "public", catalog = "_30jd")
public class UserPath implements Serializable {

    @EmbeddedId
    private PK_User_Path pkUserPath;

    @Column(name = "ongoing", nullable = false)
    private boolean ongoing;

    // JOINS
    @ManyToOne
    @MapsId("pathId")
    @JoinColumn(name = "path_id")
    private Path path;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    // GETTERS AND SETTERS

    public PK_User_Path getPkUserPath() {
        return pkUserPath;
    }

    public void setPkUserPath(PK_User_Path pkUserPath) {
        this.pkUserPath = pkUserPath;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // CONSTRUCTORS

    public UserPath(User user, Path path) {
        this.ongoing = true;
        this.user = user;
        this.path = path;
        this.pkUserPath = new PK_User_Path(user.getUserId(), path.getPathId());
    }

    public UserPath() {

    }

    // HASH EQUALS STRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPath userPath = (UserPath) o;
        return pkUserPath.equals(userPath.pkUserPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkUserPath, ongoing, path, user);
    }
}
