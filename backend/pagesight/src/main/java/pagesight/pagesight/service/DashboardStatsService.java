package pagesight.pagesight.service;

import org.springframework.stereotype.Service;
import pagesight.pagesight.dto.DashboardStatsResponse;
import pagesight.pagesight.model.MonitorStatus;
import pagesight.pagesight.repository.CheckLogRepository;
import pagesight.pagesight.repository.MonitorRepository;
import pagesight.pagesight.repository.NotificationRepository;

@Service
public class DashboardStatsService {

    private final MonitorRepository monitorRepository;
    private final CheckLogRepository checkLogRepository;
    private final NotificationRepository notificationRepository;

    public DashboardStatsService(MonitorRepository monitorRepository,
                                  CheckLogRepository checkLogRepository,
                                  NotificationRepository notificationRepository) {
        this.monitorRepository = monitorRepository;
        this.checkLogRepository = checkLogRepository;
        this.notificationRepository = notificationRepository;
    }

    public DashboardStatsResponse getStatsForUser(Long userId) {
        long totalMonitors = monitorRepository.countByUserId(userId);

        long activeMonitors = monitorRepository.findByUserId(userId).stream()
                .filter(m -> m.getStatus() == MonitorStatus.ACTIVE)
                .count();

        long favoriteMonitors = monitorRepository.findByUserIdAndFavoriteTrue(userId).size();

        long totalChecksPerformed = monitorRepository.findByUserId(userId).stream()
                .mapToLong(m -> checkLogRepository.countByMonitorId(m.getId()))
                .sum();

        long unreadNotifications = notificationRepository.countByUserIdAndReadFalse(userId);

        return new DashboardStatsResponse(
                totalMonitors, activeMonitors, favoriteMonitors, totalChecksPerformed, unreadNotifications);
    }
}
