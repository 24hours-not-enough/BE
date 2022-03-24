package com.example.trip.service;

import com.example.trip.advice.exception.AlreadyLikeException;
import com.example.trip.advice.exception.AuthLikesNotFoundException;
import com.example.trip.advice.exception.FeedDetailLocNotFoundException;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.Likes;
import com.example.trip.domain.User;
import com.example.trip.repository.FeedDetailLocRepository;
import com.example.trip.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final FeedDetailLocRepository feedDetailLocRepository;
    private final LikeRepository likeRepository;

    public void likeFeed(Long feedDetailLocId, User user) {
        // 해당 피드 상세 위치 값이 있는지 체크
        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId)
                .orElseThrow(() -> new FeedDetailLocNotFoundException());

        //이미 좋아요한 게시글인지 체크
        List<Likes> didYouLike = likeRepository.findByFeedDetailLocIdAndUserId(feedDetailLocId, user.getId());
        if (!didYouLike.isEmpty()){
            throw new AlreadyLikeException();
        }

        Likes like = Likes.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .build();

        likeRepository.save(like);
    }


    public void unlikeFeed(Long feedDetailLocId, User user) {
        // 좋아요를 한 사람만 권한이 있어야함
        List<Likes> myLike = likeRepository.findByFeedDetailLocIdAndUserId(feedDetailLocId, user.getId());

        if (myLike.isEmpty()) {
            throw new AuthLikesNotFoundException();
        }
        likeRepository.deleteLikeFeed(feedDetailLocId, user.getId());
    }
}
