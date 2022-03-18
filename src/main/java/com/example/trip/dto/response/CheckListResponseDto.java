package com.example.trip.dto.response;

import com.example.trip.domain.CheckList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CheckListResponseDto {

    private Long check_List_id;

    private String check_item;

    private Boolean is_checked;

    public CheckListResponseDto(CheckList checkList) {
        this.check_List_id = checkList.getId();
        this.check_item = checkList.getCheck_item();
        this.is_checked = checkList.getIs_checked();
    }
}
