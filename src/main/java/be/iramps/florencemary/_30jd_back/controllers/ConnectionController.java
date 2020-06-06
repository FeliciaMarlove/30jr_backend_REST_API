package be.iramps.florencemary._30jd_back.controllers;

import be.iramps.florencemary._30jd_back.DTO.Connection;
import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.UserPost;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import be.iramps.florencemary._30jd_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST : connexion
 * /connection
 */
@RestController
@RequestMapping(value = "/connection")
@CrossOrigin
public class ConnectionController {
    private final UserService service;

    @Autowired
    private UserRepository repository;

    public ConnectionController(UserService service) {
        this.service = service;
    }

    /**
     * (POST) /connect
     * Connection
     * @param connection les données de connexion
     * @return DTOEntity l'utilisateur connecté
     */
    @PostMapping("/connect")
    public DTOEntity connect(@RequestBody Connection connection) {
        return this.service.login(connection);
    }

    /**
     * (POST) /signup
     * Inscription
     * @param user l'utilisateur (données d'inscription)
     * @return DTOEntity l'utilisateur créé
     */
    @PostMapping("/signup")
    public DTOEntity create(@RequestBody UserPost user) {
        return this.service.create(user);
    }
}
