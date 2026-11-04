package gr.damianakis.hotelreservation.mapper;

import gr.damianakis.hotelreservation.dto.GuestResponseDto;
import gr.damianakis.hotelreservation.model.Guest;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    public GuestResponseDto toGuestResponseDto(Guest guest){
        return new GuestResponseDto(
                guest.getId(),
                guest.getFullName(),
                guest.getEmail(),
                guest.getPhone()
        );
    }
}
