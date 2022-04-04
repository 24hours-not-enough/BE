package com.example.trip.domain;

import com.example.trip.dto.request.FeedRequestDto;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class FeedComment extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_detail_loc_id")
    private FeedDetailLoc feedDetailLoc;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //피드 댓글 수정
    public void update(FeedRequestDto.FeedRequestCommentModifyDto feedRequestCommentModifyDto){
        this.content = feedRequestCommentModifyDto.getContent();

    }
}
