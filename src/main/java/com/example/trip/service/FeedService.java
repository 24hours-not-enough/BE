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
    private final LikeRepository likeRepository;
    private final BookMarkRepository bookMarkRepository;
    private final CommentRepository commentRepository;
    private final AwsS3Service awsS3Service;

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

    public Map<String, String> registerFeedImage(Long feedDetailLocId, List<MultipartFile> imgFiles) {
        Map<String, String> nameAndUrl = awsS3Service.uploadFile(imgFiles);
        // 해당 피드 상세 위치 값이 있는지 체크
        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId)
                .orElseThrow(() -> new FeedDetailLocNotFoundException());
        nameAndUrl.entrySet().forEach(x -> feedDetailLocImgRepository.save(FeedDetailLocImg.builder()
                .feedDetailLoc(feedDetailLoc)
                .fileName(x.getKey())
                .imgUrl(x.getValue())
                .build()));

        return nameAndUrl;
    }

    public void deleteFeedImage(FeedRequestDto.FeedRequestDeleteImgDto FeedRequestDeleteImgDto) {

        List<String> fileNames = FeedRequestDeleteImgDto.getFileNames();
        fileNames.forEach(x -> awsS3Service.deleteFile(x));
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

    public void bookmarkFeed(Long feedDetailLocId, User user) {
        // 해당 피드 상세 위치 값이 있는지 체크
        FeedDetailLoc feedDetailLoc = feedDetailLocRepository.findById(feedDetailLocId)
                .orElseThrow(() -> new FeedDetailLocNotFoundException());
        BookMark bookmark = BookMark.builder()
                .feedDetailLoc(feedDetailLoc)
                .user(user)
                .build();

        bookMarkRepository.save(bookmark);
    }

    public void unbookmarkFeed(Long feedDetailLocId, User user) {
        // 북마크를 한 사람만 권한이 있어야함
        List<BookMark> myBookmark = bookMarkRepository.findByFeedDetailLocIdAndUserId(feedDetailLocId, user.getId());

        if (myBookmark.isEmpty()) {
            throw new AuthBookMarkNotFoundException();
        }
        bookMarkRepository.deleteBookmarkFeed(feedDetailLocId, user.getId());
    }

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
