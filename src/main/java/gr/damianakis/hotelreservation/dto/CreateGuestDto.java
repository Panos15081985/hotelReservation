package gr.damianakis.hotelreservation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateGuestDto(

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "email must be valid")
        String email,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone must contain 10 to 15 digits")
        String phone
) {
}
