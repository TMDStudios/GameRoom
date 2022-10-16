package com.tmdstudios.gameroom.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tmdstudios.gameroom.models.Room;

@Repository
public interface RoomRepo extends CrudRepository<Room, Long> {
	List<Room> findAll();
	Room findByIdIs(Long id);
	Room findByLinkIs(String link);
}
