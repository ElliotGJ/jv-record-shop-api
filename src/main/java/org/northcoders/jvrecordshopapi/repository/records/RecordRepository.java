package org.northcoders.jvrecordshopapi.repository.records;

import org.northcoders.jvrecordshopapi.model.records.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;


@Repository
public interface RecordRepository extends CrudRepository<Record, Long> {
    Iterable<Record> findAllByNameIgnoreCase(String name);
    Iterable<Record> findAllByReleaseYear(Year year);
    Iterable<Record> findAllByStock_StockGreaterThan(int stock);
}
