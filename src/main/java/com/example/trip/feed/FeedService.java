package com.example.trip.feed;

import com.example.trip.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

        feedRepository.save(feed);

        //feed Detail 저장
        feedDetailRepository.saveAll(feedRequestRegisterDto.getFeedDetail());

        //feed DetailLoc 저장
        feedDetailLocRepository.saveAll(feedRequestRegisterDto.getFeedDetailLoc());

        //feed DetailLocImg 저장
//        feedDetailLocImgRepository.saveAll(feedRequestRegisterDto.getFeedDetailLocImg());
        
    }

//    @Transactional
//    public void modifyFeed(){
//        Feed feed = new Feed(){};
//        feed.update();
//    }

    public void deleteFeed(Long feedId){
        feedRepository.deleteById(feedId);
    }
}
