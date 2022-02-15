package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    private GuestRepository guestRepository;
    private GuestMapper guestMapper;

    @Autowired
    public GuestService(GuestRepository guestRepository, GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    public GuestDTO addNewGuest(GuestPayload guestPayload) {
        Guest savedEntity = guestRepository.save(guestMapper.map(guestPayload));
        return guestMapper.map(savedEntity);
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        Guest guestToUpdate = guestMapper.map(guestDTO);
        guestToUpdate.setName(guestDTO.getName());
        return guestMapper.map(guestRepository.save(guestToUpdate));
    }

    public GuestDTO findById(Long guestId) {
        return handleGuest(guestRepository.findById(guestId).map(guestMapper::map));
    }

    public List<GuestDTO> findByName(String name) {
        return handleGustCollection(guestRepository.findGuestByName(name));
    }

    public List<GuestDTO> findAllGuests() {
        return handleGustCollection(guestRepository.findAll());
    }

    private List<GuestDTO> handleGustCollection(List<Guest> guests){
        if (!guests.isEmpty()) {
            return guestMapper.map(guests);
        } else {
            throw new EntityNotFoundException("Guest not found");
        }
    }

    private GuestDTO handleGuest(Optional<GuestDTO> guest){
        return guest.orElseThrow(() -> new EntityNotFoundException("Guest not found"));
    }

}
