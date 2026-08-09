package pagesight.pagesight.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pagesight.pagesight.util.HtmlDiffUtil;

@Service
public class ChangeDetectionService {

    private static final Pattern TAG_BY_ATTRIBUTE = Pattern.compile(
            "<[^>]*(?:id|class)=[\"'][^\"']*%s[^\"']*[\"'][^>]*>(.*?)</[^>]+>",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    private static final Pattern STRIP_TAGS = Pattern.compile("<[^>]+>");

    public String extractContent(String rawHtml, String selectorPath) {
        if (rawHtml == null) {
            return "";
        }
        String hint = extractHintToken(selectorPath);
        if (hint != null && !hint.isBlank()) {
            Pattern pattern = Pattern.compile(String.format(TAG_BY_ATTRIBUTE.pattern(), Pattern.quote(hint)),
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher matcher = pattern.matcher(rawHtml);
            if (matcher.find()) {
                return stripTags(matcher.group(1));
            }
        }
        return stripTags(rawHtml);
    }

    public boolean hasChanged(String previousSnapshot, String newContent) {
        return HtmlDiffUtil.hasChanged(previousSnapshot, newContent);
    }

    private String stripTags(String html) {
        return HtmlDiffUtil.normalize(STRIP_TAGS.matcher(html).replaceAll(" "));
    }

    private String extractHintToken(String selectorPath) {
        if (selectorPath == null) {
            return null;
        }
        return selectorPath.replaceFirst("^[.#]", "").trim();
    }
}
