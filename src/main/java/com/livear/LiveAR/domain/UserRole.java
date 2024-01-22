package com.livear.LiveAR.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_USER("USER"),
    ROLE_CONTRACTOR("ADMIN"),
    ROLE_ANONYMOUS("ANONYMOUS");

    private final String description;
}
