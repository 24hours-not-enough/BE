package com.example.trip.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Image {

    private String file_store_course;

    private String store_file_name;

    private String file_extsn;

    private int file_size;
}
