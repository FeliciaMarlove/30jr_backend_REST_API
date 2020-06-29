package be.iramps.florencemary._30jd_back.models;

import be.iramps.florencemary._30jd_back.security.UserRoles;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "person", schema = "public", catalog = "_30jd")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", allocationSize = 1, initialValue = 1)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "newsletter", nullable = false)
    private boolean newsletter;

    @Column(name = "busy", nullable = false)
    private boolean busy;

    @Column(name = "user_role", nullable = false)
    private UserRoles userRole;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "local_notif")
    private boolean localNotif;

    @Column(name = "local_notif_hour")
    private int localNotifHour;

    // JOINS

    // GETTERS AND SETTERS

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    // CONSTRUCTORS

    public User(String email, String password, boolean newsletter) {
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.newsletter = newsletter;
        this.busy = false;
        this.userRole = userRole.USER;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.localNotif = true;
        this.localNotifHour = Integer.parseInt(timestamp.toString().substring(11,13));
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
                ", timestamp=" + timestamp +
                '}';
    }
}
