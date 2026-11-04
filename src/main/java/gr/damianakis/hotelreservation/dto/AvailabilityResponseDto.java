package gr.damianakis.hotelreservation.dto;

import java.util.List;

public record AvailabilityResponseDto(
         boolean available,
         List<BlockingDatesDto> blockingDates
) {
}
