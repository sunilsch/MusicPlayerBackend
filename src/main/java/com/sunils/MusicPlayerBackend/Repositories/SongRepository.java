package com.sunils.MusicPlayerBackend.Repositories;

import com.sunils.MusicPlayerBackend.Entities.SongEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<SongEntity, Integer> {
    @Query(value = "SELECT * FROM Songs s WHERE s.name LIKE %?1%", nativeQuery = true)
    Iterable<SongEntity> searchSongs(String name);

    @Query(value = "SELECT * FROM Songs s WHERE s.name = ?1", nativeQuery = true)
    SongEntity getSong(String name);
}
