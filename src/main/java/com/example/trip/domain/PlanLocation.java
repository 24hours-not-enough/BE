package com.example.trip.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_location_id")
    private Long id;

    @Column(name = "location_name")
    private String name;

    private String latitude;

    private String longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Builder
    public PlanLocation(String name, String latitude, String longitude, Plan plan){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.plan = plan;
    }
}
