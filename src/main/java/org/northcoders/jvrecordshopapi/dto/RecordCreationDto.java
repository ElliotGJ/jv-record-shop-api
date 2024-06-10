package org.northcoders.jvrecordshopapi.dto;

import java.time.Year;
import java.util.List;

public record RecordCreationDto(
        String name,
        List<Long> artistIds,
        Year releaseYear,
        List<String> genres,
        int Stock)
{
}
