package com.example.trip.domain;

import com.example.trip.dto.FeedRequestDto;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Feed extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feed")
    private List<FeedDetail> feedDetail;

    private String title;

    private LocalDateTime travelStart;

    private LocalDateTime travelEnd;

    //피드 관련 수정
    public void update(FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto){
        // 피드 수정
        this.title = feedRequestModifyDto.getTitle();
        this.travelStart = feedRequestModifyDto.getTravelStart();
        this.travelEnd = feedRequestModifyDto.getTravelEnd();
        this.feedDetail = feedRequestModifyDto.getFeedDetail();
    }
}
