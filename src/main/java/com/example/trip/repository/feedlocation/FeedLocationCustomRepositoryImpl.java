package com.example.trip.repository.feedlocation;

import com.example.trip.dto.response.*;
import com.example.trip.dto.response.queryprojection.QUserInfo;
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
                                                Projections.list(Projections.constructor(FeedDetailLocImgResponseDto.ImgUrl.class, feedDetailLocImg.imgUrl)),
                                                Projections.list(Projections.constructor(LikesResponseDto.GetUserId.class,
                                                        likes.user.id)
                                                ),
                                                new QUserInfo(feedDetailLoc.feedDetail.feed.user),
                                                feedDetailLoc.memo,
                                                Projections.list(Projections.constructor(FeedCommentResponseDto.GetComment.class,
                                                                feedComment.id,
                                                                new QUserInfo(feedComment.user),
                                                                feedComment.content)
                                                ))
                                ))
                )
                .from(feedLocation)
                .innerJoin(feedLocation.bookMarks, bookMark).on(feedLocation.id.eq(bookMark.feedLocation.id), bookMark.user.id.eq(userId))
                .innerJoin(feedLocation.feedDetailLocs, feedDetailLoc).on(feedDetailLoc.feedLocation.id.eq(feedLocation.id))
                .leftJoin(feedDetailLoc.feedDetailLocImg, feedDetailLocImg).on(feedDetailLoc.id.eq(feedDetailLocImg.feedDetailLoc.id))
                .innerJoin(feedDetailLoc.feedDetail, feedDetail).on(feedDetailLoc.feedDetail.id.eq(feedDetail.id))
                .innerJoin(feedDetail.feed, feed).on(feedDetail.feed.id.eq(feed.id))
                .innerJoin(feed.user, user).on(feed.user.id.eq(user.id))
                .leftJoin(feedDetailLoc.likes, likes).on(feedDetailLoc.id.eq(likes.feedDetailLoc.id))
                .leftJoin(likes.user, user).on(likes.user.id.eq(user.id))
                .leftJoin(feedDetailLoc.feedComments, feedComment).on(feedDetailLoc.id.eq(feedComment.feedDetailLoc.id))
                .innerJoin(feedComment.user, user).on(feedComment.user.id.eq(user.id))
                .fetch();
    }
}