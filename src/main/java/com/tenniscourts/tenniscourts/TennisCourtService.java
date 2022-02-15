package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TennisCourtService {

    private final TennisCourtRepository tennisCourtRepository;
    private final ScheduleService scheduleService;
    private final TennisCourtMapper tennisCourtMapper;

    @Autowired
    public TennisCourtService(TennisCourtRepository tennisCourtRepository, ScheduleService scheduleService, TennisCourtMapper tennisCourtMapper) {
        this.tennisCourtRepository = tennisCourtRepository;
        this.scheduleService = scheduleService;
        this.tennisCourtMapper = tennisCourtMapper;
    }

    public TennisCourtDTO addTennisCourt(TennisCourtDTO tennisCourt) {
        return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(tennisCourt)));
    }

    public TennisCourtDTO findTennisCourtById(Long tennisCourtId) {
        Optional<TennisCourtDTO> tennisCourtDTO = tennisCourtRepository.findById(tennisCourtId).map(tennisCourtMapper::map);
        return tennisCourtDTO.orElseThrow(() -> new EntityNotFoundException("Tennis Court not found"));
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {
        TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
        return tennisCourtDTO;
    }

    public List<TennisCourtDTO> findAllCourts() {
        List<TennisCourt> allCourts = tennisCourtRepository.findAll();
        if (!allCourts.isEmpty()) {
            return tennisCourtMapper.map(allCourts);
        } else {
            throw new EntityNotFoundException("Courts not found");
        }
    }
}
