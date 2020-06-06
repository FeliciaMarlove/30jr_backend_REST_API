package be.iramps.florencemary._30jd_back.DTO;

/**
 * DTO POST Connexion
 */
public class Connection implements DTOEntity {
    private String email;
    private String password;

    public Connection(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Connection() {
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
}
