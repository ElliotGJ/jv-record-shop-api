package org.northcoders.jvrecordshopapi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.northcoders.jvrecordshopapi.dto.ArtistDto;
import org.northcoders.jvrecordshopapi.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ArtistControllerTest {

    @Mock
    ArtistService artistService;

    @InjectMocks
    ArtistController artistController;

    @Autowired
    MockMvc mockArtistController;


    @Test
    @DisplayName("Return all artists")
    void getAllArtists() throws Exception {
         given(artistService.getAllArtists()).willReturn(new HashSet<>());
         mockArtistController.perform(get("/api/artist"))
                 .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Add a new artist")
    void addNewArtist() throws Exception {
        given(artistService.addNewArtist("James")).willReturn(new ArtistDto(1L, "James", new ArrayList<>()));
        String json = "James";
         mockArtistController.perform(post("/api/artist").contentType("application/json").content(json))
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.name", is("James")));
    }
}