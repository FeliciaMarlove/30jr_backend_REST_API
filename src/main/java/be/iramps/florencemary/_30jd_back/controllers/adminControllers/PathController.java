package be.iramps.florencemary._30jd_back.controllers.adminControllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.Message;
import be.iramps.florencemary._30jd_back.DTO.PathPost;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.services.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST : Parcours
 * /admin/api/path
 */
@RestController
@RequestMapping(value = "/admin/api/path")
@CrossOrigin
public class PathController {
    private final PathService service;

    @Autowired
    private PathRepository repository;

    public PathController(PathService service) {
        this.service = service;
    }

    /**
     * (GET)
     * @return List DTOEntity tous les parcours
     */
    @GetMapping
    public List<DTOEntity> read() {
        return this.service.read();
    }

    /**
     * (GET) /admin/api/path/{id}
     * @param id l'ID du parcours
     * @return DTOEntity un parcours
     */
    @GetMapping("/{id}")
    public DTOEntity read(@PathVariable("id") Integer id) {
        return this.service.read(id);
    }

    /**
     * (GET) /{id}/tasks
     * @param id l'ID du parcours
     * @return ListDTOEntity la liste des tâches du parcours
     */
    @GetMapping("/{id}/tasks")
    public List<DTOEntity> listTasks(@PathVariable("id") Integer id) { return this.service.listTasks(id); }

    /**
     * (POST)
     * @param path le parcours à créer
     * @return DTOEntity le parcours créé
     */
    @PostMapping
    public DTOEntity create(@RequestBody PathPost path) {
        return this.service.create(path);
    }

    /**
     * (PUT) /{id}
     * @param id l'ID du parcours à modifier
     * @param path le DTO du parcours à mettre à jour
     * @return DTOEntity le parcours mis à jour
     */
    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable("id") Integer id, @RequestBody PathPost path) {
        return this.service.update(id, path);
    }

    /**
     * (DELETE) /{id}
     * @param id l'ID du parcours à désactiver
     * @return DTOEntity le parcours désactivé
     */
    @DeleteMapping("/{id}")
    public DTOEntity delete(@PathVariable("id") Integer id) {
        return this.service.delete(id);
    }

    /**
     * (DELETE) /{id}/activate
     * @param id l'ID du parcours à activer
     * @return DTOEntity le parcours activé
     */
    @DeleteMapping("/{id}/activate")
    public DTOEntity activate(@PathVariable("id") Integer id) {
        return this.service.activate(id);
    }

    /**
     * (GET) /{id}/add/{taskId}/{index}
     * @param id l'ID du parcours
     * @param taskId l'ID de la tâche à ajouter dans le parcours
     * @param index l'index de position de la tâche dans le parcours
     * @return Message un message de réussite/d'échec
     */
    @GetMapping("/{id}/add/{taskId}/{index}")
    public Message addTask(@PathVariable("id") Integer id, @PathVariable("taskId") Integer taskId, @PathVariable("index") Integer index) {
        return this.service.addTask(id, taskId, index);
    }

    /**
     * (GET) /{id}/remove/{taskId}
     * @param id l'ID du parcours
     * @param taskId l'ID de la tâche à enlever du parcours
     * @return Message un message de réussite/d'échec
     */
    @GetMapping("/{id}/remove/{taskId}")
    public Message removeTask(@PathVariable("id") Integer id, @PathVariable("taskId") Integer taskId) {
        return this.service.removeTask(id, taskId);
    }
}
