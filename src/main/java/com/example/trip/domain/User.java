package com.example.trip.domain;

import com.querydsl.core.annotations.QueryInit;
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
    private String socialaccountId;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @QueryInit("*")
    @Embedded
    private Image image;

    @Builder
    public User(String email, String username, String password, boolean memberstatus, String socialaccountId, Role role, Image image) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.memberstatus = memberstatus;
        this.socialaccountId = socialaccountId;
        this.role = role;
        this.image = image;
    }

    public void update(String username, String url, String filename) {
        this.username = username;
        this.image = new Image(url, filename);
    }

    public void deleteAccount() {
        this.socialaccountId = null;
        this.memberstatus = false;
    }
}
