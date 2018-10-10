package pl.abbl.reactchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.entity.User;
import pl.abbl.reactchat.repository.UserRepository;
import pl.abbl.reactchat.service.UserService;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository usersRepository;

    @Override
    public AbstractCallback register(Map<String, String> userCredentials) {
        String username = userCredentials.get("username");
        String password = userCredentials.get("password");

        if(username != null && password != null){
            if(usersRepository.findByUsername(username) == null){
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                user.setActive(1);
                usersRepository.saveAndFlush(user);

                return new AuthenticationCallback(AuthenticationCallback.REGISTER_SUCCESSFUL);
            }else{
                return new AuthenticationCallback(AuthenticationCallback.USERNAME_TAKEN);
            }
        }else{
            return new AuthenticationCallback(AuthenticationCallback.VALUE_OF_FIELD_IS_MISSING);
        }
    }
}