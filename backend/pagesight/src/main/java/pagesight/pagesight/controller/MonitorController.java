package pagesight.pagesight.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import pagesight.pagesight.dto.MonitorRequest;
import pagesight.pagesight.dto.MonitorResponse;
import pagesight.pagesight.model.MonitorStatus;
import pagesight.pagesight.service.MonitorService;
import pagesight.pagesight.service.CurrentUserService;

@RestController
@RequestMapping("/api/users/me/monitors")
public class MonitorController {

    private final MonitorService monitorService;
    private final CurrentUserService currentUserService;

    public MonitorController(MonitorService monitorService, CurrentUserService currentUserService) {
        this.monitorService = monitorService;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ResponseEntity<MonitorResponse> createMonitor(@RequestBody MonitorRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        MonitorResponse response = monitorService.createMonitor(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MonitorResponse>> getMonitors() {
        Long userId = currentUserService.getCurrentUserId();
        return ResponseEntity.ok(monitorService.getMonitorsForUser(userId));
    }

    @GetMapping("/{monitorId}")
    public ResponseEntity<MonitorResponse> getMonitor(@PathVariable Long monitorId) {
        return ResponseEntity.ok(monitorService.getMonitor(monitorId));
    }

    @PatchMapping("/{monitorId}/favorite")
    public ResponseEntity<MonitorResponse> toggleFavorite(@PathVariable Long monitorId) {
        return ResponseEntity.ok(monitorService.toggleFavorite(monitorId));
    }

    @PatchMapping("/{monitorId}/status")
    public ResponseEntity<MonitorResponse> updateStatus(@PathVariable Long monitorId,
                                                        @RequestParam MonitorStatus status) {
        return ResponseEntity.ok(monitorService.updateStatus(monitorId, status));
    }

    // Lets the frontend trigger an immediate check instead of waiting for the scheduler.
    @PostMapping("/{monitorId}/check")
    public ResponseEntity<Void> runCheckNow(@PathVariable Long monitorId) {
        monitorService.runCheck(monitorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{monitorId}")
    public ResponseEntity<Void> deleteMonitor(@PathVariable Long monitorId) {
        monitorService.deleteMonitor(monitorId);
        return ResponseEntity.noContent().build();
    }
}
