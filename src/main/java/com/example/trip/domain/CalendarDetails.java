package com.example.trip.domain;

import com.example.trip.dto.request.CalendarDetailsRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_details_id")
    private Long id;

    @Column(name = "location_name")
    private String name;

    @Column(name = "location_memo")
    private String memo;

    private String latitude;

    private String longitude;

    @Column(name = "orders")
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @Builder
    public CalendarDetails(String name, String latitude, String longitude, Calendar calendar, String memo, int order){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.calendar = calendar;
        this.memo = memo;
        this.order = order;
    }
}
