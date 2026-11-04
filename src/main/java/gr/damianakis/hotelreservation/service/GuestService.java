package gr.damianakis.hotelreservation.service;

import gr.damianakis.hotelreservation.dto.CreateGuestDto;
import gr.damianakis.hotelreservation.exception.ResourceNotFoundException;
import gr.damianakis.hotelreservation.mapper.GuestMapper;
import gr.damianakis.hotelreservation.model.Guest;
import gr.damianakis.hotelreservation.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestService(GuestRepository guestRepository, GuestMapper guestMapper){
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    public Guest createGuest(Guest guest){
        return guestRepository.save(guest);
    }

    public void deleteGuest(Long id){
        guestRepository.deleteById(id);
    }

    public List<Guest> getAllGuests(){
        return guestRepository.findAll();
    }

    public Guest getGuestByEmail(String email){
        return guestRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Guest not found with Email"+email));
    }

    public Guest getGuestByEmailOrCreate(CreateGuestDto createGuestDto){
        return guestRepository.findByEmail(createGuestDto.email())
                .orElseGet(()->{
                Guest guest  = new Guest();
                guest.setEmail(createGuestDto.email());
                guest.setPhone(createGuestDto.phone());
                guest.setFullName(createGuestDto.fullName());
                return guestRepository.save(guest);

                });
    }

    public Guest getGuestByPhone(String phone){
        return guestRepository.findByPhone(phone)
                .orElseThrow(()-> new ResourceNotFoundException("Guest not found with Phone "+phone));
    }
}
