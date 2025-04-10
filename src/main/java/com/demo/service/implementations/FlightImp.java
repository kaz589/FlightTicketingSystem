package com.demo.service.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.demo.model.AirplaneModel;
import com.demo.model.Airports;
import com.demo.model.Flight;
import com.demo.model.Seat;
import com.demo.model.DTO.FlightDTO;
import com.demo.repository.AirplaneModelRepository;
import com.demo.repository.AirportsRepository;
import com.demo.repository.FlightRepository;
import com.demo.repository.SeatRepository;
import com.demo.service.FlightService;

@Service
public class FlightImp implements FlightService{
    
	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private AirplaneModelRepository airplaneModelRepository;
	@Autowired
	private AirportsRepository airportsRepository;
	@Autowired
	private SeatRepository seatRepository;
	
	
	
	@Override
	public List<FlightDTO> findAllFlight() {
		List<FlightDTO> newFlightDTO=flightRepository.findAll().stream()
				.map(FlightDTO::new)
				.toList();
		
				
		return newFlightDTO;
	}

	@Override
	public Optional<FlightDTO> findFlightById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public ResponseEntity<Flight> createFlight(FlightDTO flight) {
		
		Flight newFlight = flightRepository.save(createFlightEntity(flight));
		
		
		    AirplaneModel models = newFlight.getAirplaneModel(); // 提取值
		    List<Seat> seats = generateSeatsForFlight(models, newFlight.getId()); // 調用方法
		    seatRepository.saveAll(seats);
		
		
		return  ResponseEntity.ok(newFlight);
		//return  ResponseEntity.ok(flightRepository.save(newFlight));
	}

	@Override
	public Flight updateFlight(int id, FlightDTO flightDetails) {
		// 查找機場是否存在
		Flight findFlight = flightRepository.findById(id)
					        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "航班未找到"));
		
		Flight newFlight = createFlightEntity(flightDetails);
		
		return flightRepository.save(newFlight);
	}

	@Override
	public void deleteFlight(int id) {
		flightRepository.deleteById(id);
		
	}

	@Override
	public Page<FlightDTO> searchFlights(String originAirport, String destinationAirport, Date startTime, Date endTime,
			String airplaneModelName, Pageable pageable) {
		
		
		originAirport=(originAirport==null||originAirport.trim().isEmpty()||originAirport.trim().equalsIgnoreCase("all"))?null:originAirport;
		destinationAirport=(destinationAirport==null||destinationAirport.trim().isEmpty()||destinationAirport.trim().equalsIgnoreCase("all"))?null:destinationAirport;
		
		return flightRepository.searchFlights(originAirport, destinationAirport, startTime, endTime, airplaneModelName, pageable);
	}
	
	
	 /**
     * 根據 FlightDTO 創建 Flight 實體
     *
     * @param flight FlightDTO 包含原始數據
     * @return Flight 實體
     */
    public Flight createFlightEntity(FlightDTO flight) {
        // 查詢起點機場
        Airports originAirport = airportsRepository.findByAirportName(flight.getOriginAirport());
        if (originAirport == null) {
            throw new RuntimeException("Origin airport not found: " + flight.getOriginAirport());
        }

        // 查詢目的地機場
        Airports destinationAirport = airportsRepository.findByAirportName(flight.getDestinationAirport());
        if (destinationAirport == null) {
            throw new RuntimeException("Destination airport not found: " + flight.getDestinationAirport());
        }

        // 查詢飛機型號
        AirplaneModel airplaneModel = airplaneModelRepository.findByModelName(flight.getModelName())
                .orElseThrow(() -> new RuntimeException("Airplane model not found: " + flight.getModelName()));

        // 創建 Flight 實體
        return flight.toEntity(airplaneModel, originAirport, destinationAirport);
    }
    
    
    public List<Seat> generateSeatsForFlight(AirplaneModel model, Integer flightId) {
        List<Seat> seats = new ArrayList<>();
        int totalRows = model.getSeatRowCount(); // 動態獲取座位排數
        char[] seatLetters = generateSeatLetters(totalRows); // 根據 totalRows 動態生成座位字母
        
        Flight flight = flightRepository.findById(flightId).orElse(null); // 如果找不到，返回 null
        // 動態定義艙等範圍
        Map<String, Range<Integer>> seatClasses = Map.of(
            "FIR", Range.of(model.getFirstClassSeatStart(), model.getFirstClassSeatEnd()),    // 頭等艙範圍
            "BUS", Range.of(model.getBusinessSeatStart(), model.getBusinessSeatEnd()), // 商務艙範圍
            "ECO", Range.of(model.getEconomySeatStart(), model.getEconomySeatEnd())   // 經濟艙範圍
        );
        int lastrow=model.getEconomySeatEnd();//最後一排
        // 遍歷每排和每個座位
        for (int row = 1; row <=lastrow ; row++) {
        	final int currentRow = row; // 定義一個 final 的臨時變數
            for (char seatLetter : seatLetters) {
                String seatNumber = row + String.valueOf(seatLetter); // 生成座位號

                // 確定座位艙等
                String seatClass = seatClasses.entrySet().stream()
                    .filter(entry -> entry.getValue().contains(currentRow)) // 找到艙等範圍包含該排的艙等
                    .map(Map.Entry::getKey) // 獲取艙等名稱
                    .findFirst()
                    .orElse("ECO"); // 默認為經濟艙

                // 創建座位並加入列表
                seats.add(new Seat(flight, seatNumber, seatClass, false));
            }
        }
System.out.println(seats);
        return seats;
    }
	
 // 根據 totalRows 動態生成座位字母
    private char[] generateSeatLetters(int totalRows) {
        int maxSeatsPerRow = Math.min(totalRows, 26); // 確保不超過字母 Z（26 個字母）
        char[] seatLetters = new char[maxSeatsPerRow];
        for (int i = 0; i < maxSeatsPerRow; i++) {
            seatLetters[i] = (char) ('A' + i); // 根據字母 ASCII 值生成座位字母
        }
        return seatLetters;
    }
	

}
