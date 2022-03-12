package com.example.trip.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
@Getter
public class Image {

    private String file_store_course;

    private String store_file_name;

    private String file_extsn;

    private int file_size;

    @Builder
    public Image(String file_store_course, String store_file_name, String file_extsn, int file_size) {
        this.file_store_course = file_store_course;
        this.store_file_name = store_file_name;
        this.file_extsn = file_extsn;
        this.file_size = file_size;
    }
}
