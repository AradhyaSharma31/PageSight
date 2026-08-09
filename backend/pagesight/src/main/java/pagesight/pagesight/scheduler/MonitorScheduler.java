package pagesight.pagesight.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import pagesight.pagesight.model.Monitor;
import pagesight.pagesight.model.MonitorStatus;
import pagesight.pagesight.repository.MonitorRepository;
import pagesight.pagesight.service.MonitorService;

@Component
public class MonitorScheduler {

    private static final Logger log = LoggerFactory.getLogger(MonitorScheduler.class);

    private final MonitorRepository monitorRepository;
    private final MonitorService monitorService;

    public MonitorScheduler(MonitorRepository monitorRepository, MonitorService monitorService) {
        this.monitorRepository = monitorRepository;
        this.monitorService = monitorService;
    }

    @Scheduled(fixedRate = 60_000)
    public void checkDueMonitors() {
        List<Monitor> activeMonitors = monitorRepository.findByStatus(MonitorStatus.ACTIVE);

        for (Monitor monitor : activeMonitors) {
            if (isDue(monitor)) {
                try {
                    monitorService.runCheck(monitor.getId());
                } catch (Exception e) {
                    log.warn("Check failed for monitor {}: {}", monitor.getId(), e.getMessage());
                }
            }
        }
    }

    private boolean isDue(Monitor monitor) {
        if (monitor.getLastCheckedAt() == null) {
            return true;
        }
        long minutesSinceLastCheck = ChronoUnit.MINUTES.between(monitor.getLastCheckedAt(), LocalDateTime.now());
        return minutesSinceLastCheck >= monitor.getCheckIntervalMinutes();
    }
}
