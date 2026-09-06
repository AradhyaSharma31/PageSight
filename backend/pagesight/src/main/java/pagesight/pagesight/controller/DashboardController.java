package pagesight.pagesight.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pagesight.pagesight.dto.DashboardStatsResponse;
import pagesight.pagesight.service.DashboardStatsService;
import pagesight.pagesight.service.CurrentUserService;

@RestController
@RequestMapping("/api/users/me/dashboard")
public class DashboardController {

    private final DashboardStatsService dashboardStatsService;
    private final CurrentUserService currentUserService;

    public DashboardController(DashboardStatsService dashboardStatsService, CurrentUserService currentUserService) {
        this.dashboardStatsService = dashboardStatsService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getStats() {
        Long userId = currentUserService.getCurrentUserId();
        return ResponseEntity.ok(dashboardStatsService.getStatsForUser(userId));
    }
}
