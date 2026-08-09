package pagesight.pagesight.dto;

public class DashboardStatsResponse {

    private long totalMonitors;
    private long activeMonitors;
    private long favoriteMonitors;
    private long totalChecksPerformed;
    private long unreadNotifications;

    public DashboardStatsResponse() {
    }

    public DashboardStatsResponse(long totalMonitors, long activeMonitors, long favoriteMonitors,
                                   long totalChecksPerformed, long unreadNotifications) {
        this.totalMonitors = totalMonitors;
        this.activeMonitors = activeMonitors;
        this.favoriteMonitors = favoriteMonitors;
        this.totalChecksPerformed = totalChecksPerformed;
        this.unreadNotifications = unreadNotifications;
    }

    public long getTotalMonitors() {
        return totalMonitors;
    }

    public void setTotalMonitors(long totalMonitors) {
        this.totalMonitors = totalMonitors;
    }

    public long getActiveMonitors() {
        return activeMonitors;
    }

    public void setActiveMonitors(long activeMonitors) {
        this.activeMonitors = activeMonitors;
    }

    public long getFavoriteMonitors() {
        return favoriteMonitors;
    }

    public void setFavoriteMonitors(long favoriteMonitors) {
        this.favoriteMonitors = favoriteMonitors;
    }

    public long getTotalChecksPerformed() {
        return totalChecksPerformed;
    }

    public void setTotalChecksPerformed(long totalChecksPerformed) {
        this.totalChecksPerformed = totalChecksPerformed;
    }

    public long getUnreadNotifications() {
        return unreadNotifications;
    }

    public void setUnreadNotifications(long unreadNotifications) {
        this.unreadNotifications = unreadNotifications;
    }
}
