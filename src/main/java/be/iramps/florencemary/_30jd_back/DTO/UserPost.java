package be.iramps.florencemary._30jd_back.DTO;

/**
 * DTO POST utilisateur
 */
public class UserPost implements DTOEntity {
    private String email;
    private String password;
    private boolean newsletter;
    private boolean localNotif;
    private int localNotifHour;

    public boolean isLocalNotif() {
        return localNotif;
    }

    public void setLocalNotif(boolean localNotif) {
        this.localNotif = localNotif;
    }

    public int getLocalNotifHour() {
        return localNotifHour;
    }

    public void setLocalNotifHour(int localNotifHour) {
        this.localNotifHour = localNotifHour;
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
        this.password = password;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public UserPost(String email, String password, boolean newsletter) {
        this.email = email;
        this.password = password;
        this.newsletter = newsletter;
    }

    public UserPost() {
    }
}
