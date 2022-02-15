package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest/v1/")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @ApiOperation("Add new guest entity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest was added"),
            @ApiResponse(code = 500, message = "Guest was not added"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<Void> createGuest(GuestPayload guestPayload) {
        return ResponseEntity.created(locationByEntity(guestService.addNewGuest(guestPayload).getId())).build();
    }

    @PutMapping
    @ApiOperation("Update guest entity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest was updated"),
            @ApiResponse(code = 500, message = "Guest was not updated"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<GuestDTO> updateGuest(GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @GetMapping("/{id}")
    @ApiOperation("Find guest by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest entity found"),
            @ApiResponse(code = 404, message = "Guest entity not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<GuestDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @GetMapping
    @ApiOperation("Find guest by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest entity found"),
            @ApiResponse(code = 404, message = "Guest entity not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<List<GuestDTO>> findByName(String name) {
        return ResponseEntity.ok(guestService.findByName(name));
    }

    @GetMapping("/all")
    @ApiOperation("Get all guests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest entity found"),
            @ApiResponse(code = 404, message = "Guest entity not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }
}
