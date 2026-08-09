package pagesight.pagesight.util;

public final class HtmlDiffUtil {

    private HtmlDiffUtil() {
    }

    public static boolean hasChanged(String previousContent, String newContent) {
        if (previousContent == null) {
            return newContent != null && !newContent.isBlank();
        }
        return !normalize(previousContent).equals(normalize(newContent));
    }

    public static String normalize(String content) {
        if (content == null) {
            return "";
        }
        return content.trim().replaceAll("\\s+", " ");
    }
}
