package gr.damianakis.hotelreservation.controller;


import gr.damianakis.hotelreservation.dto.CreateRoomDto;
import gr.damianakis.hotelreservation.dto.RoomResponseDto;
import gr.damianakis.hotelreservation.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponseDto> create(
            @Valid @RequestBody CreateRoomDto createRoomDto
            ){

        RoomResponseDto roomResponseDto = roomService.createRoom(createRoomDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{roomNumber}")
                .buildAndExpand(roomResponseDto.roomNumber())
                .toUri();

        return ResponseEntity.created(location).body(roomResponseDto);
    }
}
