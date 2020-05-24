package be.iramps.florencemary._30jd_back.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class PK_User_Path implements Serializable {

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "path_id", nullable = false)
    private Integer pathId;

    @Column(name = "date_begin", nullable = false)
    private LocalDate dateUserPath;

    // JOINS

    // GETTERS AND SETTERS

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    public LocalDate getDateUserPath() {
        return dateUserPath;
    }

    public void setDateUserPath(LocalDate dateUserPath) {
        this.dateUserPath = dateUserPath;
    }

    // CONSTRUCTORS

    public PK_User_Path() {
    }

    public PK_User_Path(Integer userId, Integer pathId) {
        this.userId = userId;
        this.pathId = pathId;
        this.dateUserPath = LocalDate.now();
    }

    // HASH EQUALS STRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PK_User_Path that = (PK_User_Path) o;
        return userId.equals(that.userId) &&
                pathId.equals(that.pathId) &&
                dateUserPath.equals(that.dateUserPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, pathId, dateUserPath);
    }

    @Override
    public String toString() {
        return "PK_User_Path{" +
                "userId=" + userId +
                ", pathId=" + pathId +
                ", dateUserPath=" + dateUserPath +
                '}';
    }
}
