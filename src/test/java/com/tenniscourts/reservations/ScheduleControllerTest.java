package com.tenniscourts.reservations;

import com.tenniscourts.schedules.ScheduleController;
import com.tenniscourts.schedules.ScheduleDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ScheduleController.class)
public class ScheduleControllerTest {

    @InjectMocks
    private ScheduleController scheduleController;

    @Test
    public void findSchedulesByDates() {
        ResponseEntity<List<ScheduleDTO>> result = scheduleController.findAllSchedules();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}