package com.tmdstudios.gameroom.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tmdstudios.gameroom.models.Player;
import com.tmdstudios.gameroom.models.Room;

@Repository
public interface RoomRepo extends CrudRepository<Room, Long> {
	List<Room> findAll();
	Room findByIdIs(Long id);
	@Query(nativeQuery = true, value = "SELECT * FROM rooms WHERE rooms.link = :id")
	Room findByLinkIs(@Param("id") String link);
	Player findByName(String name);
}
