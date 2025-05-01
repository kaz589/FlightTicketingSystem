package com.demo.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteResponse {

    private Long id;
    private Integer memberId;
    private Integer cityId;
    private Date createdAt;
}
