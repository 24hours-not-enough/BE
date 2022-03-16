package com.example.trip.repository;

import com.example.trip.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialaccountId(String socialaccountId);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username=:nickName")
    Optional<User> findByNickName(String nickName);

    @Transactional
    @Modifying
    @Query("update User u set u.refreshToken = :refreshToken where u.email = :email")
    void updateRefreshToken(@Param(value = "email") String email, @Param(value = "refreshToken") String refreshToken);

}
