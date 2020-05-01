package be.iramps.florencemary._30jd_back.utilities;

import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.models.UserRole;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * For development and test purpose
 */
@Component
public class AdminUsersDataLoader implements ApplicationRunner {
    private UserRepository userRepository;

    @Autowired
    public AdminUsersDataLoader(UserRepository userRepository)  { this.userRepository = userRepository; }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByUserRole(UserRole.SUPER).size() == 0) {
            userRepository.save(new User("super@super", "superpassword", false, UserRole.SUPER));
        }
        if (userRepository.findByUserRole(UserRole.ADMIN).size() == 0) {
            userRepository.save(new User("admin@admin", "adminpassword", false, UserRole.ADMIN));
            userRepository.save(new User("admin2@admin2", "adminpassword2", false, UserRole.ADMIN));
        }
    }
}
