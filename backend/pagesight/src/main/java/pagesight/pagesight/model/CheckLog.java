package pagesight.pagesight.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "check_logs")
public class CheckLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    private Monitor monitor;

    // Nullable: a check can be at the whole-page level or scoped to one element.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_selector_id")
    private ElementSelector elementSelector;

    @Column(nullable = false)
    private boolean changed;

    @Column(name = "checked_at", nullable = false, updatable = false)
    private LocalDateTime checkedAt;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    public CheckLog() {
    }

    public CheckLog(Monitor monitor, ElementSelector elementSelector, boolean changed) {
        this.monitor = monitor;
        this.elementSelector = elementSelector;
        this.changed = changed;
    }

    @PrePersist
    protected void onCreate() {
        this.checkedAt = LocalDateTime.now();
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

    public ElementSelector getElementSelector() {
        return elementSelector;
    }

    public void setElementSelector(ElementSelector elementSelector) {
        this.elementSelector = elementSelector;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
