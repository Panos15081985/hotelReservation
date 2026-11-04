package gr.damianakis.hotelreservation.controller;

import gr.damianakis.hotelreservation.dto.CreateReservationDto;
import gr.damianakis.hotelreservation.dto.ReservationResponseDto;
import gr.damianakis.hotelreservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(
            @Valid @RequestBody CreateReservationDto createReservationDto
            ){


        ReservationResponseDto reservationResponseDto = reservationService.createReservation(createReservationDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservationResponseDto.id())
                .toUri();

        return ResponseEntity.created(location).body(reservationResponseDto);
    }
    @GetMapping("/{id}")
    public ReservationResponseDto getById(@PathVariable("id") Long id){
        return reservationService.getReservationById(id);
    }

    @GetMapping("/arrivals")
    public List<ReservationResponseDto> getArrivals(
            @RequestParam("from")LocalDate from,
            @RequestParam("until")LocalDate until){
        return reservationService.getArrivals(from,until);
    }

}
