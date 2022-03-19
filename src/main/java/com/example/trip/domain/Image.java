package com.example.trip.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@NoArgsConstructor
@Embeddable
@Getter
public class Image {

    @Lob
    private String file_store_course;

    @Builder
    public Image(String file_store_course) {
        this.file_store_course = file_store_course;
    }
}
