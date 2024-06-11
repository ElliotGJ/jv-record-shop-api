package org.northcoders.jvrecordshopapi.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.northcoders.jvrecordshopapi.service.ArtistService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class ArtistControllerTest {

    @Mock
    ArtistService artistService;

    @InjectMocks
    ArtistController artistController;
}