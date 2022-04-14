package com.example.trip.dto.response;

import com.example.trip.domain.FeedDetailLoc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
//지역별로 피드 정보를 담고 있다.
public class AllLocationsDto {
    private Long placeId;

    //지역 이름
    private String locationName;

    //위도
    private double latitude;

    //경도
    private double longitude;

    //각 지역마다의 피드 정보들
    private List<FeedPerLocationDto> feedPerLocations;

    //정보를 꺼내와서 Dto에 각각 담아준다.
    public List<FeedPerLocationDto> toFeedPerLocation(List<FeedDetailLoc> feedDetailLocs){
        feedPerLocations = new ArrayList<>();
        for(FeedDetailLoc feedDetailLoc : feedDetailLocs){
            FeedPerLocationDto feedPerLocationDto = FeedPerLocationDto.builder()
                    //부모 엔티티에 관련한 정보들 매핑
                    .feedId(feedDetailLoc.getId())
                    .date(feedDetailLoc.createdAt)
                    .memo(feedDetailLoc.getMemo())
                    .build();

            //각각 자식 엔티티에 관한 정보들 매핑
            //images 매핑
            feedPerLocationDto.mapFeedImages(feedDetailLoc.getFeedDetailLocImg());
            //likes 매핑
            feedPerLocationDto.mapFeedLikes(feedDetailLoc.getLikes());
            //user 매핑
            feedPerLocationDto.mapFeedUser(feedDetailLoc.getFeedDetail().getFeed().getUser());
            //comment 매핑
            feedPerLocationDto.mapFeedComment(feedDetailLoc.getFeedComments());

            //만들어진 정보를 리스트에 담기
            feedPerLocations.add(feedPerLocationDto);
        }
        return feedPerLocations;
    }

}


