package gr.damianakis.hotelreservation.service;

import gr.damianakis.hotelreservation.dto.AvailabilityRequestDto;
import gr.damianakis.hotelreservation.dto.AvailabilityResponseDto;
import gr.damianakis.hotelreservation.dto.CreateRoomDto;
import gr.damianakis.hotelreservation.dto.RoomResponseDto;
import gr.damianakis.hotelreservation.exception.ResourceNotFoundException;
import gr.damianakis.hotelreservation.exception.RoomAlreadyExistException;
import gr.damianakis.hotelreservation.model.Reservation;
import gr.damianakis.hotelreservation.model.Room;
import gr.damianakis.hotelreservation.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Room getRoomByRoomNumber(String roomNumber){
        return roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }


    public RoomResponseDto createRoom(CreateRoomDto roomDto){

        if(roomRepository.existsByRoomNumber(roomDto.roomNumber())){
            throw new RoomAlreadyExistException("exist Room with this roomNumber "+ roomDto.roomNumber());
        }

        Room newRoom = new Room();
        newRoom.setRoomNumber(roomDto.roomNumber());
        newRoom.setPricePerNight(roomDto.pricePerNight());
        newRoom.setType(roomDto.type());
        roomRepository.save(newRoom);

        return new RoomResponseDto(newRoom.getRoomNumber(),newRoom.getType(),newRoom.getPricePerNight());
    }

    public void deleteRoom(Long id){
        roomRepository.deleteById(id);
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with id: "+id));
    }
}
