package be.iramps.florencemary._30jd_back.controllers.enduserControllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.UserPathPost;
import be.iramps.florencemary._30jd_back.repositories.UserPathRepository;
import be.iramps.florencemary._30jd_back.services.UserPathService;
import be.iramps.florencemary._30jd_back.DTO.UserPathHistoryObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur Admin (CRUD sur les parcours)
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

    @GetMapping
    public List<DTOEntity> read() { return this.service.read(); }

    @PostMapping
    public DTOEntity create(@RequestBody UserPathPost userPath) { return this.service.create(userPath); }

    @GetMapping("/{userId}")
    public DTOEntity read(@PathVariable("userId") Integer userId) {
        return this.service.seeTaskOfTheDay(userId);
    }

    @GetMapping("/{userId}/history")
    public List<UserPathHistoryObj> listHistory(@PathVariable("userId") Integer userId) {
        return this.service.listHistory(userId);
    }

    @GetMapping("/{userId}/today")
    public DTOEntity seeTaskOfDay(@PathVariable("userId") Integer userId) {
        return this.service.seeTaskOfTheDay(userId);
    }

    @GetMapping("/paths")
    public List<DTOEntity> seeAvailablePaths() {
        return this.service.readPaths();
    }
}
