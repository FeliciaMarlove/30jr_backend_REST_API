package be.iramps.florencemary._30jd_back.controllers;

import be.iramps.florencemary._30jd_back.DTO.Connection;
import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.UserPost;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import be.iramps.florencemary._30jd_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/connexion")
@CrossOrigin
public class ConnectionController {
    private final UserService service;

    @Autowired
    private UserRepository repository;

    public ConnectionController(UserService service) {
        this.service = service;
    }

    @PostMapping("/connect")
    public DTOEntity connect(@RequestBody Connection connection) {
        return this.service.login(connection);
    }

    @PostMapping("/signup")
    public DTOEntity create(@RequestBody UserPost user) {
        return this.service.create(user);
    }
}
