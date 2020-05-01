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
                    return new Message("Connection succeeded", true);
                }
                return new Message("Wrong password", false);
            }
        }
        return new Message("Email not found", false);
     }

    /*
    CRUD OPERATIONS
     */

    @Override
    public DTOEntity read(Integer id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.isPresent() ?
                new DtoUtils().convertToDto(optUser.get(), new UserGet()) : null;
    }

    @Override
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(User u: userRepository.findAll()) list.add(new DtoUtils().convertToDto(u, new UserGet()));
        return list;
    }

    @Override
    public DTOEntity create(DTOEntity dtoEntity) {
        if(userRepository.findByEmail(((UserPost)dtoEntity).getEmail()) == null) {
            User u = (User)new DtoUtils().convertToEntity(new User(), dtoEntity);
            u.setUserRole(UserRole.USER);
            userRepository.save(u);
            return new DtoUtils().convertToDto(u, new UserGet());
        }
        return null;
    }

    @Override
    public DTOEntity update(Integer id, DTOEntity dtoEntity) {
        if(userRepository.existsById(id)) {
            User u = userRepository.findById(id).get();
            u.setEmail(((UserPost)dtoEntity).getEmail());
            u.setNewsletter(((UserPost)dtoEntity).isNewsletter());
            userRepository.save(u);
            return new DtoUtils().convertToDto(u, new UserGet());
        }
        return null;
    }

    @Override
    public DTOEntity delete(Integer id) {
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()) {
            userRepository.delete(optUser.get());
        }
        return new Message("User with id " + id + " deleted");
    }
}
