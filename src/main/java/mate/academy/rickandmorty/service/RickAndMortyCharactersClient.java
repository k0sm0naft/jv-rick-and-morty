package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.ExternalCharacterDto;
import mate.academy.rickandmorty.dto.external.ExternalDto;
import mate.academy.rickandmorty.exception.InvalidApiResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RickAndMortyCharactersClient {
    private final ObjectMapper objectMapper;
    @Value("${api.characters.url}")
    private String baseUrl;

    public List<ExternalCharacterDto> getExternalCharacters() {
        List<ExternalCharacterDto> externalCharacters = new ArrayList<>();

        while (baseUrl != null) {
            ExternalDto externalDto = getExternalDto(baseUrl);
            externalCharacters.addAll(externalDto.results());
            baseUrl = externalDto.info().next();
        }

        return externalCharacters;
    }

    private ExternalDto getExternalDto(String url) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                             .GET()
                                             .uri(URI.create(url))
                                             .build();
        try {
            HttpResponse<String> response =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), ExternalDto.class);
        } catch (IOException | InterruptedException e) {
            throw new InvalidApiResponseException(
                    "Can't get response from API by URL: " + url, e);
        }

    }
}
