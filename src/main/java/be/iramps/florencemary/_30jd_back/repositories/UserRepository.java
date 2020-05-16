package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.security.UserRoles;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findByUserRole(UserRoles userRole);
}
