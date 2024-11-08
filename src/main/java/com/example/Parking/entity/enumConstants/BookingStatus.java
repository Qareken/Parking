package com.example.Parking.entity.enumConstants;

import com.example.Parking.exception.BadRequestException;

import java.text.MessageFormat;

public enum BookingStatus {
    ACTIVE("active"), CANCEL("cancel");

    private final String label;

    BookingStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static RoleType fromLabel(String label) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.getLabel().equalsIgnoreCase(label)) {
                return roleType;
            }
        }
        throw new BadRequestException(MessageFormat.format("No Status with label: {} found", label));
    }
}