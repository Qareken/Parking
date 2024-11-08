package com.example.Parking.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "User name is mandatory")
    @Size(max = 50, message = "User name should not exceed 50 characters")
    private String name;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+998\\d{9}$", message = "Telefon +998 dan boshlanib umuman 12 ta son bolish kerak!")
    private String phoneNumber;
    private BigDecimal balance;
    @NotNull
    private Set<String> roles;
}
