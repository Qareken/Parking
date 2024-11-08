package com.example.Parking.repository;

import com.example.Parking.entity.Role;
import com.example.Parking.entity.enumConstants.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByAuthority(RoleType authority);
}