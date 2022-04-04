package com.example.trip.repository.feed;

import com.example.trip.dto.response.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.trip.domain.QFeedLocation.feedLocation;
import static com.example.trip.domain.QFeedDetailLoc.feedDetailLoc;
import static com.example.trip.domain.QFeedDetailLocImg.feedDetailLocImg;
import static com.example.trip.domain.QFeed.feed;
import static com.example.trip.domain.QFeedDetail.feedDetail;
import static com.example.trip.domain.QLikes.likes;
import static com.example.trip.domain.QUser.user;
import static com.example.trip.domain.QFeedComment.feedComment;

//@Repository
//public class FeedCustomRepositoryImpl implements FeedCustomRepository {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    public FeedCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
//        this.jpaQueryFactory = jpaQueryFactory;
//    }
//
//    @Override
//    public List<FeedResponseDto.GetFeed> findByUserId(Long userId) {
//        return jpaQueryFactory
//                .select(Projections.constructor(FeedResponseDto.GetFeed.class,
//                        feed.id, feed.title, feed.travelStart, feed.travelEnd,
//                        Projections.list(Projections.constructor(FeedDetailResponseDto.GetFeedDetail.class,
//                                feedDetail.day,
//                                Projections.list(Projections.constructor(FeedDetailLocResponseDto.GetFeedDetailLoc.class,
//                                        feedDetailLoc.id, feedDetailLoc.createdAt, feedDetailLoc.feedLocation.name,
//                                        Projections.list(Projections.constructor(FeedDetailLocImgResponseDto.ImgUrl.class, feedDetailLocImg.imgUrl)),
//                                        Projections.list(Projections.constructor(LikesResponseDto.GetUserId.class, likes.user.id)),
//                                        Projections.constructor(UserResponseDto.GetUser.class, feed.user),
//                                        feedDetailLoc.memo,
//                                        Projections.list(Projections.constructor(FeedCommentResponseDto.GetComment.class, feedComment.id,
//                                                Projections.constructor(UserResponseDto.GetUser.class, feedComment.user),
//                                                feedComment.content))))
//                        )),
//                        feed.feedDetail))
//                .from(feed)
//                .leftJoin(user).on(feed.user.id.eq(user.id))
//                .leftJoin(feedDetail).on(feed.id.eq(feedDetail.feed.id))
//                .leftJoin(feedDetailLoc).on(feedDetail.id.eq(feedDetailLoc.feedDetail.id))
//                .leftJoin(feedDetailLocImg).on(feedDetailLoc.id.eq(feedDetailLocImg.feedDetailLoc.id))
//                .leftJoin(likes).on(likes.feedDetailLoc.id.eq(feedDetailLoc.id))
//                .leftJoin(user).on(user.id.eq(likes.user.id))
//                .leftJoin(feedComment).on(feedComment.feedDetailLoc.id.eq(feedDetailLoc.id))
//                .leftJoin(user).on(user.id.eq(feedComment.user.id))
//                .where(feed.user.id.eq(userId))
//                .fetch();
//    }
//}
