package be.iramps.florencemary._30jd_back.controllers.enduserControllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.UserPost;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import be.iramps.florencemary._30jd_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST : utilisateur
 * /api/user
 */
@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin
public class UserController {
    private final UserService service;

    @Autowired
    private UserRepository repository;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * (GET) /{id}
     * @param id l'ID de l'utilisateur
     * @return DTOEntity l'utilisateur
     */
    @GetMapping("/{id}")
    public DTOEntity read(@PathVariable("id") Integer id) { return this.service.read(id); }

    /**
     * (PUT) /{id}
     * @param id l'ID de l'utilisateur à modifier
     * @param user l'utilisateur à mettre à jour
     * @return DTOEntity l'utilisateur mis à jour
     */
    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable("id") Integer id, @RequestBody UserPost user) {
        return this.service.update(id, user);
    }

    /**
     * (DELETE) /{id}
     * @param id l'ID de l'utilisateur à supprimer
     * @return DTOEntity un message de réussite ou d'échec
     */
    @DeleteMapping("/{id}")
    public DTOEntity delete(@PathVariable("id") Integer id) {
        return this.service.delete(id);
    }
}
