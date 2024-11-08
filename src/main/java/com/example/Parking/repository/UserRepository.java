package com.example.Parking.repository;


import com.example.Parking.entity.Role;
import com.example.Parking.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<Users> findUsersByName(String name);
    @EntityGraph(attributePaths = {"roles"})
    Optional<Users> findUsersByPhoneNumber(String name);

    @Query("SELECT u FROM Users u JOIN u.roles r WHERE r = :role")
    Set<Users> findByRole(@Param("role") Role role);
}
