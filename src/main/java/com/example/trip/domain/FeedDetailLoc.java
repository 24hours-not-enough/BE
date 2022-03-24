package com.example.trip.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class FeedDetailLoc extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_detail_loc_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_detail_id")
    private FeedDetail feedDetail;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedDetailLoc")
    private List<FeedDetailLocImg> feedDetailLocImg;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedDetailLoc")
    private List<Likes> likes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedDetailLoc")
    private List<FeedComment> feedComments;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_location_id")
    private  FeedLocation feedLocation;

    private String memo;

    public FeedDetailLoc(Long id){
        this.id = id;
    }
}