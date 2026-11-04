package gr.damianakis.hotelreservation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CreateReservationDto(

        @NotNull(message = "Guest Details are required")
        @Valid
        CreateGuestDto guestDto,

        @NotBlank(message = "RoomNumber is required")
        @Pattern(regexp = "^[1-3]0[1-9]$", message = "Room number must be like 101-109, 201-209 or 301-309")
        String roomNumber,

        @NotNull(message = "Check-In date is required ")
        @FutureOrPresent(message = "Check-In can not be in the past")
        LocalDate checkIn,

        @NotNull(message = "Check-Out date is required")
        LocalDate checkOut
) {
}
