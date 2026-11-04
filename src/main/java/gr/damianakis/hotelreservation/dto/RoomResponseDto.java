package gr.damianakis.hotelreservation.dto;

public record RoomResponseDto(
        String roomNumber,
        String type,
        Double pricePerNight
) {
}
