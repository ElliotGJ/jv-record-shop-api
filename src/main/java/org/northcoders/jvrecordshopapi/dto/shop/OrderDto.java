package org.northcoders.jvrecordshopapi.dto.shop;

import java.sql.Timestamp;
import java.util.List;

public record OrderDto(Long id, Long accountId, List<BasketItemDto> items, String line1, String line2, String postcode, String city, Timestamp createdAt) {
}
