package com.demo.dto;

public class MemberAuthorityDto {
    private Integer id;
    private String authorityDetail;

    // 無參構造函數（Spring 會用到）
    public MemberAuthorityDto() {
    }

    public MemberAuthorityDto(Integer id, String authorityDetail) {
        this.id = id;
        this.authorityDetail = authorityDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorityDetail() {
        return authorityDetail;
    }

    public void setAuthorityDetail(String authorityDetail) {
        this.authorityDetail = authorityDetail;
    }
}
