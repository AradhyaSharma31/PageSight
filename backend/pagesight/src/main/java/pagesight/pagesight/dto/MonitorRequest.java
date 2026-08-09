package pagesight.pagesight.dto;

import java.util.List;

public class MonitorRequest {

    private String name;
    private String url;
    private Integer checkIntervalMinutes;
    private List<ElementSelectorRequest> elements;

    public MonitorRequest() {
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

    public Integer getCheckIntervalMinutes() {
        return checkIntervalMinutes;
    }

    public void setCheckIntervalMinutes(Integer checkIntervalMinutes) {
        this.checkIntervalMinutes = checkIntervalMinutes;
    }

    public List<ElementSelectorRequest> getElements() {
        return elements;
    }

    public void setElements(List<ElementSelectorRequest> elements) {
        this.elements = elements;
    }
}
