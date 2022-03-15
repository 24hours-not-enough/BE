package com.example.trip.domain;

import com.example.trip.dto.request.PlanRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    private String title;

    private String travel_destination;

    private LocalDateTime travel_start;

    private LocalDateTime travel_end;

    private Boolean del_tc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calendar> calendars = new ArrayList<>();

    @Builder
    public Plan(String title, String travel_destination, LocalDateTime travel_start, LocalDateTime travel_end, Boolean del_tc, User user) {
        this.title = title;
        this.travel_destination = travel_destination;
        this.travel_start = travel_start;
        this.travel_end = travel_end;
        this.del_tc = del_tc;
        this.user = user;
    }

    public static Plan of(User user, PlanRequestDto.Regist dto) {
        return Plan.builder()
                .user(user)
                .title(dto.getTitle())
                .travel_destination(dto.getTravel_destination())
                .travel_start(dto.getTravel_start())
                .travel_end(dto.getTravel_end())
                .del_tc(true)
                .build();
    }

    public void updatePlan(PlanRequestDto.Modify modify) {
        if (modify.getTitle() != null) {
            this.title = modify.getTitle();
        }
        if (modify.getTitle() != null) {
            this.travel_destination = modify.getTravel_destination();
        }
        if (modify.getTitle() != null) {
            this.travel_start = modify.getTravel_start();
        }
        if (modify.getTitle() != null) {
            this.travel_end = modify.getTravel_end();
        }
    }

    public void deletePlan(PlanRequestDto.Modify modify) {
        this.del_tc = modify.getDel_fl();
    }
}
