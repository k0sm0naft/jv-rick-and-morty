package mate.academy.rickandmorty.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.client.RickAndMortyCharactersClient;
import mate.academy.rickandmorty.dto.external.ExternalDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.mappper.CharacterMapper;
import mate.academy.rickandmorty.repository.CharacterDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    public static final String CHARACTERS_ENDPOINT_PATH = "/api/character";
    private final CharacterDao characterDao;
    private final CharacterMapper characterMapper;
    private final RickAndMortyCharactersClient charactersClient;
    private final Random random;
    @Value("${rickandmorty.url}")
    private String baseUrl;

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void saveAllExternalCharacters() {
        String fullUrl = baseUrl + CHARACTERS_ENDPOINT_PATH;
        while (fullUrl != null) {
            ExternalDto externalDto = charactersClient.getExternalDto(fullUrl);
            characterDao.saveAll(externalDto.results().stream()
                    .map(characterMapper::toModel)
                    .toList());
            fullUrl = externalDto.info().next();
        }
    }

    @Override
    public CharacterDto getRandomCharacter() {
        long randomId = random.nextLong(characterDao.count());
        return characterDao.findById(randomId)
                           .map(characterMapper::toDto)
                           .orElseThrow(() -> new NoSuchElementException(
                                   "Can't get random character by id " + randomId));
    }

    @Override
    public List<CharacterDto> findByName(String name) {
        return characterDao.findAllByNameContains(name).stream()
                           .map(characterMapper::toDto)
                           .toList();
    }
}
