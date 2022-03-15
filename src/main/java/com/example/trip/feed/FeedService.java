package com.example.trip.feed;

import com.example.trip.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedDetailRepository feedDetailRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedDetailLocImgRepository feedDetailLocImgRepository;

    public List<Feed> findAll(){

        return feedRepository.findAll();
    }

    @Transactional
    public void registerFeed(User user, FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto){
        // feed 저장
        Feed feed = Feed.builder()
                .user(user)
                .title(feedRequestRegisterDto.getTitle())
                .build();

        Feed newFeed = feedRepository.save(feed);

        //feed Detail 저장
        feedRequestRegisterDto.getFeedDetail().stream()
                .forEach(x -> x.setFeed(newFeed));
        List<FeedDetail> feedDetails = feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());

        //feed DetailLoc 저장
        feedRequestRegisterDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.setFeedDetail(x)));

        List<List<FeedDetailLoc>> feedDetailLocs = feedDetails.stream()
                .map(FeedDetail::getFeedDetailLoc)
                        .collect(Collectors.toList());

        feedDetailLocs.forEach(x -> feedDetailLocRepository.saveAll(x));

        //feed DetailLocImg 저장
        feedRequestRegisterDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.getFeedDetailLocImg().forEach(z -> z.setFeedDetailLoc(y))));

        List<List<FeedDetailLocImg>> feedDetailLocImgs = feedDetailLocs.stream()
                        .flatMap(Collection::stream)
                        .map(FeedDetailLoc::getFeedDetailLocImg)
                        .collect(Collectors.toList());



        feedDetailLocImgs.forEach(x -> feedDetailLocImgRepository.saveAll(x));
        
    }

//    @Transactional
//    public void modifyFeed(){
//        Feed feed = feedRepository.findById();
//        feed.update();
//    }

    public void deleteFeed(Long feedId){
        feedRepository.deleteById(feedId);
    }
}
