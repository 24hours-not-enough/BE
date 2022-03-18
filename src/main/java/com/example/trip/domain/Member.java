package com.example.trip.domain;

import com.example.trip.dto.request.MemberRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ColumnDefault("false")
    private Boolean room_rep;

    private Boolean active;


    @Builder
    public Member(Plan plan, Boolean room_rep, User user, Boolean active){
        this.plan = plan;
        this.room_rep = room_rep;
        this.user = user;
        this.active = active;
    }

    public void modifyActive() {
        this.active = true;
    }
}
