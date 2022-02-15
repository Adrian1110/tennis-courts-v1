package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public ScheduleDTO findScheduleByID(Long scheduleId) {
        ScheduleDTO schedule = scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
        return handleSchedule(Optional.of(schedule));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public List<ScheduleDTO> findAllSchedules() {
        return handleSchedule(scheduleRepository.findAll());
    }

    private List<ScheduleDTO> handleSchedule(List<Schedule> schedule){
        if (!schedule.isEmpty()) {
            return scheduleMapper.map(schedule);
        } else {
            throw new EntityNotFoundException("Schedule not found");
        }
    }

    private ScheduleDTO handleSchedule(Optional<ScheduleDTO> scheduleDTO){
        return scheduleDTO.orElseThrow( () -> new EntityNotFoundException("Schedule not found"));
    }
}
