package gr.damianakis.hotelreservation.dto;

import java.time.LocalDate;

public record BlockingDatesDto (
        LocalDate checkIn,
        LocalDate checkOut
){
}
