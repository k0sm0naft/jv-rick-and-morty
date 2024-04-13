package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.internal.CharacterDto;

public interface CharacterService {
    void saveAllExternalCharacters();

    CharacterDto getRandomCharacter();

    List<CharacterDto> findByName(String name);
}
