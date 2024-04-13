package mate.academy.rickandmorty.dto.external;

import java.util.List;

public record ExternalDto(
        InfoDto info,
        List<ExternalCharacterDto> results
) {
}
