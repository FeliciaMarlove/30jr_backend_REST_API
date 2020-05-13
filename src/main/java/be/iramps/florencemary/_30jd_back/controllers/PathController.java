package be.iramps.florencemary._30jd_back.controllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.Message;
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

    @GetMapping("/{id}")
    public DTOEntity read(@PathVariable("id") Integer id) {
        return this.service.read(id);
    }

    @GetMapping("/{id}/tasks")
    public List<DTOEntity> listTasks(@PathVariable("id") Integer id) { return this.service.listTasks(id); }

    @PostMapping
    public DTOEntity create(@RequestBody PathPost path) {
        return this.service.create(path);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable("id") Integer id, @RequestBody PathPost path) {
        return this.service.update(id, path);
    }

    @DeleteMapping("/{id}")
    public DTOEntity delete(@PathVariable("id") Integer id) {
        return this.service.delete(id);
    }

    @DeleteMapping("/{id}/activate")
    public DTOEntity activate(@PathVariable("id") Integer id) {
        return this.service.activate(id);
    }

    @GetMapping("/{id}/add/{taskId}/{index}")
    public Message addTask(@PathVariable("id") Integer id, @PathVariable("taskId") Integer taskId, @PathVariable("index") Integer index) {
        return this.service.addTask(id, taskId, index);
    }

    @GetMapping("/{id}/remove/{taskId}")
    public Message removeTask(@PathVariable("id") Integer id, @PathVariable("taskId") Integer taskId) {
        return this.service.removeTask(id, taskId);
    }
}
