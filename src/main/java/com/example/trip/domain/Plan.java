package com.example.trip.domain;

import com.example.trip.dto.request.PlanRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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

    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Member> members = new HashSet<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Calendar> calendars = new HashSet<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<CheckList> checkLists = new HashSet<>();

    @Builder
    public Plan(String title, String travel_destination, LocalDateTime travel_start, LocalDateTime travel_end, Boolean del_tc, User user, String uuid) {
        this.title = title;
        this.travel_destination = travel_destination;
        this.travel_start = travel_start;
        this.travel_end = travel_end;
        this.del_tc = del_tc;
        this.user = user;
        this.uuid = uuid;
    }

    public static Plan of(User user, PlanRequestDto.Regist dto) {
        return Plan.builder()
                .user(user)
                .title(dto.getTitle())
                .travel_destination(dto.getTravelDestination())
                .travel_start(dto.getTravelStart())
                .travel_end(dto.getTravelEnd())
                .del_tc(true)
                .uuid(UUID.randomUUID().toString())
                .build();
    }

    public void updatePlan(PlanRequestDto.Modify modify) {
        if (modify.getTitle() != null) {
            this.title = modify.getTitle();
        }
        if (modify.getTitle() != null) {
            this.travel_destination = modify.getTravelDestination();
        }
        if (modify.getTitle() != null) {
            this.travel_start = modify.getTravelStart();
        }
        if (modify.getTitle() != null) {
            this.travel_end = modify.getTravelEnd();
        }else {
            this.del_tc = modify.getDelFl();
        }
    }

    public void deletePlan(Boolean del_tc) {
        this.del_tc = del_tc;
    }
}
