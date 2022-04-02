package com.example.trip.service.impl;

import com.example.trip.domain.FeedComment;
import com.example.trip.domain.FeedDetailLoc;
import com.example.trip.domain.Image;
import com.example.trip.domain.User;
import com.example.trip.dto.request.FeedRequestDto;
import com.example.trip.repository.CommentRepository;
import com.example.trip.repository.FeedDetailLocRepository;
import com.example.trip.repository.LikeRepository;
import com.example.trip.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.trip.domain.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedCommentServiceImplTest {

    @Mock
    FeedDetailLocRepository feedDetailLocRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    CommentRepository commentRepository;

    FeedDetailLoc feedDetailLoc;

    User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .email("kim@naver.com")
                .username("babo")
                .password("1234")
                .memberstatus(true)
                .socialaccountId("KAKAO_1234")
                .role(USER)
                .image(Image.builder().build())
                .build();


        when(userRepository.save(any(User.class))).thenReturn(user);
        userRepository.save(user);

        List<FeedDetailLoc> detailLocs = new ArrayList<>();

        feedDetailLoc = FeedDetailLoc.builder()
                .memo("감귤농장에서 귤 따기")
                .build();

        detailLocs.add(feedDetailLoc);

        when(feedDetailLocRepository.saveAll(detailLocs)).thenReturn(detailLocs);
        feedDetailLocRepository.saveAll(detailLocs);
    }

    @Test
    @DisplayName("댓글 생성 - 정상 케이스")
    void createComment_normal() {
        //given
        FeedRequestDto.FeedRequestCommentRegisterDto dto = new FeedRequestDto.FeedRequestCommentRegisterDto("저도 가보고 싶어요~");

        FeedComment feedComment = FeedComment.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .content(dto.getContent())
                .build();

        when(commentRepository.save(feedComment)).thenReturn(feedComment);

        //when
        FeedComment comment = commentRepository.save(feedComment);

        //then
        assertEquals("저도 가보고 싶어요~", comment.getContent());
        assertEquals("감귤농장에서 귤 따기", comment.getFeedDetailLoc().getMemo());
        assertEquals("babo", comment.getUser().getUsername());
        assertEquals("kim@naver.com", comment.getUser().getEmail());
        assertEquals("KAKAO_1234", comment.getUser().getSocialaccountId());
        assertEquals(USER, comment.getUser().getRole());
    }

    @Test
    @DisplayName("댓글 수정 - 정상 케이스")
    void modifyComment_normal() {
        //given
        FeedRequestDto.FeedRequestCommentRegisterDto dtoCreate = new FeedRequestDto.FeedRequestCommentRegisterDto("저도 가보고 싶어요~");

        FeedComment feedComment = FeedComment.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .content(dtoCreate.getContent())
                .build();

        when(commentRepository.save(feedComment)).thenReturn(feedComment);
        FeedComment comment = commentRepository.save(feedComment);

        FeedRequestDto.FeedRequestCommentModifyDto dtoModify = new FeedRequestDto.FeedRequestCommentModifyDto("다음에 함께 가요!");

        //when
        comment.update(dtoModify);

        //then
        assertEquals("다음에 함께 가요!", comment.getContent());
        assertNotEquals("저도 가보고 싶어요~", comment.getContent());
    }

    @Test
    @DisplayName("댓글 삭제 - 정상 케이스")
    void deleteComment_normal() {

        // given
        FeedRequestDto.FeedRequestCommentRegisterDto dto = new FeedRequestDto.FeedRequestCommentRegisterDto("저도 가보고 싶어요~");

        FeedComment feedComment = FeedComment.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .content(dto.getContent())
                .build();

        Long expectedId = 12345L;

        // ID 값은 원래 DB에서 자동생성 -> 이렇게 처리해서 ID 값을 임의로 줄 수 있음
        doAnswer(invocation -> {
            ReflectionTestUtils.setField((FeedComment) invocation.getArgument(0), "id", expectedId);
            return null;
        }).when(commentRepository).save(feedComment);

        // 그렇다고 해서, 이 save 후 반환되는 객체에 대한 선언을 해주지 않으면 안 됨
        when(commentRepository.save(feedComment)).thenReturn(feedComment);
        FeedComment comment = commentRepository.save(feedComment);

        // when
        commentRepository.deleteById(comment.getId());

        // then
        assertFalse(commentRepository.existsById(12345L));
    }
}