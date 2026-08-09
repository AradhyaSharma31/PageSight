package pagesight.pagesight.dto;

import java.time.LocalDateTime;
import java.util.List;
import pagesight.pagesight.model.MonitorStatus;

public class MonitorResponse {

    private Long id;
    private String name;
    private String url;
    private MonitorStatus status;
    private boolean favorite;
    private int checkIntervalMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime lastCheckedAt;
    private List<ElementSelectorResponse> elements;

    public MonitorResponse() {
    }

    public MonitorResponse(Long id, String name, String url, MonitorStatus status, boolean favorite,
                            int checkIntervalMinutes, LocalDateTime createdAt, LocalDateTime lastCheckedAt,
                            List<ElementSelectorResponse> elements) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.status = status;
        this.favorite = favorite;
        this.checkIntervalMinutes = checkIntervalMinutes;
        this.createdAt = createdAt;
        this.lastCheckedAt = lastCheckedAt;
        this.elements = elements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastCheckedAt() {
        return lastCheckedAt;
    }

    public void setLastCheckedAt(LocalDateTime lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public List<ElementSelectorResponse> getElements() {
        return elements;
    }

    public void setElements(List<ElementSelectorResponse> elements) {
        this.elements = elements;
    }
}
