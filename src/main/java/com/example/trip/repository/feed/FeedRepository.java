package com.example.trip.repository.feed;

import com.example.trip.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedCustomRepository {
    @Query("select f from Feed f left join fetch f.user u where u.id = :userId and f.id = :feedId")
    Optional<Feed> FindFeedByUserId(Long userId, Long feedId);

//    List<Feed> findByUserId(Long userId, Pageable pageable);
//    List<Feed> findByUserId(Long userId);

    @Query("select f from Feed f where f.id = ?1 and f.user.id = ?2")
    List<Feed> findByIdAndUserId(Long feedId, Long userId);

}
