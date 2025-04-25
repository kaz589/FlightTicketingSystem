package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SeatLockScheduler {
	@Autowired
    private SeatService seatService; // 或 SeatLockService

    // 每分鐘執行一次，釋放過期座位鎖定
    @Scheduled(fixedRate = 60000)
    public void autoReleaseExpiredLocks() {
    	System.out.println("AAA");
        seatService.releaseExpiredLocks();
    }
}
