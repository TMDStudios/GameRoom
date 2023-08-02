package com.tmdstudios.gameroom.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tmdstudios.gameroom.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	List<User> findAll();
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByToken(String token);
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE users.id = :id")
	User findUserById(@Param("id") Long id);
}
