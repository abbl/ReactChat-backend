package pl.abbl.reactchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.repository.UserRepository;
import pl.abbl.reactchat.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public AbstractCallback createUser(String userName) {
		return userRepository.createUser(userName);
	}

	@Override
	public void removeUser(String token) {
		userRepository.removeUser(token);
	}

	@Override
	public boolean verifyToken(String token) {
		if(token != null)
			return userRepository.getUser(token) != null;
		return false;
	}
	
	@Override
	public String getUserName(String token) {
		return userRepository.getUser(token).getUserName();
	}
}