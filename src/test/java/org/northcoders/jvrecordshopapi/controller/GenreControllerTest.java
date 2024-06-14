package org.northcoders.jvrecordshopapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.northcoders.jvrecordshopapi.dto.GenreDto;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.northcoders.jvrecordshopapi.model.Genre;

import static org.hamcrest.Matchers.is;
import org.northcoders.jvrecordshopapi.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class GenreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Spy
    Mapper mapper;

    @Mock
    GenreService genreService;

    @InjectMocks
    GenreController genreController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
    }

    @Test
    @DisplayName("Add a new genre")
    void addNewGenre() throws Exception {
        when(genreService.addGenre("Metal")).thenReturn(new Genre(1L, "Metal", new HashSet<>()));
        mockMvc.perform(post("/api/genre").contentType("application/json").content("Metal"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Metal")));
    }

    @Test
    @DisplayName("Delete a genre")
    void deleteGenre() throws Exception {
        mockMvc.perform(delete("/api/genre/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Get all genres")
    void getAllGenres() throws Exception {
        when(genreService.getAllGenres()).thenReturn(new HashSet<>(List.of(new GenreDto(1L, "Metal"))));
        mockMvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Metal")));
    }

}