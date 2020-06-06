package be.iramps.florencemary._30jd_back.controllers.enduserControllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.UserPathPost;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import be.iramps.florencemary._30jd_back.services.UserPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST : Relation Utilisateur-Parcours
 * /api/userpath
 */
@RestController
@RequestMapping(value = "/api/userpath")
@CrossOrigin
public class UserPathController {
    private final UserPathService service;

    @Autowired
    private UserPathRepository repository;

    public UserPathController(UserPathService service) {
        this.service = service;
    }

    /**
     * (GET)
     * @return List DTOEntity la liste des relations utilisateur-parcours
     */
    @GetMapping
    public List<DTOEntity> read() { return this.service.read(); }

    /**
     * (POST)
     * @param userPath la relation utilisateur-parcours à créer
     * @return DTOEntity la relation créée
     */
    @PostMapping
    public DTOEntity create(@RequestBody UserPathPost userPath) { return this.service.create(userPath); }

    /**
     * (GET) /{userId}
     * Retourne le défi du jour pour un utilisateur
     * @param userId l'ID de l'utlisateur
     * @return DTOEntity la tâche du jour
     */
    @GetMapping("/{userId}")
    public DTOEntity seeTaskOfTheDay(@PathVariable("userId") Integer userId) {
        return this.service.seeTaskOfTheDay(userId);
    }

    /**
     * (GET) /{userId}/day
     * Retourne le jour en cours du parcours actif d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return Integer le numéro du jour en nombre réel (commence à 1)
     */
    @GetMapping("/{userId}/day")
    public Integer getDay(@PathVariable("userId") Integer userId) {
        return this.service.getDayIndex(userId) + 1;
    }

    /**
     * (GET) /paths
     * Retourne la liste des parcours disponibles (size == 30 && actif)
     * @return List DTOEntity la liste des parcours
     */
    @GetMapping("/paths")
    public List<DTOEntity> seeAvailablePaths() {
        return this.service.readPaths();
    }
}
