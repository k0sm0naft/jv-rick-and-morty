package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/characters")
@Tag(name = "Characters from \"Rick and Morty\"",
        description = "Endpoints for getting information about characters from \"Rick and Morty\"")
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    @Operation(summary = "Return character info",
            description = "Return single random character info")
    public CharacterDto getRandomCharacter() {
        return characterService.getRandomCharacter();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Return list of characters",
            description = "Return list of characters info that contains word in their names")
    public List<CharacterDto> searchByName(
            @PathVariable @Parameter(description = "Part of characters name") String name) {
        return characterService.findByName(name);
    }
}
