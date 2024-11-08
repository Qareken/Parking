package com.example.Parking.controller;

import com.example.Parking.dto.AuthResponse;
import com.example.Parking.dto.RefreshTokenRequest;
import com.example.Parking.dto.RefreshTokenResponse;
import com.example.Parking.dto.UserDTO;
import com.example.Parking.security.SecurityService;
import com.example.Parking.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/parking/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServiceImpl userService;
    private final SecurityService service;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody UserDTO userDTO){
        log.info(userDTO+"warn");
        return ResponseEntity.ok(service.authenticateUser(userDTO));
    }
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return ResponseEntity.ok(service.register(userDTO));
    }
    @PostMapping("/createAdmin")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody @Valid UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return ResponseEntity.ok(service.registerAdmin(userDTO));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(service.refreshToken(refreshTokenRequest));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        service.logout();
        return ResponseEntity.ok("user logout. Username is: "+userDetails.getUsername());
    }
    @PostMapping("/addMoney")
    public ResponseEntity<UserDTO> updateBalance(@RequestParam Long value, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.updateCurrentBalance(value, userDetails.getUsername()));
    }
}
