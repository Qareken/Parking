package com.example.Parking.service.impl;

import com.example.Parking.dto.UserDTO;
import com.example.Parking.entity.Role;
import com.example.Parking.entity.Users;
import com.example.Parking.entity.enumConstants.RoleType;
import com.example.Parking.exception.EntityNotFoundException;
import com.example.Parking.exception.PhoneNumberAlreadyExistsException;
import com.example.Parking.mapper.UserMapper;
import com.example.Parking.repository.RoleRepository;
import com.example.Parking.repository.UserRepository;
import com.example.Parking.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
//    @PostConstruct
//    public void initializeRoles() {
//
//        for (RoleType roleType : RoleType.values()) {
//            if (roleRepository.findByAuthority(roleType).isEmpty()) {
//                Role role = Role.from(roleType);
//                roleRepository.save(role);
//            }
//        }
//    }




//    @PostConstruct
//    public void initializeUsers() {
//        Optional<Role> adminRoleOptional = roleRepository.findByAuthority(RoleType.ROLE_ADMIN);
//
//        if (adminRoleOptional.isPresent()) {
//            Role adminRole = adminRoleOptional.get();
//
//
//            if (userRepository.findByRole(adminRole).isEmpty()) {
//                Set<Role> roles = new HashSet<>();
//                roles.add(adminRole);
//                String password = "admin";
//                // Создаем пользователя-администратора
//                Users adminUser = Users.builder()
//                        .name("admin")
//                        .password(passwordEncoder.encode(password))
//                        .phoneNumber("+998949009393")
//                        .balance(BigDecimal.valueOf(0))
//                        .roles(roles)
//                        .build();
//
//                userRepository.save(adminUser);
//            }
//        }
//
//    }

    @Override
    public Users findByPhoneNumber(String phone) {
        return userRepository.findUsersByPhoneNumber(phone).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Phone with {} is not found", phone)));
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        var user = userMapper.toEntity(userDTO);
        if(userRepository.findUsersByPhoneNumber(user.getPhoneNumber()).isPresent()){
            throw new PhoneNumberAlreadyExistsException(MessageFormat.format("This {} already exist. Please Try with another phone number", user.getPhoneNumber()));
        }
        Role defaultRole = roleRepository.findByAuthority(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found."));



        user.getRoles().add(defaultRole);

        return userMapper.toDTO(userRepository.save(user));

    }

    @Override
    public Users updateByPrice(Users users) {
        return userRepository.save(users);
    }

    @Override
    public UserDTO updateCurrentBalance(Long money, String phoneNumber) {
        var currentUser = findByPhoneNumber(phoneNumber);
        var balance = currentUser.getBalance().add(BigDecimal.valueOf(money));
        currentUser.setBalance(balance);
        return userMapper.toDTO(updateByPrice(currentUser));
    }

    @Override
    public UserDTO saveAdmin(UserDTO userDTO) {
        var user = userMapper.toEntity(userDTO);
        if(userRepository.findUsersByPhoneNumber(user.getPhoneNumber()).isPresent()){
            throw new PhoneNumberAlreadyExistsException(MessageFormat.format("This {} already exist. Please Try with another phone number", user.getPhoneNumber()));
        }
        Role defaultRole = roleRepository.findByAuthority(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found."));
        user.getRoles().add(defaultRole);
        return userMapper.toDTO(userRepository.save(user));

    }

}



