package be.iramps.florencemary._30jd_back.controllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.TaskPost;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import be.iramps.florencemary._30jd_back.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/task")
@CrossOrigin
public class TaskController {
    private final TaskService service;

    @Autowired
    private TaskRepository taskRepository;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<DTOEntity> read() { return this.service.read(); }

    @GetMapping("/{id}")
    public DTOEntity read(@PathVariable("id") Integer id) {
        return this.service.read(id);
    }

    @PostMapping
    public DTOEntity create(@RequestBody TaskPost task) {
        return this.service.create(task);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable("id") Integer id, @RequestBody TaskPost task) {
        return this.service.update(id, task);
    }

    @DeleteMapping("/{id}")
    public DTOEntity delete(@PathVariable("id") Integer id) {
        return this.service.delete(id);
    }

    @DeleteMapping("/{id}/activate")
    public DTOEntity activate(@PathVariable("id") Integer id) { return this.service.activate(id); }
}
