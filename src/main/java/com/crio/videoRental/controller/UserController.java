package com.crio.videoRental.controller;

import com.crio.videoRental.dto.LoginDto;
import com.crio.videoRental.dto.RegistrationDto;
import com.crio.videoRental.dto.ResponseDto;
import com.crio.videoRental.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseDto<?>> signUp(@Valid @RequestBody RegistrationDto userDto) {
        ResponseDto<?> userResponseDto = userService.userRegistration(userDto);
        return ResponseEntity.status(userResponseDto.getCode()).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<?>> signIn(@Valid @RequestBody LoginDto credential){
        ResponseDto<?> responseDto = userService.userLogin(credential);
        return ResponseEntity.status(responseDto.getCode()).body(responseDto);
    }
}

