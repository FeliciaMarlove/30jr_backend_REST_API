package be.iramps.florencemary._30jd_back.DTO;

import be.iramps.florencemary._30jd_back.security.UserRoles;

import java.sql.Timestamp;

public class UserGet implements DTOEntity {
    private Integer userId;
    private String email;
    private boolean newsletter;
    private boolean busy;
    private UserRoles userRole;
    private Timestamp timestamp;
    private boolean localNotif;
    private int localNotifHour;
    private boolean introNotif;
    private boolean pushNotif;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isIntroNotif() {
        return introNotif;
    }

    public void setIntroNotif(boolean introNotif) {
        this.introNotif = introNotif;
    }

    public boolean isPushNotif() {
        return pushNotif;
    }

    public void setPushNotif(boolean pushNotif) {
        this.pushNotif = pushNotif;
    }

    public UserGet(Integer userId, String email, boolean newsletter, boolean busy, UserRoles userRole) {
        this.userId = userId;
        this.email = email;
        this.newsletter = newsletter;
        this.busy = busy;
        this.userRole = userRole;
    }

    public UserGet() {
    }

    @Override
    public String toString() {
        return "UserGet{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", newsletter=" + newsletter +
                ", busy=" + busy +
                ", userRole=" + userRole +
                ", timestamp=" + timestamp +
                ", localNotif=" + localNotif +
                ", localNotifHour=" + localNotifHour +
                '}';
    }
}
