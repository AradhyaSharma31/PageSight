package pagesight.pagesight.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import pagesight.pagesight.dto.ElementSelectorRequest;
import pagesight.pagesight.dto.ElementSelectorResponse;
import pagesight.pagesight.dto.MonitorRequest;
import pagesight.pagesight.dto.MonitorResponse;
import pagesight.pagesight.exception.MonitorNotFoundException;
import pagesight.pagesight.exception.UserNotFoundException;
import pagesight.pagesight.model.CheckLog;
import pagesight.pagesight.model.ElementSelector;
import pagesight.pagesight.model.Monitor;
import pagesight.pagesight.model.MonitorStatus;
import pagesight.pagesight.model.User;
import pagesight.pagesight.repository.CheckLogRepository;
import pagesight.pagesight.repository.ElementSelectorRepository;
import pagesight.pagesight.repository.MonitorRepository;
import pagesight.pagesight.repository.UserRepository;

@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;
    private final ElementSelectorRepository elementSelectorRepository;
    private final CheckLogRepository checkLogRepository;
    private final UserRepository userRepository;
    private final PageFetchService pageFetchService;
    private final ChangeDetectionService changeDetectionService;
    private final NotificationService notificationService;

    public MonitorService(MonitorRepository monitorRepository,
                           ElementSelectorRepository elementSelectorRepository,
                           CheckLogRepository checkLogRepository,
                           UserRepository userRepository,
                           PageFetchService pageFetchService,
                           ChangeDetectionService changeDetectionService,
                           NotificationService notificationService) {
        this.monitorRepository = monitorRepository;
        this.elementSelectorRepository = elementSelectorRepository;
        this.checkLogRepository = checkLogRepository;
        this.userRepository = userRepository;
        this.pageFetchService = pageFetchService;
        this.changeDetectionService = changeDetectionService;
        this.notificationService = notificationService;
    }

    @Transactional
    public MonitorResponse createMonitor(Long userId, MonitorRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Monitor monitor = new Monitor(user, request.getName(), request.getUrl());
        if (request.getCheckIntervalMinutes() != null) {
            monitor.setCheckIntervalMinutes(request.getCheckIntervalMinutes());
        }
        monitor = monitorRepository.save(monitor);

        if (request.getElements() != null) {
            for (ElementSelectorRequest elementRequest : request.getElements()) {
                ElementSelector selector = new ElementSelector(
                        monitor, elementRequest.getSelectorPath(), elementRequest.getLabel());
                monitor.getElementSelectors().add(elementSelectorRepository.save(selector));
            }
        }

        return toResponse(monitor);
    }

    public List<MonitorResponse> getMonitorsForUser(Long userId) {
        return monitorRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public MonitorResponse getMonitor(Long monitorId) {
        return toResponse(findMonitorOrThrow(monitorId));
    }

    @Transactional
    public MonitorResponse toggleFavorite(Long monitorId) {
        Monitor monitor = findMonitorOrThrow(monitorId);
        monitor.setFavorite(!monitor.isFavorite());
        return toResponse(monitorRepository.save(monitor));
    }

    @Transactional
    public MonitorResponse updateStatus(Long monitorId, MonitorStatus status) {
        Monitor monitor = findMonitorOrThrow(monitorId);
        monitor.setStatus(status);
        return toResponse(monitorRepository.save(monitor));
    }

    @Transactional
    public void deleteMonitor(Long monitorId) {
        Monitor monitor = findMonitorOrThrow(monitorId);
        monitorRepository.delete(monitor);
    }

    @Transactional
    public void runCheck(Long monitorId) {
        Monitor monitor = findMonitorOrThrow(monitorId);
        String html = pageFetchService.fetchHtml(monitor.getUrl());

        boolean anyChanged = false;
        for (ElementSelector selector : monitor.getElementSelectors()) {
            String newContent = changeDetectionService.extractContent(html, selector.getSelectorPath());
            boolean changed = changeDetectionService.hasChanged(selector.getLastContentSnapshot(), newContent);

            checkLogRepository.save(new CheckLog(monitor, selector, changed));

            selector.setLastContentSnapshot(newContent);
            selector.setLastCheckedAt(LocalDateTime.now());
            elementSelectorRepository.save(selector);

            if (changed) {
                anyChanged = true;
            }
        }

        monitor.setLastCheckedAt(LocalDateTime.now());
        monitorRepository.save(monitor);

        if (anyChanged) {
            notificationService.notifyChange(monitor);
        }
    }

    @Transactional
    public ElementSelectorResponse addElement(Long monitorId, ElementSelectorRequest request) {
        Monitor monitor = findMonitorOrThrow(monitorId);
        ElementSelector selector = new ElementSelector(monitor, request.getSelectorPath(), request.getLabel());
        selector = elementSelectorRepository.save(selector);
        return new ElementSelectorResponse(
                selector.getId(), selector.getSelectorPath(), selector.getLabel(), selector.getLastCheckedAt());
    }

    @Transactional
    public void removeElement(Long elementId) {
        elementSelectorRepository.deleteById(elementId);
    }


    public String previewSelector(String url, String selectorPath) {
        String html = pageFetchService.fetchHtml(url);
        return changeDetectionService.extractContent(html, selectorPath);
    }

    private Monitor findMonitorOrThrow(Long monitorId) {
        return monitorRepository.findById(monitorId)
                .orElseThrow(() -> new MonitorNotFoundException(monitorId));
    }

    private MonitorResponse toResponse(Monitor monitor) {
        List<ElementSelectorResponse> elements = monitor.getElementSelectors().stream()
                .map(e -> new ElementSelectorResponse(
                        e.getId(), e.getSelectorPath(), e.getLabel(), e.getLastCheckedAt()))
                .collect(Collectors.toList());

        return new MonitorResponse(
                monitor.getId(),
                monitor.getName(),
                monitor.getUrl(),
                monitor.getStatus(),
                monitor.isFavorite(),
                monitor.getCheckIntervalMinutes(),
                monitor.getCreatedAt(),
                monitor.getLastCheckedAt(),
                elements
        );
    }
}
