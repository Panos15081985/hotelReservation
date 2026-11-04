package gr.damianakis.hotelreservation.mapper;

import gr.damianakis.hotelreservation.dto.GuestResponseDto;
import gr.damianakis.hotelreservation.dto.ReservationResponseDto;
import gr.damianakis.hotelreservation.model.Guest;
import gr.damianakis.hotelreservation.model.Reservation;
import gr.damianakis.hotelreservation.model.ReservationStatus;
import gr.damianakis.hotelreservation.model.Room;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ReservationMapper {

    public Reservation toReservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut){
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkOut);
        reservation.setStatus(ReservationStatus.COMPLETED);

        return reservation;
    }

    public ReservationResponseDto toReservationResponseDto(Reservation reservation){

        long nights = ChronoUnit.DAYS.between(reservation.getCheckIn(),reservation.getCheckOut());

        double totalPrice = nights * reservation.getRoom().getPricePerNight();

        GuestResponseDto guestResponseDto = new GuestResponseDto(
                reservation.getGuest().getId(),
                reservation.getGuest().getFullName(),
                reservation.getGuest().getEmail(),
                reservation.getGuest().getPhone()
        );

        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getStatus(),
                reservation.getRoom().getRoomNumber(),
                reservation.getRoom().getType(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                nights,
                totalPrice,
                guestResponseDto
        );
    }
}
