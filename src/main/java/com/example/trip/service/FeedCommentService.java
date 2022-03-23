package com.example.trip.service;

import com.example.trip.advice.exception.AuthFeedCommentNotFoundException;
import com.example.trip.advice.exception.FeedDetailLocNotFoundException;
import com.example.trip.domain.FeedComment;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.User;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.repository.CommentRepository;
import com.example.trip.repository.FeedDetailLocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedCommentService {
    private final CommentRepository commentRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;

    public void registerFeedComment(User user, Long feedDetailLocId, FeedRequestDto.FeedRequestCommentRegisterDto feedRequestCommentRegisterDto) {
        // 해당 피드 상세 위치 값이 있는지 체크
        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId)
                .orElseThrow(() -> new FeedDetailLocNotFoundException());
        FeedComment feedComment = FeedComment.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .content(feedRequestCommentRegisterDto.getContent())
                .build();
        commentRepository.save(feedComment);

    }

    @Transactional
    public void modifyFeedComment(User user, Long commentId, FeedRequestDto.FeedRequestCommentModifyDto feedRequestCommentModifyDto) {
        // 댓글을 올린 사람만 권한이 있어야함
        List<FeedComment> myFeedComment = commentRepository.findByIdAndUserId(commentId, user.getId());

        if (myFeedComment.isEmpty()) {
            throw new AuthFeedCommentNotFoundException();
        }
        FeedComment feedComment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 값이 없습니다."));
        feedComment.update(feedRequestCommentModifyDto);

    }

    public void deleteFeedComment(User user, Long commentId) {
        // 댓글을 올린 사람만 권한이 있어야함
        List<FeedComment> myFeedComment = commentRepository.findByIdAndUserId(commentId, user.getId());

        if (myFeedComment.isEmpty()) {
            throw new AuthFeedCommentNotFoundException();
        }

        commentRepository.deleteById(commentId);
    }
}
