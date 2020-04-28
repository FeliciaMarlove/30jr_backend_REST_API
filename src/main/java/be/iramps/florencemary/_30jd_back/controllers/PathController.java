package be.iramps.florencemary._30jd_back.controllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.PathPost;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.services.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/path")
@CrossOrigin
public class PathController {
    private final PathService service;

    @Autowired
    private PathRepository repository;

    public PathController(PathService service) {
        this.service = service;
    }

    @GetMapping
    public List<DTOEntity> read() {
        return this.service.read();
    }

    @PostMapping
    public DTOEntity create(@RequestBody PathPost path) {
        return this.service.create(path);
    }
}
