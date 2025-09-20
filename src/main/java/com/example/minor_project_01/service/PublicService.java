package com.example.minor_project_01.service;

import com.example.minor_project_01.dto.PasswordReqDto;
import com.example.minor_project_01.dto.ResponseDTO;
import com.example.minor_project_01.dto.SignUpDto;
import com.example.minor_project_01.dto.UserDto;
import com.example.minor_project_01.entity.Role;
import com.example.minor_project_01.entity.User;
import com.example.minor_project_01.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PublicService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public SignUpDto createuser(UserDto userdto) {

        User user = new User();
        user.setName(userdto.getName());
        user.setEmail(userdto.getEmail());
        user.setRole(Role.CUSTOMER);
        userRepo.save(user);

        String token = UUID.randomUUID().toString();
        String key = "password:" + token;
        String value = userdto.getEmail();
        redisTemplate.opsForValue().set(key, value,2, TimeUnit.MINUTES);

        String reseturl = "localhost:8080/public/resetpassword/"+token;
        //trigger email verification
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setMessage("User created");
        signUpDto.setLink(reseturl);

        return signUpDto;
    }

    public String resetpassword(PasswordReqDto passwordReqDto, String token) {
        if (token == null) return "invalid token";

        String key = "password:" + token.trim();
        String storedEmail = redisTemplate.opsForValue().get(key);

        if (storedEmail == null) {
            return "invalid or expired token";
        }

        // check that the email provided matches the token owner
        if (!storedEmail.equalsIgnoreCase(passwordReqDto.getEmail().trim())) {
            return "email does not match token";
        }

        User user = userRepo.findByEmail(storedEmail);
        if (user == null) {
            return "user not found";
        }

        user.setPassword(passwordEncoder.encode(passwordReqDto.getPassword()));
        userRepo.save(user);

        // optionally delete the token after successful reset
        redisTemplate.delete(key);

        return "success change pwd";
    }
}
