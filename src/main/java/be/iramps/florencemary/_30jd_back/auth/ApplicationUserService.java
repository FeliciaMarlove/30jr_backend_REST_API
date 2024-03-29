package be.iramps.florencemary._30jd_back.auth;

import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Récupère l'utilisateur en base de données
 */
@Service
public class ApplicationUserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Récupère l'utilisateur en base de données
     * @param username identifiant utilisé pour la connexion
     * @return ApplicationUser le principal
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new ApplicationUser(user);
    }
}
