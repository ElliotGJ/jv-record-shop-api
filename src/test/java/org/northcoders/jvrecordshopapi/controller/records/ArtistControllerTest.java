package org.northcoders.jvrecordshopapi.controller.records;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.northcoders.jvrecordshopapi.dto.records.ArtistDto;
import org.northcoders.jvrecordshopapi.dto.records.ArtistDtoNoRecords;
import org.northcoders.jvrecordshopapi.service.records.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @BeforeEach
    void setUp() {
        mockArtistController = MockMvcBuilders.standaloneSetup(artistController).build();
    }


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

    @Test
    @DisplayName("Get artist by ID")
    void getArtistById() throws Exception {
        given(artistService.getArtistById(1L)).willReturn(new ArtistDto(1L, "James", new ArrayList<>()));
         mockArtistController.perform(get("/api/artist/1"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.name", is("James")))
                 .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @DisplayName("Return all artists by name")
    void getArtistsByName() throws Exception {
        given(artistService.getArtistsByName("James")).willReturn(new HashSet<>(List.of(new ArtistDtoNoRecords(1L, "James"))));
         mockArtistController.perform(get("/api/artist?name=James"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$[0].name", is("James")));
    }

    @Test
    @DisplayName("Update artist")
    void updateArtist() throws Exception {
        given(artistService.updateArtist(1L, "Jimbo")).willReturn(new ArtistDto(1L, "Jimbo", new ArrayList<>()));
        String json = "Jimbo";
        mockArtistController.perform(put("/api/artist/1").contentType("application/json").content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Jimbo")));
    }
}