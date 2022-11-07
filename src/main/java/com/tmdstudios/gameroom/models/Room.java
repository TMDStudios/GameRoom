package com.tmdstudios.gameroom.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="rooms")
public class Room {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotEmpty(message="Room name is required!")
    @Size(min=4, max=16, message="The room name must be between 4 and 16 characters long")
	private String name;
	@Size(max=64, message="The message cannot be longer than 64 characters")
	private String message;
	private String link;
	private Boolean privateRoom = false;
	private String password;
	private String gameType;
	
	@OneToMany(mappedBy="room", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<Player> players;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name="user_id")
	private User host;
	
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
	
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
	
	@PostPersist
	protected void setLink() {
		this.link = this.getName().replace(" ", "").toLowerCase() + this.getId();
	}
	
	public Room() {}
	
	public Room(User user) {
		this.host = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Boolean getPrivateRoom() {
		return privateRoom;
	}

	public void setPrivateRoom(Boolean privateRoom) {
		this.privateRoom = privateRoom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}
}
