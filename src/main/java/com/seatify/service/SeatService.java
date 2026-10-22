package com.seatify.service;

import com.seatify.entity.Seat;
import com.seatify.repository.SeatRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeatService {

    private final  SeatRepo seatRepo;

    public SeatService(SeatRepo seatRepo) {
        this.seatRepo = seatRepo;
    }

    public List<Seat> getSeatsByVenueId(Long id){
        return seatRepo.findByVenue_Id(id);
    }

}
