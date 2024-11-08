package com.example.Parking.service;

import com.example.Parking.dto.UserDTO;
import com.example.Parking.entity.Users;

public interface UserService {
    Users findByPhoneNumber(String name);
    UserDTO save(UserDTO userDTO);
    Users updateByPrice(Users users);
    UserDTO updateCurrentBalance(Long money, String phoneNumber);
    UserDTO saveAdmin(UserDTO userDTO);
}
