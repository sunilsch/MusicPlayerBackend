package com.sunils.MusicPlayerBackend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Songs")
public class SongEntity {
    private String name;
    private String interpret;
    private String filePath;
    @Id
    private Integer id;


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getInterpret(){
        return interpret;
    }
    public void setInterpret(String interpret){
        this.interpret = interpret;
    }

    public String getFilePath(){
        return filePath;
    }
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
}
