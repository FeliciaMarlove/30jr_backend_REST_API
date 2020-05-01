package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.*;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import be.iramps.florencemary._30jd_back.utilities.UserPathHistoryObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
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
    BUSINESS LAYER
     */

    @Transactional
    public DTOEntity seeTaskOfTheDay(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            User u = optionalUser.get();
            for (UserPath up: userPathRepository.findAll()) {
                if(up.getUser().getUserId().equals(userId) && up.isOngoing()) {
                    LocalDate dateBegin = up.getPkUserPath().getDateUserPath();
                    LocalDate today = LocalDate.now();
                    int deltaDays = Period.between(dateBegin, today).getDays();
                    Path p = up.getPath();
                    if (deltaDays < p.getTasks().size()) {
                        return new DtoUtils().convertToDto(p.getTasks().get(deltaDays), new TaskGet());
                    } else {
                        up.setOngoing(false);
                        userPathRepository.save(up);
                        u.setBusy(false);
                        userRepository.save(u);
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public List<UserPathHistoryObj> listHistory(Integer userId) {
        List<UserPathHistoryObj> list = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            for (UserPath up: userPathRepository.findAll()) {
                if (up.getUser().getUserId().equals(userId) && !up.isOngoing()) {
                    LocalDate begin = up.getPkUserPath().getDateUserPath();
                    LocalDate end = begin.plusDays(30);
                    list.add(new UserPathHistoryObj(up.getPath().getPathName(), end));
                }
            }
        }
        return list;
    }

    // same as create method !
//    public Message startPath(Integer pathId, Integer userId) {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (!optionalUser.isPresent()) return new Message("UserId not found", false);
//        if (optionalUser.get().isBusy()) return new Message("User is already busy", false);
//        Optional<Path> optionalPath = pathRepository.findById(pathId);
//        if (!optionalPath.isPresent()) return new Message("PathId not found", false);
//        UserPath up = new UserPath(optionalUser.get(), optionalPath.get());
//        optionalUser.get().setBusy(true);
//        userRepository.save(optionalUser.get());
//        userPathRepository.save(up);
//        return new Message("User " + userId + " just started path " + pathId, true);
//    }

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
            if (!optPath.isPresent()) return null;
            Path p = optPath.get();
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
