package be.iramps.florencemary._30jd_back.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Modèle : Clé primaire de la relation Utilisateur-Parcours
 * Clé composée
 */
@Embeddable
public class PK_User_Path implements Serializable {

    /**
     * Integer l'ID de l'utilisateur
     * Non null
     */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /**
     * Integer l'ID du parcours
     * Non null
     */
    @Column(name = "path_id", nullable = false)
    private Integer pathId;

    /**
     * LocalDate la date de création
     * Non null
     */
    @Column(name = "date_begin", nullable = false)
    private LocalDate dateUserPath;

    // JOINS

    // GETTERS AND SETTERS

    /**
     * Retourne l'ID de l'utilisateur
     * @return Integer l'ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Définit l'ID de l'utilisateur
     * @param userId Integer l'ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Retourn l'ID du parcours
     * @return Integer l'ID
     */
    public Integer getPathId() {
        return pathId;
    }

    /**
     * Définit l'ID du parcours
     * @param pathId Integer l'ID
     */
    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    /**
     * Retourne la date de création
     * @return LocalDate la date
     */
    public LocalDate getDateUserPath() {
        return dateUserPath;
    }

    /**
     * Définit la date de création
     * @param dateUserPath LocalDate la date
     */
    public void setDateUserPath(LocalDate dateUserPath) {
        this.dateUserPath = dateUserPath;
    }

    // CONSTRUCTORS

    public PK_User_Path() {
    }

    /**
     * Constructeur de la clé composée
     * Définit dateUserPath = LocalDate.now()
     * @param userId Integer l'ID de l'utilisateur
     * @param pathId Integer l'ID du parcours
     */
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
