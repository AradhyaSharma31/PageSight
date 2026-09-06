package pagesight.pagesight.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import pagesight.pagesight.dto.NotificationResponse;
import pagesight.pagesight.service.NotificationService;
import pagesight.pagesight.service.CurrentUserService;

@RestController
@RequestMapping("/api/users/me/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final CurrentUserService currentUserService;

    public NotificationController(NotificationService notificationService, CurrentUserService currentUserService) {
        this.notificationService = notificationService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @RequestParam(defaultValue = "false") boolean unreadOnly) {
        Long userId = currentUserService.getCurrentUserId();
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId, unreadOnly));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount() {
        Long userId = currentUserService.getCurrentUserId();
        return ResponseEntity.ok(notificationService.countUnread(userId));
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }
}
