package be.iramps.florencemary._30jd_back.repositories;

import be.iramps.florencemary._30jd_back.models.Notification;
import be.iramps.florencemary._30jd_back.models.Notification_Type;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    List<Notification> findAllByNotificationType(Notification_Type type);
}
