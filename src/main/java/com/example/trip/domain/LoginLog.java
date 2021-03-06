package com.example.trip.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginLog extends TimeStamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_log_id")
    private Long id;

    private String email;

    private String login_ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public LoginLog(Long id, String email, String login_ip, User user) {
        this.id = id;
        this.email = email;
        this.login_ip = login_ip;
        this.user = user;
    }
}
