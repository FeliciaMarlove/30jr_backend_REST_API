package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Modèle : Relation utilisateur-parcours
 */
@Entity
@Table(name = "user_path", schema = "public", catalog = "_30jd")
public class UserPath implements Serializable {

    /**
     * PK_User_Path clé primaire composée
     */
    @EmbeddedId
    private PK_User_Path pkUserPath;

    /**
     * boolean statut (true = en cours, false = pas en cours)
     */
    @Column(name = "ongoing", nullable = false)
    private boolean ongoing;

    // JOINS
    /**
     * Relation N-1 parcours
     */
    @ManyToOne
    @MapsId("pathId")
    @JoinColumn(name = "path_id")
    private Path path;

    /**
     * Relation N-1 utilisateur
     */
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    // GETTERS AND SETTERS

    /**
     * Retourne la clé primaire de la relation
     * @return PK_User_Path la clé primaire
     */
    public PK_User_Path getPkUserPath() {
        return pkUserPath;
    }

    /**
     * Définit la clé primaire de la relation
     * @param pkUserPath PK_User_Path la clé primaire
     */
    public void setPkUserPath(PK_User_Path pkUserPath) {
        this.pkUserPath = pkUserPath;
    }

    /**
     * Retourne le statut (en cours/pas en cours) de la relation
     * @return boolean le statut (true = en cours, false = pas en cours)
     */
    public boolean isOngoing() {
        return ongoing;
    }

    /**
     * Définit le statut (en cours/pas en cours) de la relation
     * @param ongoing boolean le statut (true = en cours, false = pas en cours)
     */
    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    /**
     * Retourne le parcours de la relation
     * @return Path le parcours
     */
    public Path getPath() {
        return path;
    }

    /**
     * Définit le parcours de la relation
     * @param path Path le parcours
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Retourne l'utilisateur de la relation
     * @return User l'utilisateur
     */
    public User getUser() {
        return user;
    }

    /**
     * Définit l'utilisateur de la relation
     * @param user User l'utilisateur
     */
    public void setUser(User user) {
        this.user = user;
    }

    // CONSTRUCTORS

    /**
     * Constructeur de la relation utilisateur-parcours
     * Instancie une clé primaire PK_User_Path(userId, pathId)
     * @param user User l'utilisateur de la relation
     * @param path Path le parcours de la relation
     */
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
