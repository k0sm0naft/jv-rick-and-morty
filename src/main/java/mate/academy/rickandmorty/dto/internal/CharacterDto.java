package mate.academy.rickandmorty.dto.internal;

public record CharacterDto(
        Integer id,
        Integer externalId,
        String name,
        String status,
        String gender
) {
}
