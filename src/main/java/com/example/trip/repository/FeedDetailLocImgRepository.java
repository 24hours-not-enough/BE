package com.example.trip.repository;

import com.example.trip.domain.FeedDetail;
import com.example.trip.domain.FeedDetailLocImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedDetailLocImgRepository extends JpaRepository<FeedDetailLocImg, Long> {
    @Query("select i from FeedDetailLocImg i left join fetch i.feedDetailLoc l left join fetch l.feedDetail d left join fetch d.feed f where f.id=:feedId")
    List<FeedDetailLocImg> FindFeedandImgs(@Param("feedId")Long feedId);

    @Query("select i.imgUrl from FeedDetailLocImg i where i.feedDetailLoc.id = :feeddetaillocId")
    List<String> FindImgsByFeedDetailLocId(@Param("feeddetaillocId") Long feeddetaillocId);

}
