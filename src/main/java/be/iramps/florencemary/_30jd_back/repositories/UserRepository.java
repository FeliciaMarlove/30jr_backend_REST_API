package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.security.UserRoles;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * DAO utilisateur
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Retourne l'utilisateur correspondant à une adresse e-mail
     * @param email String l'e-mail à retrouver
     * @return User l'utilisateur
     */
    User findByEmail(String email);

    /**
     * Retourne la liste des utilisateurs par rôle
     * @param userRole UserRoles le rôle à rechercher
     * @return List User les utilisteurs
     */
    List<User> findByUserRole(UserRoles userRole);
}
