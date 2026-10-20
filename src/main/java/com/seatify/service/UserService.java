package com.seatify.service;

import com.seatify.entity.User;
import com.seatify.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void registerUser(String name, String email, String password){
        if(userRepo.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "An account with this email already exist!"
            );
        }
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(User.Role.USER);

            userRepo.save(user);
    }
}
