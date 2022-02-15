package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("court/v1/")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @Autowired
    public TennisCourtController(TennisCourtService tennisCourtService) {
        this.tennisCourtService = tennisCourtService;
    }

    @PostMapping
    @ApiOperation("Add tennis court")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tennis Court added"),
            @ApiResponse(code = 500, message = "Tennis Court not added"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<Void> addTennisCourt(TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId()))
                .build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tennis Court found"),
            @ApiResponse(code = 404, message = "Tennis Court not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long id) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(id));
    }


    @GetMapping("/scheduled/{id}")
    @ApiOperation("Find tennis courts with schedules by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tennis Court Scheduled found"),
            @ApiResponse(code = 404, message = "Tennis Court Scheduled not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long id) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(id));
    }

    @GetMapping("/all")
    @ApiOperation("Find all courts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tennis Court found"),
            @ApiResponse(code = 404, message = "Tennis Court not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<List<TennisCourtDTO>> findAllCourts() {
        return ResponseEntity.ok(tennisCourtService.findAllCourts());
    }
}
