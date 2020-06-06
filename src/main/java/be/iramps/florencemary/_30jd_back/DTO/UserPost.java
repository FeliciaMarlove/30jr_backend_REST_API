package be.iramps.florencemary._30jd_back.DTO;

/**
 * DTO POST utilisateur
 */
public class UserPost implements DTOEntity {
    private String email;
    private String password;
    private boolean newsletter;

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
