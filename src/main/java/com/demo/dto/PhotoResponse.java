package com.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoResponse {
    private Long id;

    private String url;

    private String caption;

    private String attraction;
}
