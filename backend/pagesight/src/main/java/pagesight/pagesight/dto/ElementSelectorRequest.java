package pagesight.pagesight.dto;

public class ElementSelectorRequest {

    private String selectorPath;
    private String label;

    public ElementSelectorRequest() {
    }

    public ElementSelectorRequest(String selectorPath, String label) {
        this.selectorPath = selectorPath;
        this.label = label;
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
}
