package org.northcoders.jvrecordshopapi.dto;

import java.time.Year;
import java.util.List;

public record RecordDTO(
        long id,
        String name,
        List<String> artists,
        Year releaseYear,
        List<String> genres,
        int stock)
{
}
