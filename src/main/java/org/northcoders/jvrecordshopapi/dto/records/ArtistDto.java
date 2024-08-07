package org.northcoders.jvrecordshopapi.dto.records;

import java.util.List;



public record ArtistDto(
        long id,
        String name,
        List<RecordDto> records
        )
{
}
