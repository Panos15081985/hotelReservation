package gr.damianakis.hotelreservation.dto;

import java.time.LocalDate;

public record AvailabilityRequestDto(
        String roomNumber,
        LocalDate checkIn,
        LocalDate checkOut
) {
}
