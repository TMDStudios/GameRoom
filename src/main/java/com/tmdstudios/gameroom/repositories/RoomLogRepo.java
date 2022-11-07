package com.tmdstudios.gameroom.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tmdstudios.gameroom.models.RoomLog;

@Repository
public interface RoomLogRepo extends CrudRepository<RoomLog, Long> {
	List<RoomLog> findAll();
}
