package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthFeedNotFoundException;
import com.example.trip.advice.exception.FeedNotFoundException;
import com.example.trip.domain.*;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.response.AllLocationsDto;
import com.example.trip.dto.response.FeedDetailLocResponseDto;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.repository.*;
import com.example.trip.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.trip.dto.response.FeedDetailLocResponseDto.*;

@RequiredArgsConstructor
@Service
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final FeedDetailRepository feedDetailRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedDetailLocImgRepository feedDetailLocImgRepository;
    private final FeedLocationRepository feedLocationRepository;

    @Autowired
    CacheManager cacheManager;

    @Override
    @Cacheable(value = "allFeeds")
    public List<AllLocationsDto> findAll() {
        List<FeedLocation> feedLocations = feedLocationRepository.findAll();
        List<AllLocationsDto> allLocationsDtos = new ArrayList<>();
        for (FeedLocation feedLocation : feedLocations) {
            AllLocationsDto allLocationsDto = AllLocationsDto.builder()
                    .placeId(feedLocation.getId())
                    .locationName(feedLocation.getName())
                    .latitude(feedLocation.getLatitude())
                    .longitude(feedLocation.getLongitude())
                    .build();
            allLocationsDto.toFeedPerLocation(feedLocation.getFeedDetailLocs());
            allLocationsDtos.add(allLocationsDto);
        }
        return allLocationsDtos;
    }

    @Override
    @Transactional
    public List<Long> registerFeed(User user, FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto) {
        cacheManager.getCache("allFeeds").clear();

        // feed 저장
        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .travelStart(feedRequestRegisterDto.getTravelStart())
                .travelEnd(feedRequestRegisterDto.getTravelEnd())
                .build();

        Feed newFeed = feedRepository.save(feed);

        //feed Detail 저장
        // Collection.forEach를 사용하지않고, stream.forEach()를 쓰는 이유는, 리스트가 반복을 하면서 변경이 되기 떄문에,
        // 병렬적으로 진행하기 위해서
        feedRequestRegisterDto.getFeedDetail().stream()
                .forEach(x -> x.setFeed(newFeed));
        List<FeedDetail> feedDetails = feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());

        //feed DetailLoc 저장
        feedRequestRegisterDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.setFeedDetail(x)));

        List<FeedDetailLoc> feedDetailLocs = feedDetails.stream()
                .map(FeedDetail::getFeedDetailLoc)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        //feed Location 저장
        List<FeedLocation> feedLocations = feedDetailLocs.stream()
                .map(FeedDetailLoc::getFeedLocation)
                .collect(Collectors.toList());
        feedLocationRepository.saveAll(feedLocations);
        feedDetailLocRepository.saveAll(feedDetailLocs);

        //feed DetailLocImg 저장
        feedRequestRegisterDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.getFeedDetailLocImg().forEach(z -> z.setFeedDetailLoc(y))));

        List<FeedDetailLocImg> feedDetailLocImgs = feedDetailLocs.stream()
                .map(FeedDetailLoc::getFeedDetailLocImg)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        feedDetailLocImgRepository.saveAll(feedDetailLocImgs);

        return feedDetailLocRepository.findByFeedId(newFeed.getId());
    }


    @Override
    @Transactional
    public void modifyFeed(User user, Long feedId, FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto) {

        // 피드를 올린 사람만 권한이 있어야함
        List<Feed> myFeed = feedRepository.findByIdAndUserId(feedId, user.getId());

        if (myFeed.isEmpty()) {
            throw new AuthFeedNotFoundException();
        }

//Feed 수정

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new FeedNotFoundException());
        feed.update(feedRequestModifyDto);

        List<FeedDetail> feedDetails = feed.getFeedDetail();
        feedDetails.forEach(x -> x.update(
                feedRequestModifyDto.getFeedDetail().get(feedDetails.indexOf(x))
        ));


        List<FeedDetailLoc> feedDetailLocs = feedDetails.stream()
                .map(FeedDetail::getFeedDetailLoc)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        feedDetailLocs.forEach(x -> x.update(
                feedRequestModifyDto.getFeedDetail().stream()
                        .map(FeedDetail::getFeedDetailLoc)
                        .flatMap(List<FeedDetailLoc>::stream)
                        .collect(Collectors.toList())
                        .get(feedDetailLocs.indexOf(x)

                        )));
        List<FeedLocation> feedLocations = feedDetailLocs.stream()
                .map(FeedDetailLoc::getFeedLocation)
                .collect(Collectors.toList());

        feedLocations.forEach(x -> x.update(
                feedRequestModifyDto.getFeedDetail().stream()
                        .map(FeedDetail::getFeedDetailLoc)
                        .flatMap(List<FeedDetailLoc>::stream)
                        .map(FeedDetailLoc::getFeedLocation)
                        .collect(Collectors.toList())
                        .get(feedLocations.indexOf(x)))

        );


        List<FeedDetailLocImg> feedDetailLocImgs = feedDetailLocs.stream()
                .map(FeedDetailLoc::getFeedDetailLocImg)
                .flatMap(List<FeedDetailLocImg>::stream)
                .collect(Collectors.toList());

        feedDetailLocImgs.forEach(x -> x.update(
                feedRequestModifyDto.getFeedDetail().stream()
                        .map(FeedDetail::getFeedDetailLoc)
                        .flatMap(List<FeedDetailLoc>::stream)
                        .map(FeedDetailLoc::getFeedDetailLocImg)
                        .flatMap(List<FeedDetailLocImg>::stream)
                        .collect(Collectors.toList()).get(feedDetailLocImgs.indexOf(x))
        ));
    }


    @Override
    @Transactional
    public void deleteFeed(User user, Long feedId) {
        // 피드를 올린 사람만 권한이 있어야함
        List<Feed> myFeed = feedRepository.findByIdAndUserId(feedId, user.getId());

        if (myFeed.isEmpty()) {
            throw new AuthFeedNotFoundException();
        }
        feedRepository.deleteById(feedId);
    }

    @Override
    public List<FeedResponseDto.GetFeed> getFeeds(Long userId) {
        ArrayList<FeedResponseDto.GetFeed> arr = new ArrayList<>();
        feedRepository.findByUserId(userId).stream()
                .map(x -> arr.add(new FeedResponseDto.GetFeed(x)))
                .collect(Collectors.toList());
        return arr;
    }


    @Override
    public List<Map<String, List<GetFeedDetailLoc>>> getLikeFeeds(Long userId) {
        // 전체 좋아요한 도시의 개수
        List<FeedDetailLoc> addressList = feedDetailLocRepository.findAddressCnt(userId);

        ArrayList<Map<String, List<GetFeedDetailLoc>>> arr = new ArrayList<>();

        for (FeedDetailLoc address : addressList) {
            Map<String, List<GetFeedDetailLoc>> map = new HashMap<>();
            // 좋아요한 도시별로 장소 나눠서 List로 보여주기
            List<FeedDetailLoc> locations = feedDetailLocRepository.findLocationsByLikes(userId, address.getFeedLocation().getPlaceAddress());
            List<GetFeedDetailLoc> oneCityLocations = locations.stream().map(GetFeedDetailLoc::new).collect(Collectors.toList());
            map.put(address.getFeedLocation().getPlaceAddress(), oneCityLocations);

            arr.add(map);

        }
        return arr;
    }

}
