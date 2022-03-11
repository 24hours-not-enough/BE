package com.example.trip.domain;

import com.example.trip.dto.request.MemberRequest;
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

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ColumnDefault("false")
    private Boolean room_rep;

    @Builder
    public Member(String email, Plan plan, Boolean room_rep){
        this.email = email;
        this.plan = plan;
        this.room_rep = room_rep;
    }

    public Member(MemberRequest.joinDto joinMember) {
        this.email = joinMember.getEmail();
    }
}
