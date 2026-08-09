package pagesight.pagesight.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pagesight.pagesight.dto.DashboardStatsResponse;
import pagesight.pagesight.service.DashboardStatsService;

@RestController
@RequestMapping("/api/users/{userId}/dashboard")
public class DashboardController {

    private final DashboardStatsService dashboardStatsService;

    public DashboardController(DashboardStatsService dashboardStatsService) {
        this.dashboardStatsService = dashboardStatsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getStats(@PathVariable Long userId) {
        return ResponseEntity.ok(dashboardStatsService.getStatsForUser(userId));
    }
}
