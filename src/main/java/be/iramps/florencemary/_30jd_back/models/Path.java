package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Modèle : Parcours
 */
@Entity
@Table(name = "path", schema = "public", catalog = "_30jd")
public class Path implements Serializable {

    /**
     * Integer ID auto-incrémenté
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "path_generator")
    @SequenceGenerator(name = "path_generator", allocationSize = 1, initialValue = 1)
    @Column(name = "path_id")
    private Integer pathId;

    /**
     * String nom
     * Non null
     * Unique
     * Longueur max = 255
     */
    @Column(name = "path_name", nullable = false, unique = true)
    private String pathName;

    /**
     * String description courte
     * Non null
     * Longueur max = 255
     */
    @Column(name = "path_short_desc", nullable = false)
    private String pathShortDescription;

    /**
     * String description longue
     * Longueur max = 1000
     */
    @Column(name = "path_long_desc", length = 1000)
    private String pathLongDescription;

    /**
     * boolean statut (actif/inactif)
     * Non null
     */
    @Column(name = "path_active", nullable = false)
    private boolean pathActive;

    // JOINS

    // GETTERS AND SETTERS

    /**
     * Retourne l'ID du parcours
     * @return Integer l'ID
     */
    public Integer getPathId() {
        return pathId;
    }

    /**
     * Retourne le nom du parcours
     * @return String le nom
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * Définit le nom du parcours
     * @param pathName String le nom
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    /**
     * Retourne la description courte du parcours
     * @return String la description
     */
    public String getPathShortDescription() {
        return pathShortDescription;
    }

    /**
     * Définit la description courte du parcours
     * @param pathShortDescription String la description courte
     */
    public void setPathShortDescription(String pathShortDescription) {
        this.pathShortDescription = pathShortDescription;
    }

    /**
     * Retourne la description longue parcours
     * @return String la description longue
     */
    public String getPathLongDescription() {
        return pathLongDescription;
    }

    /**
     * Définit la description longue du parcours
     * @param pathLongDescription String la description longue
     */
    public void setPathLongDescription(String pathLongDescription) {
        this.pathLongDescription = pathLongDescription;
    }

    /**
     * Retourne le statut (actif/inactif) du parcours
     * @return boolean le statut (true = actif, false = inactif)
     */
    public boolean isPathActive() {
        return pathActive;
    }

    /**
     * Définit le statut (actif/inactif) du parcours
     * @param pathActive boolean le statut (true = actif, false = inactif)
     */
    public void setPathActive(boolean pathActive) {
        this.pathActive = pathActive;
    }


    // CONSTRUCTORS

    /**
     * Constructeur du parcours
     * Initialise pathActive = false
     * @param pathName String le nom du parcours
     * @param pathShortDescription String la description courte du parcours
     * @param pathLongDescription String la description longue du parcours (nullable)
     */
    public Path(String pathName, String pathShortDescription, String pathLongDescription) {
        this.pathName = pathName;
        this.pathShortDescription = pathShortDescription;
        this.pathLongDescription = pathLongDescription;
        this.pathActive = false;
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
