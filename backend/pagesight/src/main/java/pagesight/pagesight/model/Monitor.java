package pagesight.pagesight.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monitors")
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 2048)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MonitorStatus status = MonitorStatus.ACTIVE;

    @Column(name = "is_favorite", nullable = false)
    private boolean favorite = false;

    @Column(name = "check_interval_minutes", nullable = false)
    private int checkIntervalMinutes = 30;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_checked_at")
    private LocalDateTime lastCheckedAt;

    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElementSelector> elementSelectors = new ArrayList<>();

    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckLog> checkLogs = new ArrayList<>();

    public Monitor() {
    }

    public Monitor(User user, String name, String url) {
        this.user = user;
        this.name = name;
        this.url = url;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MonitorStatus getStatus() {
        return status;
    }

    public void setStatus(MonitorStatus status) {
        this.status = status;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getCheckIntervalMinutes() {
        return checkIntervalMinutes;
    }

    public void setCheckIntervalMinutes(int checkIntervalMinutes) {
        this.checkIntervalMinutes = checkIntervalMinutes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastCheckedAt() {
        return lastCheckedAt;
    }

    public void setLastCheckedAt(LocalDateTime lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public List<ElementSelector> getElementSelectors() {
        return elementSelectors;
    }

    public void setElementSelectors(List<ElementSelector> elementSelectors) {
        this.elementSelectors = elementSelectors;
    }

    public List<CheckLog> getCheckLogs() {
        return checkLogs;
    }

    public void setCheckLogs(List<CheckLog> checkLogs) {
        this.checkLogs = checkLogs;
    }
}