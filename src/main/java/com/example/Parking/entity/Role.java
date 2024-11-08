package com.example.Parking.entity;

import com.example.Parking.entity.enumConstants.RoleType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Builder
@FieldNameConstants
@Entity
@Table(name = "authorities")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType authority;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @JsonBackReference
    private Set<Users> user = new HashSet<>();
    public GrantedAuthority toAuthority(){
        return   new SimpleGrantedAuthority(authority.name());
    }
    public static Role from(RoleType roleType){
        var role = new Role();
        role.setAuthority(roleType);
        return role;
    }
}

