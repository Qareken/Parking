package com.example.Parking.entity.enumConstants;



import com.example.Parking.exception.BadRequestException;

import java.text.MessageFormat;

public enum RoleType {
    ROLE_ADMIN("admin"), ROLE_USER("user"), ROLE_MODERATOR("moderator");
    private final String label;

    RoleType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    public static RoleType fromLabel(String label){
        for(RoleType roleType : RoleType.values()){
            if(roleType.getLabel().equalsIgnoreCase(label)){
                return roleType;
            }
        }
        throw new BadRequestException(MessageFormat.format("No Role with label: {} found",label));
    }
}
