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
    private final LikeRepository likeRepository;
    private final BookMarkRepository bookMarkRepository;

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

    @Transactional
    public void modifyFeed(Long feedId, FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto){
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new IllegalArgumentException("ID가 존재하지 않습니다. "));

        //feed Detail 수정
        feedRequestModifyDto.getFeedDetail().stream()
                .forEach(x -> x.setFeed(feed));

        //feed DetailLoc 수정
        feedRequestModifyDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.setFeedDetail(x)));

        //feed DetailLocImg 수정
        feedRequestModifyDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.getFeedDetailLocImg().forEach(z -> z.setFeedDetailLoc(y))));

        feed.update(feedRequestModifyDto);
    }

    public void deleteFeed(Long feedId){
        feedRepository.deleteById(feedId);
    }

    public void likeFeed(Long feedDetailLocId, User user){
        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId).orElseThrow(() -> new NullPointerException("해당 값이 없습니다."));
        Likes like = Likes.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .build();

        likeRepository.save(like);
    }


    public void unlikeFeed(Long feedId, User user){
        likeRepository.deleteLikeFeed(feedId, user.getId());
    }

    public void bookmarkFeed(Long feedDetailLocId, User user){
        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId).orElseThrow(() -> new NullPointerException("해당 값이 없습니다."));
        BookMark bookmark = BookMark.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .build();

        bookMarkRepository.save(bookmark);
    }


    public void unbookmarkFeed(Long feedDetailLocId, User user){
        bookMarkRepository.deleteBookmarkFeed(feedDetailLocId, user.getId());
    }
}
