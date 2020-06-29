package be.iramps.florencemary._30jd_back.services;

import be.iramps.florencemary._30jd_back.DTO.*;
import be.iramps.florencemary._30jd_back.models.*;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskPathRepository;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
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

    /**
     * Retourne la tâche du jour d'un utilisateur
     * Transaction
     * Vérifie qu'une relation utilisateur-parcours en cours existe
     * Récupère la tâche du jour sur base du nombre de jours écoulés entre aujourd'hui et le début du parcours
     * Met à jour la base de données en cas de parcours terminé : UserPath setOngoing = false, User setBusy = false
     * @param userId Integer l'ID de l'utilisateur
     * @return DTOEntity la tâche du jour ou null
     */
    @Transactional
    public DTOEntity seeTaskOfTheDay(Integer userId) {
        UserPath up = findUserPathOfUser(userId);
        User u = userRepository.findById(userId).get();
        if (up != null) {
            int deltaDays = getDayIndex(userId);
            Path p = up.getPath();
            if (deltaDays < taskPathRepository.findByPath(p).get().size()) {
                return new DtoUtils().convertToDto(taskPathRepository.findByPathAndPosition(p, deltaDays).getTask(), new TaskGet());
            } else {
                up.setOngoing(false);
                userPathRepository.save(up);
                u.setBusy(false);
                userRepository.save(u);
                return null;
            }
        }
        return null;
    }

    /**
     * Retourne la différence entre la date du jour et la date de création d'une relation parcours-utilisateur
     * @param userId Integer l'ID de l'utilisateur
     * @return Integer le nombre de jours ou null
     */
    public Integer getDayIndex(Integer userId) {
        UserPath up = findUserPathOfUser(userId);
        if (up != null && up.isOngoing()) {
            LocalDate dateBegin = up.getPkUserPath().getDateUserPath();
            LocalDate today = LocalDate.now();
            return Period.between(dateBegin, today).getDays();
        }
        return null;
    }

    // Private methods
    private UserPath findUserPathOfUser(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            for (UserPath up: userPathRepository.findAll()) {
                if(up.getUser().getUserId().equals(userId) && up.isOngoing()) {
                    return up;
                }
            }
        }
        return null;
    }

     /*
    CRUD OPERATIONS
     */

    /**
     * Retourne la liste des parcours actifs et comportant une liste de 30 défis sous forme de DTOs
     * @return List DTOEntity la liste des parcours
     */
     public List<DTOEntity> readPaths() {
        List<DTOEntity> list = new ArrayList<>();
        for(Path p: pathRepository.findAll()) {
            Optional<List<TaskPath>> opt = taskPathRepository.findByPath(p);
            if (opt.isPresent()) {
                if (p.isPathActive() && opt.get().size() == 30) {
                    list.add(new DtoUtils().convertToDto(p, new PathGet()));
                }
            }
        }
        return list;
    }

    /**
     * Retourne la liste des relations utilisateur-parcours sous forme de DTOs
     * @return List DTOEntity la liste des relations utilisateur-parcours
     */
    public List<DTOEntity> read() {
        List<DTOEntity> list = new ArrayList<>();
        for(UserPath up: userPathRepository.findAll()) list.add(new DtoUtils().convertToDto(up, new UserPathGet()));
        return list;
    }

    /**
     * Crée une relation utilisateur-parcours
     * Transaction
     * Vérifie que l'utilisateur existe et n'a pas déjà un parcours en cours
     * Vérifie que le parcours existe et qu'il est valide (actif et contient une liste de 30 défis)
     * Crée la relation en base de données et définit l'utilisateur comme occupé et la relation comme en cours
     * @param dtoEntity DTOEntity la relation utilisateur-parcours à créer
     * @return DTOEntity la relation créée ou un Message(String) en cas d'échec
     */
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
