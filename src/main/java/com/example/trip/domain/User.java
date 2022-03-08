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
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String username;

    // 탈퇴 여부
    @Column
    private boolean memberstatus;

    @Column
    private Long kakaoId;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column
    private String refreshToken;

    @Builder
    public User(String email, String username, boolean memberstatus, Long kakaoId, Role role, String refreshToken) {
        this.email = email;
        this.username = username;
        this.memberstatus = memberstatus;
        this.kakaoId = kakaoId;
        this.role = role;
        this.refreshToken = refreshToken;
    }

    public void setUsername(String username) { this.username = username; }
    public void setMemberstatus(boolean memberstatus) { this.memberstatus = memberstatus; }

}
