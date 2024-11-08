package com.example.Parking.mapper;

import com.example.Parking.dto.UserDTO;
import com.example.Parking.entity.Role;
import com.example.Parking.entity.Users;
import com.example.Parking.entity.enumConstants.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToStrings")
    UserDTO toDTO(Users user);
    @Mapping(source = "roles", target = "roles", qualifiedByName = "stringsToRoles")
    Users toEntity(UserDTO userDTO);
    @Named("rolesToStrings")
    default Set<String> mapRolesToStrings(Set<Role> roles) {
        return roles.stream().map(Role::getAuthority).map(RoleType::getLabel).collect(Collectors.toSet());
    }
    @Named("stringsToRoles")
    default Set<Role> mapStringsToRoles(Set<String> roleNames) {
        return roleNames.stream().map(RoleType::fromLabel
        ).map(Role::from).collect(Collectors.toSet());
    }
}
