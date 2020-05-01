package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.PK_User_Path;
import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.models.UserPath;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserPathService {
    private UserPathRepository userPathRepository;
    private UserRepository userRepository;
    private PathRepository pathRepository;

    @Autowired
    public UserPathService(UserPathRepository userPathRepository, UserRepository userRepository, PathRepository pathRepository) {
        this.userPathRepository = userPathRepository;
        this.userRepository = userRepository;
        this.pathRepository = pathRepository;
    }

     /*
    CRUD OPERATIONS
     */

    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(UserPath up: userPathRepository.findAll()) list.add(new DtoUtils().convertToDto(up, new UserPathGet()));
        return list;
    }

    @Transactional
    public DTOEntity create(DTOEntity dtoEntity) {
        Optional<User> optionalUser = userRepository.findById(((UserPathPost)dtoEntity).getUserId());
        if(optionalUser.isPresent() && !optionalUser.get().isBusy()) {
            Optional<Path> optPath = pathRepository.findById(((UserPathPost)dtoEntity).getPathId());
            Path p;
            if (optPath.isPresent()) p = optPath.get();
            else return null;
            User u = optionalUser.get();
            u.setBusy(true);
            userRepository.save(u);
            UserPath up = new UserPath(u, p);
            userPathRepository.save(up);
            return new DtoUtils().convertToDto(up, new UserPathGet());
        }
        return null;
    }
}
