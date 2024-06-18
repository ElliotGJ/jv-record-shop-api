package org.northcoders.jvrecordshopapi.dto.shop;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(
        Long addressId,
        @NotBlank(message = "Missing name") String name,
        @NotBlank(message = "Missing line 1") String line1,
        String line2,
        @NotBlank(message = "Missing city") String city,
        @NotBlank(message = "Missing postcode") String postcode) {
}
