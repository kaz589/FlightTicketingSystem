package com.demo.model.DTO;




public class MembershipCountDTO {
    private String membershipLevel;
    private Long count;

    public MembershipCountDTO(String membershipLevel, Long count) {
        this.membershipLevel = membershipLevel;
        this.count = count;
    }
    
    




	public String getMembershipLevel() {
		return membershipLevel;
	}
	
	

	public void setMembershipLevel(String membershipLevel) {
		this.membershipLevel = membershipLevel;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

    // getters/setters
    
}