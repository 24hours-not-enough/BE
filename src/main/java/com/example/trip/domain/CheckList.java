package com.example.trip.domain;

import com.example.trip.dto.request.CheckListsRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_List_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String check_item;

    private Boolean is_checked;

    private Boolean is_locked;

    @Builder
    public CheckList(String check_item, Boolean is_checked, Plan plan, Boolean is_locked, User user){
        this.check_item = check_item;
        this.is_checked = is_checked;
        this.plan = plan;
        this.is_locked =is_locked;
        this.user = user;
    }

    public void updateCheckList(CheckListsRequestDto dto) {
        this.check_item = dto.getCheckName();
        this.is_checked = dto.getIsChecked();
    }
    public void updateCheckListLock(User user) {
        this.is_locked = true;
        this.user = user;
    }

    public void updateCheckListUnLock(User user) {
        this.is_locked = false;
        this.user = user;
    }
}
