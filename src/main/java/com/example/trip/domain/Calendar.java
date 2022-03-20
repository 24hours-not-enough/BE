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

    @OneToMany(mappedBy = "calendar")
    private List<CalendarDetails> calendarDetails = new ArrayList<>();

    @Builder
    public Calendar(String days, Plan plan, Boolean is_locked){
        this.days = days;
        this.plan = plan;
        this.is_locked = is_locked;
    }

    public void updateCalendarLock() {
        this.is_locked = true;
    }

    public void updateCalendarUnlock() {
        this.is_locked = false;
    }
}
