package org.northcoders.jvrecordshopapi.controller;

import org.northcoders.jvrecordshopapi.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/artist")
public class ArtistController {

    @Autowired
    ArtistService artistService;


}
