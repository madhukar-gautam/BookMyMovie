package com.gautam.theaterservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gautam.theaterservice.dto.TheaterDTO;
import com.gautam.theaterservice.model.Theater;
import com.gautam.theaterservice.service.TheaterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TheaterController.class)
public class TheaterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TheaterService theaterService;

    @Test
    public void getAllTheaters() throws Exception {
        Theater theater1 = Theater.builder()
                .id(1L)
                .name("Theater 1")
                .city("City 1")
                .address("Address 1")
                .build();

        Theater theater2 = Theater.builder()
                .id(2L)
                .name("Theater 2")
                .city("City 2")
                .address("Address 2")
                .build();

        List<Theater> theaters = Arrays.asList(theater1, theater2);

        Mockito.when(theaterService.getAllTheaters()).thenReturn(theaters);

        mockMvc.perform(get("/api/v1/theaters")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(theater1.getName())))
                .andExpect(jsonPath("$[1].name", is(theater2.getName())));
    }

    @Test
    public void addTheater() throws Exception {
        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setName("New Theater");
        theaterDTO.setCity("New City");
        theaterDTO.setAddress("New Address");

        Theater savedTheater = Theater.builder()
                .id(1L)
                .name(theaterDTO.getName())
                .city(theaterDTO.getCity())
                .address(theaterDTO.getAddress())
                .build();

        Mockito.when(theaterService.addTheater(any(TheaterDTO.class))).thenReturn(savedTheater);

        mockMvc.perform(post("/api/v1/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(theaterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedTheater.getId().intValue())))
                .andExpect(jsonPath("$.name", is(savedTheater.getName())));
    }

    @Test
    public void updateTheater() throws Exception {
        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setName("Updated Theater");
        theaterDTO.setCity("Updated City");
        theaterDTO.setAddress("Updated Address");

        Theater updatedTheater = Theater.builder()
                .id(1L)
                .name(theaterDTO.getName())
                .city(theaterDTO.getCity())
                .address(theaterDTO.getAddress())
                .build();

        Mockito.when(theaterService.updateTheater(anyLong(), any(TheaterDTO.class))).thenReturn(updatedTheater);

        mockMvc.perform(put("/api/v1/theaters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(theaterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedTheater.getId().intValue())))
                .andExpect(jsonPath("$.name", is(updatedTheater.getName())));
    }

    @Test
    public void deleteTheater() throws Exception {
        mockMvc.perform(delete("/api/v1/theaters/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(theaterService, Mockito.times(1)).deleteTheater(1L);
    }
}
