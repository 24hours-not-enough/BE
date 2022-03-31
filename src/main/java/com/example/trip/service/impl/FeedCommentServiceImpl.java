package com.example.trip.service.impl;

import com.example.trip.advice.exception.AuthFeedCommentNotFoundException;
import com.example.trip.advice.exception.FeedDetailLocNotFoundException;
import com.example.trip.domain.FeedComment;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.User;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.repository.CommentRepository;
import com.example.trip.repository.FeedDetailLocRepository;
import com.example.trip.service.FeedCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedCommentServiceImpl implements FeedCommentService {

    private final CommentRepository commentRepository;
    private final FeedDetailLocRepository feedDetailLocRepository;

    @Override
    @Transactional
    public Long registerFeedComment(User user, Long feedDetailLocId, FeedRequestDto.FeedRequestCommentRegisterDto feedRequestCommentRegisterDto) {
        // 해당 피드 상세 위치 값이 있는지 체크
        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId)
                .orElseThrow(() -> new FeedDetailLocNotFoundException());

        // 피드 상세 위치 값을 참조하는 피드 커멘트 생성
        FeedComment feedComment = FeedComment.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .content(feedRequestCommentRegisterDto.getContent())
                .build();

        // 피드 커멘트 저장
        FeedComment comment = commentRepository.save(feedComment);
        return comment.getId();
    }

    @Override
    @Transactional
    public void modifyFeedComment(User user, Long commentId, FeedRequestDto.FeedRequestCommentModifyDto feedRequestCommentModifyDto) {
        // 댓글을 올린 사람만 권한이 있어야함
        FeedComment myFeedComment = commentRepository.findByIdAndUserId(commentId, user.getId());

        if (myFeedComment == null) {
            throw new AuthFeedCommentNotFoundException();
        }
//        FeedComment feedComment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 값이 없습니다."));
        myFeedComment.update(feedRequestCommentModifyDto);

    }

    @Override
    @Transactional
    public void deleteFeedComment(User user, Long commentId) {
        // 댓글을 올린 사람만 권한이 있어야함
        FeedComment myFeedComment = commentRepository.findByIdAndUserId(commentId, user.getId());

        if (myFeedComment == null) {
            throw new AuthFeedCommentNotFoundException();
        }

        commentRepository.deleteById(commentId);
    }
}
