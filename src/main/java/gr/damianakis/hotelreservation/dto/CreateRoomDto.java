package gr.damianakis.hotelreservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.NumberFormat;

public record CreateRoomDto (

        @NotBlank(message = "RoomNumber is required")
        @Pattern(regexp = "^[1-3]0[1-9]$", message = "Room number must be like 101-109, 201-209 or 301-309")
        String roomNumber,

        @NotBlank(message = "type is required")
        String type,

        @NotNull
        @Positive
        Double pricePerNight
){}
