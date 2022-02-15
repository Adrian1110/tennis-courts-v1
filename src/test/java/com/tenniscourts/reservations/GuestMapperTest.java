package com.tenniscourts.reservations;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GuestMapperTest {
    private GuestMapper guestMapper = Mappers.getMapper( GuestMapper.class);

    @Test
    public void checkGuestMapper() {
        Guest guest = new Guest();
        guest.setName("TestMapper");

        GuestDTO result = guestMapper.map(guest);
        assertEquals(guest.getName(), result.getName());
    }
}
