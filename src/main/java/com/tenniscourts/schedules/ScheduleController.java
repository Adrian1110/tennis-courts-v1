package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/schedule/v1/")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @GetMapping("/{id}")
    @ApiOperation("Find schedule by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Schedule found"),
            @ApiResponse(code = 404, message = "Schedule not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findScheduleByID(id));
    }

    @GetMapping("/all")
    @ApiOperation("Find all reservations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Schedule found"),
            @ApiResponse(code = 404, message = "Schedule not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<List<ScheduleDTO>> findAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAllSchedules());
    }
}
