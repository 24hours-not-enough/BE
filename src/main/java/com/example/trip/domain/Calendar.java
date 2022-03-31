package com.example.trip.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long id;

    private String days;

    private Boolean is_locked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "calendar")
    private final List<CalendarDetails> calendarDetails = new ArrayList<>();

    @Builder
    public Calendar(String days, Plan plan, Boolean is_locked, User user){
        this.days = days;
        this.plan = plan;
        this.is_locked = is_locked;
        this.user = user;
    }

    public void updateCalendarLock(User user) {
        this.is_locked = true;
        this.user = user;
    }

    public void CalendarUnLock(User user) {
        this.is_locked = false;
        this.user = user;
    }

    public void updateCalendarUnlock(String days, User user) {
        this.is_locked = false;
        this.days = days;
        this.user = user;
    }
}
