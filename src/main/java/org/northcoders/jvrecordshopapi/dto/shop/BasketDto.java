package org.northcoders.jvrecordshopapi.dto.shop;

import java.util.List;

public record BasketDto(Long id, Long userId, List<BasketItemDto> items) {
}
