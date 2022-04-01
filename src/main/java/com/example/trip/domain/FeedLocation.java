package com.example.trip.domain;


import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_location_id")
    private Long id;

    @Column(name = "location_name")
    private String name;

    private String latitude;

    private String longitude;

    private String placeAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedLocation")
    private List<FeedDetailLoc> feedDetailLocs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedLocation")
    private List<BookMark> bookMarks;

    public void update(FeedLocation feedLocation){
        this.name = feedLocation.getName();
        this.latitude = feedLocation.getLatitude();
        this.longitude = feedLocation.getLongitude();
        this.placeAddress = feedLocation.getPlaceAddress();
    }
}