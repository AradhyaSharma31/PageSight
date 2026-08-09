package pagesight.pagesight.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import pagesight.pagesight.dto.NotificationResponse;
import pagesight.pagesight.exception.NotificationNotFoundException;
import pagesight.pagesight.model.Monitor;
import pagesight.pagesight.model.Notification;
import pagesight.pagesight.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void notifyChange(Monitor monitor) {
        String message = "\"" + monitor.getName() + "\" changed — a monitored element was updated.";
        Notification notification = new Notification(monitor.getUser(), monitor, message);
        notificationRepository.save(notification);
    }

    public List<NotificationResponse> getNotificationsForUser(Long userId, boolean unreadOnly) {
        List<Notification> notifications = unreadOnly
                ? notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId)
                : notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);

        return notifications.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public long countUnread(Long userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(
                n.getId(),
                n.getMonitor().getId(),
                n.getMonitor().getName(),
                n.getMessage(),
                n.isRead(),
                n.getCreatedAt()
        );
    }
}
