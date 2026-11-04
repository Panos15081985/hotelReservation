package gr.damianakis.hotelreservation.dto;

import gr.damianakis.hotelreservation.model.ReservationStatus;

import java.time.LocalDate;

public record ReservationResponseDto(
        Long id,
        ReservationStatus status,
        String roomNumber,
        String roomType,
        LocalDate checkIn,
        LocalDate checkOut,
        long nights,
        double totalPrice,
        GuestResponseDto guest
) {}
