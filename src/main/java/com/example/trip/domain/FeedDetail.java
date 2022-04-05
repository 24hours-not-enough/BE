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
public class FeedDetail{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feedDetail")
    private List<FeedDetailLoc> feedDetailLoc;

    private String day;

    //피드 디테일 수정
    public void update(FeedDetail feedDetails){
        this.day = feedDetails.day;
    }
}
