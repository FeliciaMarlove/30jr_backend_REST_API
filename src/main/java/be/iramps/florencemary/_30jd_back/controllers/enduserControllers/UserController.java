package be.iramps.florencemary._30jd_back.controllers.enduserControllers;

import be.iramps.florencemary._30jd_back.DTO.DTOEntity;
import be.iramps.florencemary._30jd_back.DTO.UserPost;
import be.iramps.florencemary._30jd_back.models.Notification;
import be.iramps.florencemary._30jd_back.models.Notification_Type;
import be.iramps.florencemary._30jd_back.repositories.NotificationRepository;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import be.iramps.florencemary._30jd_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @GetMapping("/{id}")
    public DTOEntity read(@PathVariable("id") Integer id) { return this.service.read(id); }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable("id") Integer id, @RequestBody UserPost user) {
        return this.service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public DTOEntity delete(@PathVariable("id") Integer id) {
        return this.service.delete(id);
    }

    /*
    Notif ou null
     */
    @GetMapping("/{id}/locale")
    public Notification readLocaleNotif(@PathVariable("id") Integer id) {
        return this.service.readLocale(id);
    }

    /*
    Notif ou null
     */
    @GetMapping("/{id}/intro")
    public List<Notification> readIntroNotifs(@PathVariable("id") Integer id) {
        return this.service.readIntro(id);
    }
}
