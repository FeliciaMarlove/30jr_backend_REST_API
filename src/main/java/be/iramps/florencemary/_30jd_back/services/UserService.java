package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.security.UserRoles;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements CRUDService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

     /*
    BUSINESS LAYER
     */

    /**
     * Vérifie la demande de connexion d'un utilisateur à l'application
     * @param connection DTOEntity DTO de connexion
     * @return DTOEntity l'utilisateur (DTO GET) ou null
     */
     public DTOEntity login(DTOEntity connection) {
        String pwd = ((Connection)connection).getPassword();
        String email = ((Connection)connection).getEmail();
        for(User u: userRepository.findAll()) {
            if (u.getEmail().equals(email)) {
                if(BCrypt.checkpw(pwd, u.getPassword())) {
                    return new DtoUtils().convertToDto(userRepository.findByEmail(email), new UserGet());
                }
                return null;
            }
        }
        return null;
     }

     // Private methods
     private boolean validateEmail(String emailToValidate) {
         //regex documentation : https://howtodoinjava.com/regex/java-regex-validate-email-address/
         String regex = "^[\\w!#$%&’*+/=?`|{}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(emailToValidate);
         return matcher.matches();
     }

    private boolean validatePassword(String pwdToValidate) {
        String regex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%&+=]).*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pwdToValidate);
        return matcher.matches();
    }

    /*
    CRUD OPERATIONS
     */

    /**
     * Retourne un utilisateur sous forme de DTO GET
     * @param id Integer l'ID de l'utilisateur
     * @return DTOEntity l'utilisateur ou un Message(String, booléen) en cas d'échec
     */
    @Override
    public DTOEntity read(Integer id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.isPresent() ?
                new DtoUtils().convertToDto(optUser.get(), new UserGet()) : new Message("L'utilisateur n'a pas été trouvé.", false);
    }

    /**
     * Retourne la liste des utilisateurs sous forme de DTOs
     * @return List DTOEntity la liste des utilisateurs
     */
    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(User u: userRepository.findAll()) list.add(new DtoUtils().convertToDto(u, new UserGet()));
        return list;
    }

    /**
     * Crée un utilisateur
     * Vérifie l'e-mail (format identifiant@domain.extension, pas de caractères non autorisés)
     * Vérifie le mot de passe (8 caractères min., 1 majuscule, 1 minuscule, 1 chiffre, 1 caractère spécial (@#!$%&))
     * Définit le rôle "USER"
     * Gère les erreurs en cas d'informations manquantes
     * Vérifie que l'e-mail n'existe pas déjà en base de données
     * @param dtoEntity DTOEntity l'utilisateur à créer
     * @return DTOEntity l'utilisateur créé (DTO GET) ou un Message(String, booléen) en cas d'échec
     */
    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        if (!validateEmail(((UserPost)dtoEntity).getEmail())) return new Message("Le format de l'e-mail est incorrect", false);
        if (!validatePassword(((UserPost)dtoEntity).getPassword())) return new Message("Le mot de passe doit 8 caractères dont au moins 1 majuscule, 1 minuscule, 1 chiffre et 1 caractère spécial. Les caractères accentués ne sont pas acceptés.", false);
        if(userRepository.findByEmail(((UserPost)dtoEntity).getEmail()) == null) {
            User u = (User)new DtoUtils().convertToEntity(new User(), dtoEntity);
            try {
                u.setUserRole(UserRoles.USER);
                userRepository.save(u);
                return new DtoUtils().convertToDto(u, new UserGet());
            } catch (Exception e) {
                return new Message("Données manquantes, l'enregistrement a échoué.", false);
            }
        }
        return new Message("L'email existe déjà.", false);
    }

    /**
     * Met à jour un utilisateur
     * Vérifie que l'utilisateur existe en base de données
     * Gère les erreurs en cas de données manquantes ou de doublon
     * @param id Integer l'ID de l'utilisateur
     * @param dtoEntity DTOEntity l'utilisateur à mettre à jour
     * @return DTOEntity l'utilisateur (DTO GET) mis à jour ou un Message(String, booléen) en cas d'échec
     */
    @Override
    public DTOEntity update(Integer id, DTOEntity dtoEntity) {
        if (((UserPost)dtoEntity).getEmail() != null && !validateEmail(((UserPost)dtoEntity).getEmail())) return new Message("Le format de l'e-mail est incorrect", false);
        if(userRepository.existsById(id)) {
            User u = userRepository.findById(id).get();
            try {
                u.setEmail(((UserPost)dtoEntity).getEmail());
                u.setNewsletter(((UserPost)dtoEntity).isNewsletter());
                userRepository.save(u);
                return new DtoUtils().convertToDto(u, new UserGet());
            } catch (Exception e) {
                return new Message("Données manquantes ou doublon, l'enregistrement a échoué.", false);
            }
        }
        return new Message("L'utlisateur avec l'ID " + id + "n'a pas été trouvé.", false);
    }

    /**
     * Supprime un utilisateur de la base de données
     * L'enregistrement est supprimé sans archivage
     * @param id Integer l'ID de l'utilisateur à supprimer
     * @return DTOEntity Message(String, booléen)
     */
    @Override
    public DTOEntity delete(Integer id) {
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()) {
            userRepository.delete(optUser.get());
            return new Message("Utilisateur avec l'ID " + id + " supprimé", true);
        }
        return new Message("L'utilisateur n'a pas été trouvé.", false);
    }
}
