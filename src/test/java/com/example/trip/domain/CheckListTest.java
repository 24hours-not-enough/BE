package com.example.trip.domain;

import com.example.trip.dto.request.CheckListsRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CheckListTest {
    Long id;
    String check_item;
    Boolean is_checked;
    Boolean is_locked;
    String checkName;

    @BeforeEach
    void init(){
        id = 1L;
        check_item = "체크아이템";
        is_checked = false;
        is_locked = false;
        checkName = "체크이름";

    }

    @Test
    @DisplayName("체크리스트 등록 및 수정 정상 케이스")
    void createCheckList_Normal() {

        CheckListsRequestDto requestDto = CheckListsRequestDto.builder()
                .checkName(checkName)
                .isChecked(is_checked)
                .build();

// when
        CheckList checkList = CheckList.builder()
                .check_item(requestDto.getCheckName())
                .is_checked(requestDto.getIsChecked())
                .build();

// then
        assertNull(checkList.getId());
        assertNull(checkList.getIs_locked());
        assertEquals(checkName, requestDto.getCheckName());
        assertEquals(is_checked, requestDto.getIsChecked());
    }

}
