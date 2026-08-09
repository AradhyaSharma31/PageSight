package pagesight.pagesight.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import pagesight.pagesight.dto.MonitorRequest;
import pagesight.pagesight.dto.MonitorResponse;
import pagesight.pagesight.model.MonitorStatus;
import pagesight.pagesight.service.MonitorService;

@RestController
@RequestMapping("/api/users/{userId}/monitors")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping
    public ResponseEntity<MonitorResponse> createMonitor(@PathVariable Long userId,
                                                         @RequestBody MonitorRequest request) {
        MonitorResponse response = monitorService.createMonitor(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MonitorResponse>> getMonitors(@PathVariable Long userId) {
        return ResponseEntity.ok(monitorService.getMonitorsForUser(userId));
    }

    @GetMapping("/{monitorId}")
    public ResponseEntity<MonitorResponse> getMonitor(@PathVariable Long userId,
                                                        @PathVariable Long monitorId) {
        return ResponseEntity.ok(monitorService.getMonitor(monitorId));
    }

    @PatchMapping("/{monitorId}/favorite")
    public ResponseEntity<MonitorResponse> toggleFavorite(@PathVariable Long userId,
                                                            @PathVariable Long monitorId) {
        return ResponseEntity.ok(monitorService.toggleFavorite(monitorId));
    }

    @PatchMapping("/{monitorId}/status")
    public ResponseEntity<MonitorResponse> updateStatus(@PathVariable Long userId,
                                                          @PathVariable Long monitorId,
                                                          @RequestParam MonitorStatus status) {
        return ResponseEntity.ok(monitorService.updateStatus(monitorId, status));
    }

    // Lets the frontend trigger an immediate check instead of waiting for the scheduler.
    @PostMapping("/{monitorId}/check")
    public ResponseEntity<Void> runCheckNow(@PathVariable Long userId,
                                             @PathVariable Long monitorId) {
        monitorService.runCheck(monitorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{monitorId}")
    public ResponseEntity<Void> deleteMonitor(@PathVariable Long userId,
                                               @PathVariable Long monitorId) {
        monitorService.deleteMonitor(monitorId);
        return ResponseEntity.noContent().build();
    }
}
