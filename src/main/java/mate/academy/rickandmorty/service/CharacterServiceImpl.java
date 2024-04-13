package mate.academy.rickandmorty.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.mappper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterDao;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterDao characterDao;
    private final CharacterMapper characterMapper;
    private final RickAndMortyCharactersClient charactersClient;
    private final Random random = new Random();
    private int countOfCharacters;

    @PostConstruct
    @Override
    public void saveAllExternalCharacters() {
        List<Character> characters = charactersClient.getExternalCharacters().stream()
                                               .map(characterMapper::toModel)
                                               .toList();
        countOfCharacters = characters.size();
        characterDao.saveAll(characters);
    }

    @Override
    public CharacterDto getRandomCharacter() {
        int randomId = random.nextInt(countOfCharacters);
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
