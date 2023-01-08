package com.sunils.MusicPlayerBackend.Repositories;

import com.sunils.MusicPlayerBackend.Entities.SongEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SongRepository extends CrudRepository<SongEntity, Integer> {
    @Query(value = "SELECT * FROM Songs s WHERE s.name LIKE %?1%", nativeQuery = true)
    Iterable<SongEntity> searchSongs(String name);


    @Query(value = "SELECT * FROM Songs s WHERE s.id = ?1", nativeQuery = true)
    SongEntity getSong(Integer id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Songs WHERE id = ?1", nativeQuery = true)
    void delete(Integer id);

    @Query(value = "SELECT id FROM Songs ORDER BY id DESC LIMIT 0, 1;", nativeQuery = true)
    Integer getHighestId();
}
