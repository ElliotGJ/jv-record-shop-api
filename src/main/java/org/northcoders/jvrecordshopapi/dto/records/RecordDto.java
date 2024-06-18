package org.northcoders.jvrecordshopapi.dto.records;

import java.time.Year;
import java.util.List;

public record RecordDto(
        long id,
        String name,
        List<String> artists,
        Year releaseYear,
        List<String> genres,
        int stock)
{
}

