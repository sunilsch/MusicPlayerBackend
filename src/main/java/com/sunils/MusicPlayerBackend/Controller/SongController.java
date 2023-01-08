package com.sunils.MusicPlayerBackend.Controller;

import com.sunils.MusicPlayerBackend.Entities.SongEntity;
import com.sunils.MusicPlayerBackend.Repositories.SongRepository;
import com.sunils.MusicPlayerBackend.Services.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping(path="/musik")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private FilesStorageService filesStorageService;

    @GetMapping(path="/getAllSongs")
    public @ResponseBody Iterable<SongEntity> getAllUsers() {
        return songRepository.findAll();
    }

    @GetMapping(path="/searchSongs")
    public @ResponseBody Iterable<SongEntity> searchSongs(@RequestParam String searchPattern){
        return songRepository.searchSongs(searchPattern);
    }

    @GetMapping(path="/getSongById")
    public @ResponseBody SongEntity getSong(@RequestParam Integer id){
        return songRepository.getSong(id);
    }

    @DeleteMapping(path="/deleteSongById")
    public String deleteSong(@RequestParam Integer id){
        try{
            String filename = songRepository.getSong(id).getFilePath();
            songRepository.delete(id);
            filesStorageService.deleteOne(filename);
        } catch(Exception e){
            return "Error while deleting Song";
        }
        return "Song deleted";
    }

    @GetMapping(path="/getSongFile", produces = "audio/wav")
    public @ResponseBody ResponseEntity<Resource> getFile(@RequestParam String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    @Transactional
    public String upload(@RequestParam String songName, @RequestParam String songInterpret, @RequestBody MultipartFile file){
        if(!file.getOriginalFilename().endsWith(".wav")){
            return "Wrong File format!";
        }
        SongEntity songEntity = new SongEntity();
        Integer id = songRepository.getHighestId();
        if(id == null){
            id = 0;
        } else {
            id++;
        }
        songEntity.setId(id);
        songEntity.setInterpret(songInterpret);
        songEntity.setName(songName);
        songEntity.setFilePath(id+file.getOriginalFilename());
        songRepository.save(songEntity);
        filesStorageService.add(file, id);
        return "File uploaded";
    }

}
