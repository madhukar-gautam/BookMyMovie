package com.gautam.theaterservice.service;


import com.gautam.theaterservice.dto.TheaterDTO;
import com.gautam.theaterservice.model.Theater;
import com.gautam.theaterservice.repository.TheaterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    private static final Logger logger = LoggerFactory.getLogger(TheaterService.class);

    @Autowired
    private TheaterRepository theaterRepository;

    public Theater addTheater(TheaterDTO theaterDTO) {
        Theater theater = Theater.builder()
                .name(theaterDTO.getName())
                .city(theaterDTO.getCity())
                .address(theaterDTO.getAddress())
                .build();
        logger.info("Adding theater: {}", theater);
        return theaterRepository.save(theater);
    }

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public Theater getTheaterById(Long id) {
        Optional<Theater> theaterOptional = theaterRepository.findById(id);
        return theaterOptional.orElseThrow(() -> new RuntimeException("Theater not found"));
    }

    public Theater updateTheater(Long id, TheaterDTO theaterDTO) {
        Theater theater = getTheaterById(id);
        theater.setName(theaterDTO.getName());
        theater.setCity(theaterDTO.getCity());
        theater.setAddress(theaterDTO.getAddress());
        return theaterRepository.save(theater);
    }

    public void deleteTheater(Long id) {
        theaterRepository.deleteById(id);
    }
}
