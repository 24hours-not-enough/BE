package com.example.trip.service;

import com.example.trip.advice.exception.*;
import com.example.trip.domain.*;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.repository.LikeRepository;
import com.example.trip.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedDetailRepository feedDetailRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final FeedDetailLocImgRepository feedDetailLocImgRepository;


    public List<Feed> findAll() {

        return feedRepository.findAll();
    }

    @Transactional
    public List<Long> registerFeed(User user, FeedRequestDto.FeedRequestRegisterDto feedRequestRegisterDto) {
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

        feedDetailLocs.stream()
                .forEach(x -> feedDetailLocRepository.saveAll(x));


        //feed DetailLocImg 저장
        feedRequestRegisterDto.getFeedDetail().stream().
                forEach(x -> x.getFeedDetailLoc().forEach(y -> y.getFeedDetailLocImg().forEach(z -> z.setFeedDetailLoc(y))));

        List<List<FeedDetailLocImg>> feedDetailLocImgs = feedDetailLocs.stream()
                .flatMap(Collection::stream)
                .map(FeedDetailLoc::getFeedDetailLocImg)
                .collect(Collectors.toList());

        feedDetailLocImgs.forEach(x -> feedDetailLocImgRepository.saveAll(x));


        List<Long> newFeedDetailLocs = feedDetailLocRepository.findByFeedId(newFeed.getId());
        return newFeedDetailLocs;
    }

    @Transactional
    public void modifyFeed(User user, Long feedId, FeedRequestDto.FeedRequestModifyDto feedRequestModifyDto) {
        // 피드를 올린 사람만 권한이 있어야함
        List<Feed> myFeed = feedRepository.findByIdAndUserId(feedId, user.getId());

        if (myFeed.isEmpty()) {
            throw new AuthFeedNotFoundException();
        }


        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new FeedNotFoundException());

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

    public void deleteFeed(User user, Long feedId) {
        // 피드를 올린 사람만 권한이 있어야함
        List<Feed> myFeed = feedRepository.findByIdAndUserId(feedId, user.getId());

        if (myFeed.isEmpty()) {
            throw new AuthFeedNotFoundException();
        }
        feedRepository.deleteById(feedId);
    }
}
