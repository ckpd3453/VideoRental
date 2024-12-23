package com.crio.videoRental.service;

import com.crio.videoRental.dto.LoginDto;
import com.crio.videoRental.dto.RegistrationDto;
import com.crio.videoRental.dto.ResponseDto;

public interface IUserService {

    public ResponseDto userRegistration(RegistrationDto userDto);
    public ResponseDto userLogin(LoginDto credentials);
}
