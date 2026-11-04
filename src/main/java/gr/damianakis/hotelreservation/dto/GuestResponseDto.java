package gr.damianakis.hotelreservation.dto;

public record GuestResponseDto(
        Long id,
        String fullName,
        String email,
        String phone
) {
}
