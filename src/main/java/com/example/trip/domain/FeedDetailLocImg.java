package com.example.trip.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class FeedDetailLocImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_detail_loc_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_detail_loc_id")
    private FeedDetailLoc feedDetailLoc;

    private String fileName;

    private String imgUrl;

    //피드 디테일 로케이션 이미지 수정
    public void update(FeedDetailLocImg feedDetailLocImg){
        this.fileName = feedDetailLocImg.getFileName();
        this.imgUrl = feedDetailLocImg.getImgUrl();
    }
}

}
