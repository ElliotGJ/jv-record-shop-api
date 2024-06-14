package org.northcoders.jvrecordshopapi.dto;

import jakarta.validation.constraints.*;
import lombok.NonNull;

import java.time.Year;
import java.util.List;

public record RecordCreationDto(
        @NotBlank(message = "Name should contain a value") String name,
        @NotNull(message = "Artists should contain a value") List<Long> artistIds,
        @NotNull(message = "releaseYear should contain a value") Year releaseYear,
        @NotNull(message = "Genres should contain a value") List<String> genres,
        @Min(value = 0, message = "Stock must be greater than 0") Integer stock)
{
}
