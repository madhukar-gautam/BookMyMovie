package com.gautam.theaterservice.controller;

import com.gautam.theaterservice.dto.TheaterDTO;
import com.gautam.theaterservice.model.Theater;

import com.gautam.theaterservice.service.TheaterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theaters")
@Tag(name = "Theater", description = "Theater management APIs")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public List<Theater> getAllTheaters() {
        return theaterService.getAllTheaters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Long id) {
        Theater theater = theaterService.getTheaterById(id);
        return ResponseEntity.ok(theater);
    }

    @PostMapping
    @Operation(summary = "Add a new theater")
    public ResponseEntity<Theater> addTheater(@RequestBody TheaterDTO theaterDTO) {
        Theater newTheater = theaterService.addTheater(theaterDTO);
        return ResponseEntity.ok(newTheater);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Theater> updateTheater(@PathVariable Long id, @RequestBody TheaterDTO theaterDTO) {
        Theater updatedTheater = theaterService.updateTheater(id, theaterDTO);
        return ResponseEntity.ok(updatedTheater);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}
