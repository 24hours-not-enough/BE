package com.example.trip.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    // 탈퇴 여부
    @Column
    private boolean memberstatus;

    @Column
    private Long kakaoId;

    @Column
    private String googleId;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column
    private String refreshToken;

    @Embedded
    private Image image;

    @Builder
    public User(String email, String username, String password, boolean memberstatus, Long kakaoId, String googleId, Role role, String refreshToken, Image image) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.memberstatus = memberstatus;
        this.kakaoId = kakaoId;
        this.googleId = googleId;
        this.role = role;
        this.refreshToken = refreshToken;
        this.image = image;
    }
}
