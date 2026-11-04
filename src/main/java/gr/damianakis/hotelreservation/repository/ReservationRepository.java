package gr.damianakis.hotelreservation.repository;

import gr.damianakis.hotelreservation.model.Guest;
import gr.damianakis.hotelreservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByGuest(Guest guest);
    List<Reservation> findByRoomIdAndCheckOutAfterAndCheckInBefore(Long roomId, LocalDate checkIn, LocalDate checkOut);
    Boolean existsByRoomIdAndCheckOutAfterAndCheckInBefore(
            Long id, LocalDate checkIn, LocalDate checkOut
    );
    List<Reservation> findByCheckInBetween(LocalDate from, LocalDate until);
    List<Reservation> findByCheckOutBetween(LocalDate from, LocalDate until);
}
