package be.iramps.florencemary._30jd_back.models;

import be.iramps.florencemary._30jd_back.security.UserRoles;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Modèle : Utilisateur
 */
@Entity
@Table(name = "person", schema = "public", catalog = "_30jd")
public class User implements Serializable {

    /**
     * Intéger ID auto-incrémenté
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", allocationSize = 1, initialValue = 1)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * String email
     * Non null
     * Unique
     * Longueur max = 255
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * String mot de passe
     * Non null
     * Longueur max = 255
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * boolean inscription à la newsletter
     * Non null
     */
    @Column(name = "newsletter", nullable = false)
    private boolean newsletter;

    /**
     * boolean statut (occupé/non occupé)
     * Non null
     */
    @Column(name = "busy", nullable = false)
    private boolean busy;

    /**
     * UserRoles rôle
     */
    @Column(name = "user_role", nullable = false)
    private UserRoles userRole;

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
     * Retourne l'e-mail de l'utilisateur
     * @return String l'e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'e-mail de l'utilisateur
     * @param email String l'e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe de l'utilisateur
     * @return String le mot de passe
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur et le hache en utilisant l'algorithme BCrypt
     * @param password String le mot de passe
     */
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Retourne l'inscription à la newsletter (inscrit/non-inscrit)
     * @return boolean l'inscription (true = inscrit, false = non-inscrit)
     */
    public boolean isNewsletter() {
        return newsletter;
    }

    /**
     * Définit l'inscription à la newsletter (inscrit/non-inscrit)
     * @param newsletter boolean l'inscription (true = inscrit, false = non-inscrit)
     */
    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    /**
     * Retourne le statut de l'utilisateur (occupé/non-occupé)
     * @return boolean le statut (true = occupé, false = non-occupé)
     */
    public boolean isBusy() {
        return busy;
    }

    /**
     * Définit le statut de l'utilisateur (occupé/non-occupé)
     * @param busy boolean le statut (true = occupé, false = non-occupé)
     */
    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    /**
     * Retour le rôle de l'utilisateur
     * @return UserRoles le rôle
     */
    public UserRoles getUserRole() {
        return userRole;
    }

    /**
     * Définit le rôle de l'utilisateur
     * @param userRole UserRoles le rôle
     */
    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    // CONSTRUCTORS

    /**
     * Constructeur de l'utilisateur
     * Initialise busy = false
     * @param email String l'e-mail
     * @param password String le mot de passe
     *                 hache le mot de passe en utilisant l'algorithme BCrypt
     * @param newsletter boolean l'inscription à la newsletter
     * @param userRole UserRoles le rôle
     */
    public User(String email, String password, boolean newsletter, UserRoles userRole) {
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.newsletter = newsletter;
        this.busy = false;
        this.userRole = userRole;
    }

    public User() {
    }

    // HASH EQUALS STRING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password, newsletter, busy, userRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", newsletter=" + newsletter +
                ", busy=" + busy +
                ", userRole=" + userRole +
                '}';
    }
}
