package be.iramps.florencemary._30jd_back.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notification", schema = "public", catalog = "_30jd")
public class Notification implements Serializable, Comparable<Notification> {
    /*
    locale / push / intro -> boolean field in User (has seen / must see ...) -> "generics"
    day_triggered -> linked to UserPath (1-1 relation -> aggrégation)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_generator")
    @SequenceGenerator(name = "notification_generator", allocationSize = 1, initialValue = 1)
    @Column(name = "notification_id")
    private Integer notificationId;

    @Column(name = "notification_title")
    private String notificationTitle;

    @Column(name = "notification_body", length = 1000)
    private String notificationBody;

    /*
    first possible day to display the notif (for DAY_TRIGGERED)
     */
    @Column(name = "notification_trigger_day")
    private int notificationTriggerDay;

    /*
    for INTRO to display in the right order
     */
    @Column(name = "notification_position")
    private Integer notificationPosition;

    @Column(name = "notification_type")
    private Notification_Type notificationType;

    public Integer getNotificationId() {
        return notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public int getNotificationTriggerDay() {
        return notificationTriggerDay;
    }

    public Notification_Type getNotificationType() {
        return notificationType;
    }

    public Integer getNotificationPosition() {
        return notificationPosition;
    }

    public Notification(String notificationTitle, String notificationBody, int notificationTriggerDay, Notification_Type notificationType) {
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.notificationTriggerDay = notificationTriggerDay;
        this.notificationType = notificationType;
    }

    public Notification() {
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationBody='" + notificationBody + '\'' +
                ", notificationTriggerDay=" + notificationTriggerDay +
                ", notificationPosition=" + notificationPosition +
                ", notificationType=" + notificationType +
                '}';
    }

    @Override
    public int compareTo(Notification o) {
        return this.getNotificationPosition().compareTo(o.getNotificationPosition());
    }
}
