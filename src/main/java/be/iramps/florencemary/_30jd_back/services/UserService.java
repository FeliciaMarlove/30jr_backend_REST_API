package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.models.UserRole;
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

     public Message login(DTOEntity connection) {
        String pwd = ((Connection)connection).getPassword();
        String email = ((Connection)connection).getEmail();
        for(User u: userRepository.findAll()) {
            if (u.getEmail().equals(email)) {
                if(BCrypt.checkpw(pwd, u.getPassword())) {
                    return new Message("Connexion réussie |ROLE "+u.getUserRole(), true);
                }
                return new Message("Mot de passe incorrect.", false);
            }
        }
        return new Message("L'adresse e-mail n'existe pas.", false);
     }

    /*
    CRUD OPERATIONS
     */

    @Override
    public DTOEntity read(Integer id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.isPresent() ?
                new DtoUtils().convertToDto(optUser.get(), new UserGet()) : new Message("L'utilisateur n'a pas été trouvé.", false);
    }

    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(User u: userRepository.findAll()) list.add(new DtoUtils().convertToDto(u, new UserGet()));
        return list;
    }

    private boolean validateEmail(String emailToValidate) {
        //regex documentation : https://howtodoinjava.com/regex/java-regex-validate-email-address/
        String regex = "^[\\w!#$%&’*+/=?`|{}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailToValidate);
        return matcher.matches();
    }

    private boolean validatePassword(String pwdToValidate) {
        return pwdToValidate.length() >= 6;
    }

    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        if (!validateEmail(((UserPost)dtoEntity).getEmail())) return new Message("Le format de l'e-mail est incorrect", false);
        if (!validatePassword(((UserPost)dtoEntity).getPassword())) return new Message("Le mot de passe doit comporter au moins 6 caractères", false);
        if(userRepository.findByEmail(((UserPost)dtoEntity).getEmail()) == null) {
            User u = (User)new DtoUtils().convertToEntity(new User(), dtoEntity);
            System.out.println(u);
            try {
                u.setUserRole(UserRole.USER);
                userRepository.save(u);
                return new DtoUtils().convertToDto(u, new UserGet());
            } catch (Exception e) {
                return new Message("Données manquantes, l'enregistrement a échoué.", false);
            }
        }
        return new Message("L'email existe déjà.", false);
    }

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
