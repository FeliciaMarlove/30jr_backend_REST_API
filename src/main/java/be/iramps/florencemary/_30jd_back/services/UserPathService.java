package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.*;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskPathRepository;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import be.iramps.florencemary._30jd_back.DTO.UserPathHistoryObj;
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
    private TaskPathRepository taskPathRepository;

    @Autowired
    public UserPathService(UserPathRepository userPathRepository, UserRepository userRepository, PathRepository pathRepository, TaskPathRepository taskPathRepository) {
        this.userPathRepository = userPathRepository;
        this.userRepository = userRepository;
        this.pathRepository = pathRepository;
        this.taskPathRepository = taskPathRepository;
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
                    if (deltaDays < taskPathRepository.findByPath(p).get().size()) {
                        return new DtoUtils().convertToDto(taskPathRepository.findByPathAndPosition(p, deltaDays), new TaskGet());
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

     /*
    CRUD OPERATIONS
     */

    public List<DTOEntity> readPaths() {
        List<DTOEntity> list = new ArrayList<>();
        for(Path p: pathRepository.findAll()) {
            if (p.isPathActive() && taskPathRepository.findByPath(p).get().size() == 30) {
                list.add(new DtoUtils().convertToDto(p, new PathGet()));
            }
        }
        return list;
    }

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
            if (!optPath.isPresent()) return new Message("Le parcours n'existe pas");
            if (!optPath.get().isPathActive() && taskPathRepository.findByPath(optPath.get()).get().size() != 30) return new Message("Le parcours n'est pas disponible");
            Path p = optPath.get();
            User u = optionalUser.get();
            u.setBusy(true);
            userRepository.save(u);
            UserPath up = new UserPath(u, p);
            userPathRepository.save(up);
            return new DtoUtils().convertToDto(up, new UserPathGet());
        }
        return new Message("L'utilisateur n'a pas été trouvé ou a déjà commencé un parcours");
    }
}
