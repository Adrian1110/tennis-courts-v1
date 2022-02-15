package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, ScheduleRepository scheduleRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.scheduleRepository = scheduleRepository;
        this.reservationMapper = reservationMapper;
    }

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Guest guest = handleGuest(guestRepository.findById(createReservationRequestDTO.getGuestId()));
        Schedule schedule = handleSchedule(scheduleRepository.findById(createReservationRequestDTO.getScheduleId()));

        isScheduleReadyToPlayOnReservations(schedule);

        Reservation reservation = Reservation.builder()
                .guest(guest)
                .schedule(schedule)
                .value(BigDecimal.valueOf(10))
                .refundValue(BigDecimal.valueOf(10))
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .build();
        return reservationMapper.map(reservationRepository.save(reservation));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return handleReservation(reservationRepository.findById(reservationId).map(reservationMapper::map));
    }

    public List<ReservationDTO> findAllReservations() {
        return  handleReservation(reservationRepository.findAll());
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        Optional<Reservation> r = reservationRepository.findById(reservationId);
        if(r.isPresent()){
            if (!ReservationStatus.READY_TO_PLAY.equals(r.get().getReservationStatus())) {
                throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
            }
            BigDecimal refund = getRefundValue(r.get());
            return updateReservation(r.get(), refund, ReservationStatus.CANCELLED);
        }else{
            throw new EntityNotFoundException("Reservation not found.");
        }
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refund, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refund));
        reservation.setRefundValue(refund);

        return reservationRepository.save(reservation);
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (minutes >= 1440L) {
            return reservation.getValue();
        } else if (minutes >= 720L) {
            return reservation.getValue().multiply(BigDecimal.valueOf(.75));
        } else if (minutes >= 120L) {
            return reservation.getValue().multiply(BigDecimal.valueOf(.5));
        } else if (minutes > 0L){
            return reservation.getValue().multiply(BigDecimal.valueOf(.25));
        } else {
            return BigDecimal.ZERO;
        }
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Schedule schedule = handleSchedule(scheduleRepository.findById(scheduleId));

        isScheduleReadyToPlayOnReservations(schedule);
        Reservation previous = cancel(previousReservationId);
        previous.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previous);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previous.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previous));
        return newReservation;
    }

    private void isScheduleReadyToPlayOnReservations(Schedule schedule) {
        List <Reservation> checkExistingReservationsByScheduleID = reservationRepository.findBySchedule_Id(schedule.getId());
        Optional<ReservationStatus> reservation = checkExistingReservationsByScheduleID.stream().map(r -> r.getReservationStatus()).filter(rez -> rez.equals(ReservationStatus.READY_TO_PLAY)).findFirst();
        if(reservation.isPresent())
            throw new AlreadyExistsEntityException("Tennis court booked.");

    }

    private Guest handleGuest(Optional<Guest> guest){
        return guest.orElseThrow( () -> new EntityNotFoundException("Guest not found"));
    }

    private Schedule handleSchedule(Optional<Schedule> schedule){
        return schedule.orElseThrow( () -> new EntityNotFoundException("Schedule not found"));
    }

    private ReservationDTO handleReservation(Optional<ReservationDTO> reservation){
        return reservation.orElseThrow( () -> new EntityNotFoundException("Reservation not found"));
    }

    private List<ReservationDTO> handleReservation( List<Reservation> reservations){
        if (!reservations.isEmpty()) {
            return reservationMapper.map(reservations);
        } else {
            throw new EntityNotFoundException("Reservations not found");
        }
    }

}
