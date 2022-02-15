package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations/v1/")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ApiOperation("book reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entity added"),
            @ApiResponse(code = 500, message = "Entity was added"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping("/{id}")
    @ApiOperation("find by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entity added"),
            @ApiResponse(code = 404, message = "Entity not added"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findReservation(id));
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("delete reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entity deleted"),
            @ApiResponse(code = 404, message = "Entity not deleted"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }

    @PutMapping("/reschedule")
    @ApiOperation("Reschedule")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entity added"),
            @ApiResponse(code = 404, message = "Entity not added"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

    @GetMapping("/all")
    @ApiOperation("find all")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entity added"),
            @ApiResponse(code = 404, message = "Entity not added"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<List<ReservationDTO>> findAllReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }
}
