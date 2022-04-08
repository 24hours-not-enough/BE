package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthFeedNotFoundException;
import com.example.trip.advice.exception.FeedNotFoundException;
import com.example.trip.domain.*;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.dto.response.AllLocationsDto;
import com.example.trip.dto.response.FeedResponseDto;
import com.example.trip.repository.*;
import com.example.trip.repository.FeedRepository;
import com.example.trip.repository.FeedLocationRepository;
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
    public List<AllLocationsDto> findEachLocations(FeedRequestDto.FeedRequestMainGetDto feedRequestMainGetDto) {
        // x,y좌표 범위 내에 있는 feeD LOCATION을 모두 가져온다.
        List<FeedLocation> feedLocations = feedLocationRepository.findLocations(
                feedRequestMainGetDto.getLeftX(), feedRequestMainGetDto.getRightX(),
                feedRequestMainGetDto.getTopY(), feedRequestMainGetDto.getBottomY());

        List<AllLocationsDto> allLocationsDtos = new ArrayList<>();

        //가져온 정보들에서 필요한 정보들을 Dto에 담아준다.
        for (FeedLocation feedLocation : feedLocations) {
            AllLocationsDto allLocationsDto = AllLocationsDto.builder()
                    .placeId(feedLocation.getId())
                    .locationName(feedLocation.getName())
                    .latitude(feedLocation.getLatitude())
                    .longitude(feedLocation.getLongitude())
                    .build();
            //Dto에 자식 엔티티 정보들도 매핑 해준다.
            //아래 함수에서는 자식함수들을 타고 들어가며 부모와 자식 엔티티들을 매핑해준다.
            allLocationsDto.toFeedPerLocation(feedLocation.getFeedDetailLocs());

            //리스트에 만들어준 데이터를 담아준다.
            allLocationsDtos.add(allLocationsDto);
        }
        return allLocationsDtos;
    }

    //피드 등록
    @Override
    @Transactional
    public List<Long> registerFeed(User user, FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto) {
        // 피드 데이터를 조회해올 때 정보가 변동이 생기므로 캐시에서 삭제해준다.
        cacheManager.getCache("allFeeds").clear();

        // feed 저장
        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .travelStart(feedRequestRegisterDto.getTravelStart())
                .travelEnd(feedRequestRegisterDto.getTravelEnd())
                .build();

        //피드 정보 저장
        Feed newFeed = feedRepository.save(feed);

        //feed Detail 저장
        // Collection.forEach를 사용하지않고, stream.forEach()를 쓰는 이유는, 리스트가 반복을 하면서 변경이 되기 떄문에,
        //stream 객체를 활용하여 반복 도중 데이터가 변경이 되어도 오류가 나지않게 한다.
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

        //피드 디테일 수정
        List<FeedDetail> feedDetails = feed.getFeedDetail();
        feedDetails.forEach(x -> x.update(
                feedRequestModifyDto.getFeedDetail().get(feedDetails.indexOf(x))
        ));

        // 피드 디테일 로케이션 수정
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

        //피드 로케이션 수정
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

        //피드디테일로케이션이미지 수정
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

        upsert(feed, feedRequestModifyDto);

    }

    public void upsert(Feed feed, FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto){

// feed detail 추가로 삽입
        //기존의 데이터
        List<FeedDetail> beforeFeedDetails = feed.getFeedDetail();

        feedRequestModifyDto.getFeedDetail().stream()
                .forEach(x -> x.setFeed(feed));

        //추가가하려고 하는 피드 디테일 데이터
        List<FeedDetail> afterFeedDetails = feedRequestModifyDto.getFeedDetail();

        int beforeSize = beforeFeedDetails.size();
        int afterSize = afterFeedDetails.size();

        if(beforeSize < afterSize){
            for(int i = beforeSize ; i < afterSize; i++){
                feedDetailRepository.save(afterFeedDetails.get(i));
            }

        }


// feed detail loc 추가로 삽입
        //기존의 데이터
        List<FeedDetailLoc> beforeFeedDetailLocs = beforeFeedDetails.stream()
                .map(FeedDetail::getFeedDetailLoc)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        feedRequestModifyDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.setFeedDetail(x)));


        //추가하려고 하는 피드 디테일 데이터
        List<FeedDetailLoc> afterFeedDetailLocs = feedRequestModifyDto.getFeedDetail().stream()
                .map(FeedDetail::getFeedDetailLoc)
                .flatMap(List<FeedDetailLoc>::stream)
                .collect(Collectors.toList());

        int beforeFDLSize = beforeFeedDetailLocs.size();
        int afterFDLSize = afterFeedDetailLocs.size();

        if(beforeFDLSize < afterFDLSize){
            for(int i = beforeFDLSize ; i < afterFDLSize; i++){
                feedDetailLocRepository.save(afterFeedDetailLocs.get(i));
            }

        }

