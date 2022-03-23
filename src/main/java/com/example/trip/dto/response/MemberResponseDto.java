package com.example.trip.dto.response;

import com.example.trip.domain.Member;
import com.example.trip.domain.Plan;
import com.example.trip.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long userId;

    private String userProfileImage;

    private String userName;

    private Boolean roomRep;

    public MemberResponseDto(Member member) {
        this.userProfileImage = member.getUser().getImage().getFile_store_course();
        this.userId = member.getUser().getId();
        this.userName = member.getUser().getUsername();
        this.roomRep = member.getRoom_rep();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class invite{

        private String fileStoreCourse;

        private String nickname;

        private Long userId;

        private Boolean roomRep;

        public invite(Member member) {
            this.fileStoreCourse = member.getUser().getImage().getFile_store_course();
            this.nickname = member.getUser().getUsername();
            this.userId = member.getId();
            this.roomRep = member.getRoom_rep();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class inviteList{
        private String nickName;

        private String userProfileImage;

        private String title;

        private Long planId;

        public inviteList(Member member) {
            this.nickName = member.getUser().getUsername();
            this.userProfileImage = member.getUser().getImage().getFile_store_course();
            this.title = member.getPlan().getTitle();
            this.planId = member.getPlan().getId();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class AllMember{
        private Long memberId;

        private Boolean roomRep;

        private Boolean active;
    }
}
