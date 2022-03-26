package com.example.trip.domain;


import com.example.trip.dto.request.FeedRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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