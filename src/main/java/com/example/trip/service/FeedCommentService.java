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


public interface FeedCommentService {
    Long registerFeedComment(User user, Long feedDetailLocId, FeedRequestDto.FeedRequestCommentRegisterDto feedRequestCommentRegisterDto);

    void modifyFeedComment(User user, Long commentId, FeedRequestDto.FeedRequestCommentModifyDto feedRequestCommentModifyDto);

    void deleteFeedComment(User user, Long commentId);
}
