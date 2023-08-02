package com.tmdstudios.gameroom.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tmdstudios.gameroom.models.LoginUser;
import com.tmdstudios.gameroom.models.User;
import com.tmdstudios.gameroom.repositories.UserRepo;

@Service
public class UserService {
	
	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
	
	@Autowired
	UserRepo userRepo;
	
	public User register(User newUser, BindingResult result) {
		System.out.println("REGISTER USER");
    	Optional<User> potentialUser = userRepo.findByUsername(newUser.getUsername());

    	if(potentialUser.isPresent()) {
    		result.rejectValue("username", "UsernameTaken", "An account with that username already exists!");
    	}

    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	}
    	
    	if(result.hasErrors()) {
	        return null;
	    }
    
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	
    	return userRepo.save(newUser);
	}
	
	public User login(LoginUser newLogin, BindingResult result) {
		System.out.println("LOG IN USER");
    	Optional<User> potentialUser = userRepo.findByUsername(newLogin.getUsername());
        
    	if(!potentialUser.isPresent()) {
    		result.rejectValue("username", "Matches", "User not found!");
    		return null;
    	}

    	if(!BCrypt.checkpw(newLogin.getPassword(), potentialUser.get().getPassword())) {
    	    result.rejectValue("password", "Matches", "Invalid Password!");
    	    return null;
    	}

        return potentialUser.get();
    }
	
	public String forgotPassword(String email) {
		System.out.println("PW RESET USER");
		Optional<User> optionalUser = userRepo.findByEmail(email);
		if(!optionalUser.isPresent()) {
			return "Invalid email";
		}
		
		User user = optionalUser.get();
		user.setToken(generateToken());
		user.setTokenCreationDate(LocalDateTime.now());
		user.setConfirm(user.getPassword());
		user = userRepo.save(user);
		
		return user.getToken();
	}
	
	public String resetPassword(String token, String password) {
		System.out.println("PW RESET USER");
		Optional<User> optionalUser = userRepo.findByToken(token);
		if(!optionalUser.isPresent()) {
			return "Invalid token";
		}
		
		LocalDateTime tokenCreationDate = optionalUser.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return "Token expired.";
		}
		
		User user = optionalUser.get();

		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setPassword(hashed);
		user.setConfirm(password);
		user.setToken(null);
		user.setTokenCreationDate(null);

		userRepo.save(user);

		return "Your password has been updated.";
	}
	
	private String generateToken() {
		System.out.println("GEN TOKEN USER");
		StringBuilder token = new StringBuilder();
		return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
	}
	
	private boolean isTokenExpired(final LocalDateTime tokenCreationTime) {
		System.out.println("EXPIRED TOKEN USER");
		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationTime, now);
		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}
	
	public List<User> allUsers(){
		System.out.println("ALL USERS");
		return userRepo.findAll();
	}
	
	public User updateUser(User user) {
		System.out.println("UPDATE USER");
		return userRepo.save(user);
	}
	
	public User findById(Long id) {
		System.out.println("FIND BY ID USER");
		User optionalUser = userRepo.findUserById(id);
		if(optionalUser==null) {
			return null;
		}else {
			return optionalUser;
		}
	}
	
	public User findByToken(String token) {
		System.out.println("FIND BY TOKEN USER");
		Optional<User> optionalUser = userRepo.findByToken(token);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}else {
			return null;
		}
	}
}
