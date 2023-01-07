package com.sunils.MusicPlayerBackend.Controller;

import com.sunils.MusicPlayerBackend.Entities.SongEntity;
import com.sunils.MusicPlayerBackend.Repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/musik")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping(path="/getAllSongs")
    public @ResponseBody Iterable<SongEntity> getAllUsers() {
        return songRepository.findAll();
    }

    @GetMapping(path="/searchSongs")
    public @ResponseBody Iterable<SongEntity> searchSongs(@RequestParam String searchPattern){
        return songRepository.searchSongs(searchPattern);
    }

    @GetMapping(path="/getSong")
    public @ResponseBody SongEntity getSong(@RequestParam String name){
        return songRepository.getSong(name);
    }
}
