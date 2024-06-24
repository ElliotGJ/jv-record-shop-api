package org.northcoders.jvrecordshopapi.dto.records;

import java.time.Year;
import java.util.HashMap;
import java.util.List;

public record RecordDto(
        long id,
        String name,
        HashMap<Long, String> artists,
        Year releaseYear,
        List<String> genres,
        int stock)
{
}

