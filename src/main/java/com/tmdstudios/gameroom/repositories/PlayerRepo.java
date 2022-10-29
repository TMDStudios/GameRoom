package com.tmdstudios.gameroom.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tmdstudios.gameroom.models.Player;
import com.tmdstudios.gameroom.models.Room;

@Repository
public interface PlayerRepo extends CrudRepository<Player, Long> {
	List<Player> findAll();
	Player findByIdIs(Long id);
	Player findByNameIsAndRoomIs(String name, Room room);
}
