package com.demo.model.DTO;

public class ProviderCountDTO {
	
    private String provider;
    private Long count;
    
    
    
	public ProviderCountDTO(String provider, Long count) {
		super();
		this.provider = provider;
		this.count = count;
	}



	public String getProvider() {
		return provider;
	}



	public void setProvider(String provider) {
		this.provider = provider;
	}



	public Long getCount() {
		return count;
	}



	public void setCount(Long count) {
		this.count = count;
	}
    
    
    
    

}
