package mate.academy.rickandmorty.dto.external;

public record PageInfoDto(
        Integer count,
        Integer pages,
        String next,
        String prev
) {
}
