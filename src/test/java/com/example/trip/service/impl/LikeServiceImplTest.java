package com.example.trip.service.impl;

import com.example.trip.domain.*;
import com.example.trip.repository.FeedDetailLocRepository;
import com.example.trip.repository.LikeRepository;
import com.example.trip.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.trip.domain.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {

    @Mock
    FeedDetailLocRepository feedDetailLocRepository;

    @Mock
    LikeRepository likeRepository;

    @Mock
    UserRepository userRepository;

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

        Long userExpectedId = 12345L;

        Mockito.lenient().doAnswer(invocation -> {
            ReflectionTestUtils.setField((User) invocation.getArgument(0), "id", userExpectedId);
            return null;
        }).when(userRepository).save(user);

        when(userRepository.save(user)).thenReturn(user);
        userRepository.save(user);

        List<FeedDetailLoc> detailLocs = new ArrayList<>();

        feedDetailLoc = FeedDetailLoc.builder()
                .memo("감귤농장에서 귤 따기")
                .build();

        detailLocs.add(feedDetailLoc);

        Long detailLocExpectedId = 6789L;

        Mockito.lenient().doAnswer(invocation -> {
            ReflectionTestUtils.setField((FeedDetailLoc) invocation.getArgument(0), "id", detailLocExpectedId);
            return null;
        }).when(feedDetailLocRepository).save(feedDetailLoc);

        when(feedDetailLocRepository.saveAll(detailLocs)).thenReturn(detailLocs);
        feedDetailLocRepository.saveAll(detailLocs);
    }

    @Test
    @DisplayName("좋아요 - 정상 케이스")
    void createLike_normal() {

        // given
        Likes like = Likes.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .build();

        // when
        when(likeRepository.save(like)).thenReturn(like);
        likeRepository.save(like);

        // then
        assertEquals("감귤농장에서 귤 따기", like.getFeedDetailLoc().getMemo());
        assertEquals("babo", like.getUser().getUsername());
        assertEquals("kim@naver.com", like.getUser().getEmail());
        assertEquals(USER, like.getUser().getRole());
        assertEquals("KAKAO_1234", like.getUser().getSocialaccountId());
    }

    @Test
    @DisplayName("좋아요 취소 - 정상 케이스")
    void cancelLike_normal() {

        // given
        Likes like = Likes.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .build();


        when(likeRepository.save(like)).thenReturn(like);
        likeRepository.save(like);

        // when
        likeRepository.deleteLikeFeed(6789L, 12345L);

        // then
        assertEquals(Collections.emptyList(), likeRepository.findByFeedDetailLocIdAndUserId(6789L, 12345L));

    }

}