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
    private String profileImg;

    public MemberResponseDto(Member member) {
        this.profileImg = member.getUser().getImage().getFile_store_course();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class invite{

        private String file_store_course;

        private String nickname;

        private Long user_id;

        private Boolean room_rep;

        public invite(Member member) {
            this.file_store_course = member.getUser().getImage().getFile_store_course();
            this.nickname = member.getUser().getUsername();
            this.user_id = member.getId();
            this.room_rep = member.getRoom_rep();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class inviteList{
        private String nickName;

        private String file_store_course;

        private String title;

        private Long planId;

        public inviteList(Member member) {
            this.nickName = member.getUser().getUsername();
            this.file_store_course = member.getUser().getImage().getFile_store_course();
            this.title = member.getPlan().getTitle();
            this.planId = member.getPlan().getId();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class AllMember{
        private Long member_id;

        private Boolean room_rep;

        private Boolean active;
    }
}