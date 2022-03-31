package com.example.trip.repository.feedlocation;

import com.example.trip.dto.response.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.trip.domain.QFeedLocation.feedLocation;
import static com.example.trip.domain.QBookMark.bookMark;
import static com.example.trip.domain.QFeedDetailLoc.feedDetailLoc;
import static com.example.trip.domain.QFeedDetailLocImg.feedDetailLocImg;
import static com.example.trip.domain.QFeed.feed;
import static com.example.trip.domain.QFeedDetail.feedDetail;
import static com.example.trip.domain.QLikes.likes;
import static com.example.trip.domain.QUser.user;
import static com.example.trip.domain.QFeedComment.feedComment;



@Repository
public class FeedLocationCustomRepositoryImpl implements FeedLocationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FeedLocationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<FeedLocationResponseDto.BookMark> findBookMarkLocation(Long userId) {
        return jpaQueryFactory
                .select(Projections.constructor(FeedLocationResponseDto.BookMark.class,
                                feedLocation.id,
                                feedLocation.name,
                                feedLocation.placeAddress,
                                feedLocation.latitude,
                                feedLocation.longitude,
                                Projections.list(Projections.constructor(FeedDetailLocResponseDto.GetFeedDetailLoc.class,
                                                feedDetailLoc.id,
                                                feedDetailLoc.createdAt,
                                                feedDetailLoc.feedLocation.name,
                                                // String 으로만 받는 방법이 없을까? -> 팀원들과 논의하기
                                                Projections.list(Projections.constructor(FeedDetailLocImgResponseDto.ImgUrl.class, feedDetailLocImg.imgUrl)),
                                                Projections.list(Projections.constructor(LikesResponseDto.GetUserId.class,
                                                                likes.user.id)
                                                        ),
                                                // 왜 되는 건지 고민해보기
                                                Projections.constructor(UserResponseDto.GetUser.class, feedComment.user),
                                                feedDetailLoc.memo,
                                                Projections.list(Projections.constructor(FeedCommentResponseDto.GetComment.class,
                                                                feedComment.id,
                                                                // 왜 되는 건지 고민해보기
                                                                Projections.constructor(UserResponseDto.GetUser.class, feedComment.user),
                                                                feedComment.content)
                                                        ))
                                        ))
                        )
                .from(feedLocation)
                .innerJoin(bookMark).on(feedLocation.id.eq(bookMark.feedLocation.id), bookMark.user.id.eq(userId))
                .innerJoin(feedDetailLoc) .on(feedLocation.id.eq(feedDetailLoc.feedLocation.id))
                .leftJoin(feedDetailLocImg).on(feedDetailLoc.id.eq(feedDetailLocImg.feedDetailLoc.id))
                .innerJoin(feedDetail).on(feedDetail.id.eq(feedDetailLoc.feedDetail.id))
                .innerJoin(feed).on(feed.id.eq(feedDetail.feed.id))
                .leftJoin(likes).on(likes.feedDetailLoc.id.eq(feedDetailLoc.id))
                .leftJoin(user).on(user.id.eq(likes.user.id))
                .leftJoin(feedComment).on(feedComment.feedDetailLoc.id.eq(feedDetailLoc.id))
                .leftJoin(user).on(user.id.eq(feedComment.user.id))
                .fetch();
    }
}
