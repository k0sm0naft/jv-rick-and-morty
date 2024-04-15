package mate.academy.rickandmorty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.ExternalDto;
import mate.academy.rickandmorty.exception.InvalidApiResponseException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RickAndMortyCharactersClient {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public ExternalDto getExternalDto(String url) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                             .GET()
                                             .uri(URI.create(url))
                                             .build();
        try {
            HttpResponse<String> response =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), ExternalDto.class);
        } catch (IOException e) {
            throw new InvalidApiResponseException(
                    "Can't get response from API by URL: " + url, e);
        } catch (InterruptedException e) {
            throw new InvalidApiResponseException(
                    "Interrupted to get response from API by URL: " + url, e);
        }

    }
}
