package com.livear.LiveAR.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    String nickname;

    String email;

    String password; //기기 고유 번호

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    UserRole userRole;
}
