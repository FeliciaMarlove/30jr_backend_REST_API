package be.iramps.florencemary._30jd_back.controllers.adminControllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.TaskPost;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import be.iramps.florencemary._30jd_back.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST : Tâches
 * /admin/api/task
 */
@RestController
@RequestMapping(value = "/admin/api/task")
@CrossOrigin
public class TaskController {
    private final TaskService service;

    @Autowired
    private TaskRepository taskRepository;

    public TaskController(TaskService service) {
        this.service = service;
    }

    /**
     * (GET)
     * @return List DTOEntity la liste des tâches
     */
    @GetMapping
    public List<DTOEntity> read() { return this.service.read(); }

    /**
     * (GET) /{id}
     * @param id l'ID de la tâche
     * @return DTOEntity la tâche
     */
    @GetMapping("/{id}")
    public DTOEntity read(@PathVariable("id") Integer id) {
        return this.service.read(id);
    }

    /**
     * (POST)
     * @param task la tâche à créer
     * @return DTOEntity la tâche créée
     */
    @PostMapping
    public DTOEntity create(@RequestBody TaskPost task) {
        return this.service.create(task);
    }

    /**
     * (PUT) /{id}
     * @param id l'ID de la tâche à modifier
     * @param task la tâche à mettre à jour
     * @return DTOEntity la tâche mise à jour
     */
    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable("id") Integer id, @RequestBody TaskPost task) {
        return this.service.update(id, task);
    }

    /**
     * (DELETE) /{id}
     * @param id l'ID de la tâche à désactiver
     * @return DTOEntity la tâche désactivée
     */
    @DeleteMapping("/{id}")
    public DTOEntity delete(@PathVariable("id") Integer id) {
        return this.service.delete(id);
    }

    /**
     * (DELETE) /{id}/activate
     * @param id l'ID de la tâche à activer
     * @return DTOEntity la tâche activée
     */
    @DeleteMapping("/{id}/activate")
    public DTOEntity activate(@PathVariable("id") Integer id) { return this.service.activate(id); }
}
