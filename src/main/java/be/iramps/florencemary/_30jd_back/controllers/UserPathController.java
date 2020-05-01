package be.iramps.florencemary._30jd_back.controllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.UserPathPost;
import be.iramps.florencemary._30jd_back.models.PK_User_Path;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import be.iramps.florencemary._30jd_back.services.UserPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<DTOEntity> read() { return this.service.read(); }

    @PostMapping
    public DTOEntity create(@RequestBody UserPathPost userPath) { return this.service.create(userPath); }
}
