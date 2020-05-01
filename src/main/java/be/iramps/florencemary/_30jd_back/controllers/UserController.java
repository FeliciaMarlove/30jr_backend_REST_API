package be.iramps.florencemary._30jd_back.controllers;

import be.iramps.florencemary._30jd_back.DTO.Connection;
import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.UserPost;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import be.iramps.florencemary._30jd_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<DTOEntity> read() { return this.service.read(); }

    @GetMapping("/{id}")
    public DTOEntity read(@PathVariable("id") Integer id) { return this.service.read(id); }

    @PostMapping
    public DTOEntity create(@RequestBody UserPost user) {
        return this.service.create(user);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable("id") Integer id, @RequestBody UserPost user) {
        return this.service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public DTOEntity delete(@PathVariable("id") Integer id) {
        return this.service.delete(id);
    }

    @PostMapping("/connect")
    public DTOEntity connect(@RequestBody Connection connection) {
        return this.service.login(connection);
    }
}
