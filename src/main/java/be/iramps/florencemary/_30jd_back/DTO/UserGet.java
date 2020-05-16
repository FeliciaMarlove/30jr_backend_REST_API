package be.iramps.florencemary._30jd_back.DTO;

import be.iramps.florencemary._30jd_back.security.UserRoles;

public class UserGet implements DTOEntity {
    private Integer userId;
    private String email;
    private boolean newsletter;
    private boolean busy;
    private UserRoles userRole;

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
                '}';
    }
}
