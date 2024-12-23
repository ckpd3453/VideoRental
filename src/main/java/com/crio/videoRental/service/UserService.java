package com.crio.videoRental.service;

import com.crio.videoRental.dto.LoginDto;
import com.crio.videoRental.dto.ResponseDto;
import com.crio.videoRental.dto.RegistrationDto;
import com.crio.videoRental.model.User;
import com.crio.videoRental.repository.IUserRepo;
import com.crio.videoRental.util.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseDto userRegistration(RegistrationDto userDto) {
        User savedUser;
        try {
            // Check if the user already exists
            Optional<User> existingUser = userRepo.findByEmail(userDto.getEmail());

            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("User with this email id already exists.");
            }

            // Create a new user and hash the password
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash password
            user.setRole(userDto.getRole());

            // Save the user
            savedUser = userRepo.save(user);
        } catch (Exception e) {
            return new ResponseDto(userDto.getEmail(), e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseDto(savedUser, "User Registered Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseDto userLogin(LoginDto credential) {
        HashMap<String, Object> responseData;
        try {
            Optional<User> existingUser = userRepo.findByEmail(credential.getEmail());

            if (!existingUser.isPresent()) throw new IllegalArgumentException("User with this email id is not registered!");

            if(!credential.getRole().equals(existingUser.get().getRole())) throw new IllegalArgumentException("User is registered with some other role!");

            if(!passwordEncoder.matches(credential.getPassword(), existingUser.get().getPassword())) throw new IllegalArgumentException("User password mismatch!");

            String token = UserUtility.generateJwtToken(credential.getEmail(), credential.getRole());
            responseData = new HashMap<>();
            responseData.put("Token: ", token);

        }catch (Exception e){
            return new ResponseDto(credential.getEmail(), e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseDto(responseData, "User Login Succefully", HttpStatus.ACCEPTED);
    }
}