// feed location 추가로 삽입

        //기존의 데이터
        List<FeedLocation> beforeFeedLocations = beforeFeedDetailLocs.stream()
                .map(FeedDetailLoc::getFeedLocation)
                .collect(Collectors.toList());



        //추가하려고 하는 피드 디테일 데이터
        List<FeedLocation> afterFeedLocations = feedRequestModifyDto.getFeedDetail().stream()
                .map(FeedDetail::getFeedDetailLoc)
                .flatMap(List<FeedDetailLoc>::stream)
                .map(FeedDetailLoc::getFeedLocation)
                .collect(Collectors.toList());

        int beforeFLSize = beforeFeedLocations.size();
        int afterFLSize = afterFeedLocations.size();

        if(beforeFLSize < afterFLSize){
            for(int i = beforeFLSize ; i < afterFLSize; i++){
                feedLocationRepository.save(afterFeedLocations.get(i));
            }

        }


// feed detail loc img 추가로 삽입
//기존의 데이터
        List<FeedDetailLocImg> beforeFeedDetailLocImg = beforeFeedDetailLocs.stream()
                .map(FeedDetailLoc::getFeedDetailLocImg)
                .flatMap(List<FeedDetailLocImg>::stream)
                .collect(Collectors.toList());



        //추가하려고 하는 피드 디테일 데이터
        feedRequestModifyDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.getFeedDetailLocImg().forEach(z -> z.setFeedDetailLoc(y))));

        List<FeedDetailLocImg> afterFeedDetailLocImg = feedRequestModifyDto.getFeedDetail().stream()
                .map(FeedDetail::getFeedDetailLoc)
                .flatMap(List<FeedDetailLoc>::stream)
                .map(FeedDetailLoc::getFeedDetailLocImg)
                .flatMap(List<FeedDetailLocImg>::stream)
                .collect(Collectors.toList());

        int beforeFDLISize = beforeFeedDetailLocImg.size();
        int afterFDLISize = afterFeedDetailLocImg.size();

        if(beforeFDLISize < afterFDLISize){
            for(int i = beforeFDLISize ; i < afterFDLISize; i++){
                feedDetailLocImgRepository.save(afterFeedDetailLocImg.get(i));
            }

        }
    }


    @Override
    @Transactional
    public void deleteFeed(User user, Long feedId) {
        // 피드를 올린 사람만 권한이 있어야함
        List<Feed> myFeed = feedRepository.findByIdAndUserId(feedId, user.getId());

        if (myFeed.isEmpty()) {
            throw new AuthFeedNotFoundException();
        }
        //피드 번호로 피드 삭제
        feedRepository.deleteById(feedId);
    }

    @Override
    public List<FeedResponseDto.GetFeed> getFeeds(Long userId) {
        ArrayList<FeedResponseDto.GetFeed> arr = new ArrayList<>();
        //User Id로 피드 찾기
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

