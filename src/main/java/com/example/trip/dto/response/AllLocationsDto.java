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
public class AllLocationsDto {
    private Long placeId;

    private String locationName;

    private String latitude;

    private String longitude;

    private List<FeedPerLocationDto> feedPerLocations;

    public List<FeedPerLocationDto> toFeedPerLocation(List<FeedDetailLoc> feedDetailLocs){
        feedPerLocations = new ArrayList<>();
        for(FeedDetailLoc feedDetailLoc : feedDetailLocs){
            FeedPerLocationDto feedPerLocationDto = FeedPerLocationDto.builder()
                    .feedId(feedDetailLoc.getId())
                    .date(feedDetailLoc.createdAt)
                    .memo(feedDetailLoc.getMemo())
                    .build();

            //images 매핑
            feedPerLocationDto.mapFeedImages(feedDetailLoc.getFeedDetailLocImg());
            //likes 매핑
            feedPerLocationDto.mapFeedLikes(feedDetailLoc.getLikes());
            //user 매핑
            feedPerLocationDto.mapFeedUser(feedDetailLoc.getFeedDetail().getFeed().getUser());
            //comment 매핑
            feedPerLocationDto.mapFeedComment(feedDetailLoc.getFeedComments());
            feedPerLocations.add(feedPerLocationDto);
        }
        return feedPerLocations;
    }

}
