package gr.damianakis.hotelreservation.repository;

import gr.damianakis.hotelreservation.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByPhone(String phone);
    Optional<Guest> findByEmail(String Email);
}
