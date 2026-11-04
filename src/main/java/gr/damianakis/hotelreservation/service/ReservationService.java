package gr.damianakis.hotelreservation.service;

import gr.damianakis.hotelreservation.dto.*;
import gr.damianakis.hotelreservation.exception.InvalidDateRangeException;
import gr.damianakis.hotelreservation.exception.ReservationOverlapException;
import gr.damianakis.hotelreservation.exception.ResourceNotFoundException;
import gr.damianakis.hotelreservation.mapper.ReservationMapper;
import gr.damianakis.hotelreservation.model.Guest;
import gr.damianakis.hotelreservation.model.Reservation;
import gr.damianakis.hotelreservation.model.Room;
import gr.damianakis.hotelreservation.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestService guestService;
    private final RoomService roomService;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository,
                              GuestService guestService,
                              RoomService roomService,
                              ReservationMapper reservationMapper){
        this.reservationRepository = reservationRepository;
        this.guestService = guestService;
        this.roomService = roomService;
        this.reservationMapper = reservationMapper;
    }

    public AvailabilityResponseDto checkAvailability (AvailabilityRequestDto availabilityRequestDto){

        Room room = roomService.getRoomByRoomNumber(availabilityRequestDto.roomNumber());

        List<Reservation> conflictsReservation = reservationRepository
                .findByRoomIdAndCheckOutAfterAndCheckInBefore(
                        room.getId(),
                        availabilityRequestDto.checkIn(),
                        availabilityRequestDto.checkOut()
                );

        List<BlockingDatesDto> blockingDates = conflictsReservation.stream()
                .map(reservation -> new BlockingDatesDto(reservation.getCheckIn(),reservation.getCheckOut()))
                .toList();

        boolean available = blockingDates.isEmpty();

        return new AvailabilityResponseDto(available, blockingDates);
    }

    @Transactional
    public ReservationResponseDto createReservation(CreateReservationDto createReservationDto){

        if(!createReservationDto.checkOut().isAfter(createReservationDto.checkIn())){
            throw  new InvalidDateRangeException("Check-out must be after check-in");
        }

        Room room = roomService.getRoomByRoomNumber(createReservationDto.roomNumber());

        boolean overlapExists = reservationRepository
                .existsByRoomIdAndCheckOutAfterAndCheckInBefore(room.getId(), createReservationDto.checkIn(), createReservationDto.checkOut());

        if(overlapExists){
            throw new ReservationOverlapException( "Room " + room.getRoomNumber() + " is already booked for these dates");
        }

        Guest guest = guestService.getGuestByEmailOrCreate(createReservationDto.guestDto());

        Reservation reservation = reservationMapper.toReservation(guest,room,createReservationDto.checkIn(),createReservationDto.checkOut());

        Reservation newReservation =  reservationRepository.save(reservation);

        return reservationMapper.toReservationResponseDto(newReservation);

    }

    public ReservationResponseDto getReservationById(long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()->
                    new ResourceNotFoundException("Reservation not found with id: " + id)
                );

        return reservationMapper.toReservationResponseDto(reservation);
    }

    public List<ReservationResponseDto> getArrivals(LocalDate from, LocalDate until) {

        List<Reservation> reservations = reservationRepository.findByCheckInBetween(from, until);

        return reservations.stream()
                .map(reservationMapper::toReservationResponseDto)
                .toList();
    }


}
