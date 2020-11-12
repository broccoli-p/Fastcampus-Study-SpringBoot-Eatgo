package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(
        Long restaurantId, Long userId, String name, String date, String time, Integer partySize
    ) {
        // TODO:
        Reservation reservation = reservationRepository.save(Reservation.builder()
            .restaurantId(restaurantId)
            .name(name)
            .userId(userId)
            .date(date)
            .time(time)
            .partySize(partySize)
            .build());
        return reservation;
    }
}
