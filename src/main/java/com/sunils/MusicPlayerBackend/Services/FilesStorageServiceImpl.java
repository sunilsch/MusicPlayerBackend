package com.sunils.MusicPlayerBackend.Services;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesStorageServiceImpl implements FilesStorageService{
    private final Path root = Paths.get("audio");

    public String getRootPath(){
        return root.toAbsolutePath().toString();
    }

    @Bean
    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void add(MultipartFile file, Integer id){
        try {
            Files.copy(file.getInputStream(), this.root.resolve(id+file.getOriginalFilename()));
        } catch (Exception e){
            if(e instanceof FileAlreadyExistsException){
                throw new RuntimeException("A file of that name already exists");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAll(){
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void deleteOne(String filename){
        try {
            Path file = root.resolve(filename);
            Files.deleteIfExists(file);
        } catch (Exception e){
            throw new RuntimeException("Could not get file to delete!");
        }
    }

    @Override
    public Resource load(String filename){
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new RuntimeException("Could not rad the file!");
            }
        } catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
