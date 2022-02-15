package com.tenniscourts.reservations;

import com.tenniscourts.guests.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
public class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private GuestMapper guestMapper;

    @Test
    public void updateGuest() {
        GuestDTO guestDto = getGuestDTOMock();
        Guest guest = getGuestMock();
        when(guestMapper.map(guestDto)).thenReturn(guest);
        when(guestRepository.save(guest)).thenReturn(guest);
        when(guestMapper.map(guest)).thenReturn(guestDto);

        GuestDTO result = guestService.updateGuest(guestDto);
        assertNotNull(result);
        assertEquals("Adrian-Update",result.getName());
    }

    @Test
    public void addGuest(){
        GuestDTO guestDto = getGuestDTOMock();
        Guest guest = getGuestMock();
        GuestPayload guestPayload = getGuestPayloadMock();

        when(guestMapper.map(guestPayload)).thenReturn(guest);
        when(guestRepository.save(guest)).thenReturn(guest);
        when(guestMapper.map(guest)).thenReturn(guestDto);

        GuestDTO result = guestService.addNewGuest(guestPayload);
        assertNotNull(result);
        assertEquals("Adrian-Update",result.getName());

    }

    @Test
    public void findById() {
        Guest guestMock = getGuestMock();
        when(guestRepository.findById(1L)).thenReturn(Optional.ofNullable(guestMock));
        when(guestMapper.map(guestMock)).thenReturn(getGuestDTOMock());

        GuestDTO result = guestService.findById(1L);
        assertNotNull(result);
    }


    public GuestDTO getGuestDTOMock(){
        GuestDTO g = new GuestDTO();
        g.setName("Adrian-Update");
        return g;
    }

    public Guest getGuestMock(){
        Guest g = new Guest();
        g.setName("Adrian");
        return g;
    }

    public GuestPayload getGuestPayloadMock(){
        GuestPayload g = new GuestPayload();
        g.setName("Adrian");
        return g;
    }

}
