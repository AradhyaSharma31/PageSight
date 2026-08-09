package pagesight.pagesight.dto;

import java.time.LocalDateTime;

public class ElementSelectorResponse {

    private Long id;
    private String selectorPath;
    private String label;
    private LocalDateTime lastCheckedAt;

    public ElementSelectorResponse() {
    }

    public ElementSelectorResponse(Long id, String selectorPath, String label, LocalDateTime lastCheckedAt) {
        this.id = id;
        this.selectorPath = selectorPath;
        this.label = label;
        this.lastCheckedAt = lastCheckedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getLastCheckedAt() {
        return lastCheckedAt;
    }

    public void setLastCheckedAt(LocalDateTime lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }
}
