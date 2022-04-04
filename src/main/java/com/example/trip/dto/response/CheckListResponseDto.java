package com.example.trip.dto.response;

import com.example.trip.domain.CheckList;
import com.example.trip.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CheckListResponseDto {

    private Long checkListId;

    private String checkItem;

    private Boolean isChecked;

    public CheckListResponseDto(CheckList checkList) {
        this.checkListId = checkList.getId();
        this.checkItem = checkList.getCheck_item();
        this.isChecked = checkList.getIs_checked();
    }
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class CheckListLock{
        private Boolean isLocked;

        private String nickName;

        public CheckListLock(CheckList checkList){
            this.isLocked = checkList.getIs_locked();
            this.nickName = checkList.getUser().getUsername();
        }
    }
}
