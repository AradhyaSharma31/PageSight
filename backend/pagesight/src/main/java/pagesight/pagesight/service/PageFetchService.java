package pagesight.pagesight.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class PageFetchService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public String fetchHtml(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .header("User-Agent", "PageSight-Monitor/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new PageFetchException(
                        "Fetch failed for " + url + " with status " + response.statusCode());
            }
            return response.body();
        } catch (PageFetchException e) {
            throw e;
        } catch (Exception e) {
            throw new PageFetchException("Failed to fetch " + url + ": " + e.getMessage());
        }
    }

    public static class PageFetchException extends RuntimeException {
        public PageFetchException(String message) {
            super(message);
        }
    }
}
