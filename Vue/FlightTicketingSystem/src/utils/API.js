import instance from "./http";

// 機場相關 API
export const ApiAirport = {
  testPage: (page, size) => instance.get(`/airports/Test/${page}/${size}`),
  DistinctCities: () => instance.get("/airports/distinct-cities"),
  getAllAirports: () => instance.get("/airports/getAll"),
  DistinctCountryRegions: () =>
    instance.get("/airports/distinct-country-regions"),
  DistinctAirportName: () =>
    instance.get("/airports/distinct-AirportName"),
  searchAirports: (
    keyword,
    city,
    countryRegion,
    page,
    size,
    sortBy,
    sortOrder
  ) =>
    instance.get(`/airports/search`, {
      params: {
        keyword: keyword || "all", // 默認值為 "all"
        city: city || "all", // 默認值為 "all"
        countryRegion: countryRegion || "all", // 默認值為 "all"
        page: page || 1, // 默認值為第 1 頁
        size: size || 20, // 默認每頁 20 條
        sortBy: sortBy || "airportsId", // 默認排序字段
        sortOrder: sortOrder || "asc", // 默認排序方向 (升序)
      },
    }),
  // 新增機場
  addAirport: (airport) => instance.post("/airports", airport),

  // 刪除機場
  deleteAirport: (id) => instance.delete(`/airports/${id}`),
  // 更新機場
  updateAirport: (id, airport) => instance.put(`/airports/${id}`, airport),
};
//航班相關 API
export const ApiFlight = {
  // 獲取所有航班
  getAllFlights: () => instance.get("/flight/getAll"),

  // 更新航班
  updateFlight: (id, flightDetails) => instance.put(`/flight/${id}`, flightDetails),

  // 刪除航班
  deleteFlight: (id) => instance.delete(`/flight/${id}`),

  // 新增航班
  createFlight: (flight) => instance.post("/flight", flight),

  // 搜尋航班
  searchFlights: (
    originIata,
    destinationIata,
    startTime,
    endTime,
    airplaneModelName,
    page = 0,
    size = 10
  ) =>
    instance.get("/flight/search", {
      params: {
        originIata: originIata || null,
        destinationIata: destinationIata || null,
        startTime: startTime || null,
        endTime: endTime || null,
        airplaneModelName: airplaneModelName || null,
        page,
        size,
      },
    }),
};
export const ApiMember = {
  getMember: (id) => instance.get(`/member/GetMember/${id}`),
  getAllMember: () => instance.get(`/member/GetAllMember`),
  updateMember: (memberUpdate) =>
    instance.put(`/member/UpdateMember`, memberUpdate),
  insertMember: (memberInsert) =>
      instance.post(`/member/InsertMember`, memberInsert)
      .then((response) => {
        console.log("API 請求成功:", response);
        return response.data; // 返回 API 響應資料
      })
      .catch((error) => {
        console.error("API 請求錯誤:", error);
        throw error; // 抛出錯誤以便外層處理
      }),
  deleteMemberById: (id) => instance.delete(`/member/DeleteMember/${id}`),
};


export  const ApiSeats={
  getAllSeats: () => instance.get("/seat/getAll"),
};

// 票務相關 API
export const ApiTicket = {
  // 查詢所有票務
  getAllTickets: () => instance.get("/Ticket/getAll"),
  // 創建新票務
  createTicket: (ticketDTO) => instance.post("/Ticket", ticketDTO),
  // 更新票務
  updateTicket: (id, ticketDetails) =>
    instance.put(`/Ticket/${id}`, ticketDetails),
   // 刪除票務
   deleteTicket: (id) => instance.delete(`/Ticket/${id}`),
};
