package com.example.trip.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class FeedDetailLoc {
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

    @Lob
    private String location;

    private String comment;

    private String city;
}
