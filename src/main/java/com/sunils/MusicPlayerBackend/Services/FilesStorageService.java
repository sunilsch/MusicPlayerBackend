package com.sunils.MusicPlayerBackend.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();
    public void add(MultipartFile file, Integer id);
    public void deleteAll();
    public void deleteOne(String filename);
    public Resource load(String filename);
}
