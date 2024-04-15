package mate.academy.rickandmorty.dto.external;

import java.util.List;

public record ExternalDto(
        PageInfoDto info,
        List<ExternalCharacterDto> results
) {
}
