package be.iramps.florencemary._30jd_back.utilities;

import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.security.UserRoles;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class AdminUsersDataLoader implements ApplicationRunner {
    private UserRepository userRepository;

    @Autowired
    public AdminUsersDataLoader(UserRepository userRepository)  { this.userRepository = userRepository; }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("1 - Init super users");
        //TODO : stronger passwords
        if (userRepository.findByUserRole(UserRoles.ADMIN).size() == 0) {
            userRepository.save(new User("admin@admin", "adminpassword", false, UserRoles.ADMIN));
            userRepository.save(new User("admin2@admin2", "adminpassword2", false, UserRoles.ADMIN));
            System.out.println("ADMIN users created");
        }
    }
}
