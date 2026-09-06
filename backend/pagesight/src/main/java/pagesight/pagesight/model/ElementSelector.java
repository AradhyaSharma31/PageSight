package pagesight.pagesight.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "element_selectors")
public class ElementSelector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    private Monitor monitor;

    @Column(name = "selector_path", nullable = false, length = 1000)
    private String selectorPath;

    @Column(name = "label", length = 150)
    private String label;

    @Column(name = "last_content_snapshot", columnDefinition = "TEXT")
    private String lastContentSnapshot;

    @Column(name = "last_checked_at")
    private LocalDateTime lastCheckedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ElementSelector() {
    }

    public ElementSelector(Monitor monitor, String selectorPath, String label) {
        this.monitor = monitor;
        this.selectorPath = selectorPath;
        this.label = label;
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

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public String getSelectorPath() {
        return selectorPath;
    }

    public void setSelectorPath(String selectorPath) {
        this.selectorPath = selectorPath;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLastContentSnapshot() {
        return lastContentSnapshot;
    }

    public void setLastContentSnapshot(String lastContentSnapshot) {
        this.lastContentSnapshot = lastContentSnapshot;
    }

    public LocalDateTime getLastCheckedAt() {
        return lastCheckedAt;
    }

    public void setLastCheckedAt(LocalDateTime lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
