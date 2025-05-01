import instance from "./http";

// 機場相關 API
export const ApiAirport = {
  //分頁搜尋測試
  testPage: (page, size) => instance.get(`/airports/Test/${page}/${size}`),
  // 獲取所有不重複的城市名稱
  DistinctCities: () => instance.get("/airports/distinct-cities"),
  // 獲取所有機場資料
  getAllAirports: () => instance.get("/airports/getAll"),
  // 獲取所有不重複的國家/地區名稱

  DistinctCountryRegions: () =>
    instance.get("/airports/distinct-country-regions"),

  // 獲取所有不重複的機場名稱
  DistinctAirportName: () => instance.get("/airports/distinct-AirportName"),
  // 新增機場
  addAirport: (airport) => instance.post("/airports", airport),
  // 刪除機場
  deleteAirport: (id) => instance.delete(`/airports/${id}`),
  // 更新機場
  updateAirport: (id, airport) => instance.put(`/airports/${id}`, airport),
  //搜尋機場

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
};
//航班相關 API
export const ApiFlight = {
  // 根據ID查詢航班
  getFlightById: (id) => instance.get(`/flight/${id}`),
  // 獲取所有航班
  getAllFlights: () => instance.get("/flight/getAll"),

  // 更新航班
  updateFlight: (id, flightDetails) =>
    instance.put(`/flight/${id}`, flightDetails),

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

//會員相關API

export const ApiMember = {
  getMember: (id) => instance.get(`/member/GetMember/${id}`),
  getAllMember: () => instance.get(`/member/GetAllMember`),
  getAllMemberByFullname: (name) =>
    instance.get(`/member/GetAllMemberByFullname/${name}`),

  updateMember: (memberUpdate) =>
    instance.put(`/member/UpdateMember`, memberUpdate),
  insertMember: (memberInsert) =>
    instance
      .post(`/member/InsertMember`, memberInsert)
      .then((response) => {
        console.log(response.data);
        //如果沒回傳，代表username存在
        if (!response.data || response.data.length === 0) {
          console.log("username存在");
          return null;
        } else {
          console.log("API 請求成功:", response);
          return response.data; // 返回 API 響應資料
        }
      })
      .catch((error) => {
        console.error("API 請求錯誤:", error);
        throw error; // 抛出錯誤以便外層處理
      }),
  insertMemberDefaultAdmin: (memberInsert) =>
    instance
      .post(`/member/InsertMemberDefaultAdmin`, memberInsert)
      .then((response) => {
        console.log(response.data);
        //如果沒回傳，代表username存在
        if (!response.data || response.data.length === 0) {
          console.log("username存在");
          return null;
        } else {
          console.log("API 請求成功:", response);
          return response.data; // 返回 API 響應資料
        }
      })
      .catch((error) => {
        console.error("API 請求錯誤:", error);
        throw error; // 抛出錯誤以便外層處理
      }),

  deleteMemberById: (id) => instance.delete(`/member/DeleteMember/${id}`),
  increaseMiles: (id, plus) =>
    instance.put(`/member/IncreaseMiles/${id}/${plus}`),
  decreaseMiles: (id, minus) =>
    instance.put(`/member/DecreaseMiles/${id}/${minus}`),
  getMemberByUserName: (username) =>
    instance.get(`/member/GetMemberByUsername/${username}`),
  getMemberByEmail: (email) =>
    instance.get(`/member/GetMemberByEmail/${email}`),

  login: (loginInfo) => instance.post(`/auth/login`, loginInfo),

  pictureUpload: (file) =>
    instance.post(`/upload`, file, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    }),
  //取得所有的Admin
  getAllAdminList: () => instance.get(`/member/GetAllAdminList`),

  updateMemberAuthorityDetail: (info) =>
    instance.put(`/member/UpdateMemberAuthorityDetail`, info),
  updateMemberAuthority: (info) =>
    instance.put(`/member/UpdateMemberAuthority`, info),
};

//座位相關API
export const ApiSeats = {
  getAllSeats: () => instance.get("/seat/getAll"),
  getSeatsByFlightId: (flightId) => instance.get(`/seat/flights/${flightId}`),

  getSeatsByTicketId: (ticketId) => instance.get(`/seat/tickets/${ticketId}`),
  releaseSeat: (seatId) => instance.post(`/seat/release/${seatId}`),
};

// 票務相關 API
export const ApiTicket = {
  // 查詢所有票務
  getAllTickets: () => instance.get("/Ticket/getAll"),
  // 創建新票務
  createTicket: (data) => instance.post("/Ticket", data),
  // 更新票務
  updateTicket: (id, ticketDetails) =>
    instance.put(`/Ticket/${id}`, ticketDetails),


  // 刪除票務
  deleteTicket: (id) => instance.delete(`/Ticket/${id}`),

  getCheckMacValue: (params) => instance.post("/Ticket/calculate-mac", params),
  getTicketsByCustomerId: (customerId) =>
    instance.get(`/Ticket/member/${customerId}`),

};

// 管理員相關API
export const ApiAdmin = {
  getAdmin: (id) => instance.get(`/admin/GetAdmin/${id}`),

  insertAdmin: (adminInsert) =>
    instance
      .post(`/admin/InsertAdmin`, adminInsert)
      .then((response) => {
        console.log(response.data);

        // 如果沒回傳，代表username存在
        if (!response.data || response.data.length === 0) {
          console.log("username存在");
          return null;
        } else {
          console.log("API 請求成功:", response);
          return response.data; // 返回 API 響應資料
        }
      })
      .catch((error) => {
        console.error("API 請求錯誤:", error);
        throw error; // 抛出錯誤以便外層處理
      }),

  // login: (admin) =>
  //   instance.post(`/admin/GetPassword`, admin)
};


//里程兌換-商品管理相關API
export const ApiProducts = {
  //用id查單筆
  searchById: (id) => instance.get(`/products/${id}`),
  // 根據商品名稱查詢商品
  searchProByName: (name) => instance.get(`/products/name`, {
    params: { name },
  }),
  // 查詢低庫存商品
  searchlowstock: (threshold) =>
    instance.get(`/products/lowstock`, {
      params: { threshold },
    }),
  //查全部商品
  getAllProduct: () => instance.get(`/products/all`),

  // 新增商品
  addProduct: (product) => instance.post("/products", product),

  // 刪除商品
  deleteProduct: (id) => instance.delete(`/products/${id}`),
  // 更新商品
  updateProduct: (id, products) => instance.put(`/products/${id}`, products),
  // 上傳圖片並新增商品
  uploadProductImage: (id, formData) => {
    const url = id ? `/products/${id}/uploadImage` : '/products/0/uploadImage'; // 根據是否有 id 決定上傳的 URL
    return instance.post(url, formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    });
  }
};


export const ApiRedeem = {

  // 查詢全部訂單 (GET /redeem/all)
  getAllRedeem: () => instance.get('/redeem/'),

  // 根據 Id 查單筆訂單 (GET /redeem/{redeemId})
  getRedeemById: (redeemId) => instance.get(`/redeem/${redeemId}`),

  // 新增一筆訂單 (POST /redeem)
  addRedeem: (createRedeemRequest) => instance.post('/redeem', createRedeemRequest),

  // 更新訂單 (PUT /redeem/{redeemId})
  updateRedeem: (redeemId, redeemData) => instance.put(`/redeem/${redeemId}`, redeemData),

  // 更新訂單狀態 (PUT /redeem/{redeemId}/status)
  updateRedeemStatus: (redeemId, newStatus) =>
    instance.put(`/redeem/${redeemId}/status`, null, {
      params: {
        newStatus: newStatus,
      },
    }),

  // 軟刪除訂單 (DELETE /redeem/{redeemId})
  softDeleteRedeem: (redeemId) => instance.delete(`/redeem/${redeemId}`),

  // 根據Redeem狀態查詢訂單 (GET /redeem/redeemStatus?redeemStatus={狀態值})
  findByRedeemStatus: (redeemStatus) =>
    instance.get('/redeem/redeemStatus', {
      params: {
        redeemStatus: redeemStatus,
      },
    }),

  // 根據訂單成立時間查詢訂單列表 (GET /redeem/redeemDate?redeemDate={日期值})
  findByTime: (redeemDate) =>
    instance.get('/redeem/redeemDate', {
      params: {
        redeemDate: redeemDate,
      },
    }),

  // 根據會員ID查詢訂單列表 (GET /redeem/memberId?memberId={會員ID})
  findByMemberId: (memberId) =>
    instance.get(`/redeem/memberId`, {
      params: {
        memberId: memberId,
      },
    }),

  // 取消訂單 (PUT /redeem/cancelRedeem/{redeemId})
  cancelRedeem: (redeemId) => instance.put(`/redeem/cancelRedeem/${redeemId}`, {}),
};

export const ApiRedeemItem = {
  getItemsByRedeemId: (redeemId) => instance.get(`/redeemItem/${redeemId}`)
}



export const ApiEmail = {
  //要求更新密碼
  requestReset: (email) =>
    instance.post(
      `/request-reset`,
      { email },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    ),

  //token是否有效
  tokenIsValid: (token) =>
    instance.get(`/reset-password`, {
      params: { token },
    }),

  //重設密碼
  resetPassword: (token, newPassword) =>
    instance.post(`/reset-password`, null, {
      params: {
        token,
        newPassword,
      },
    }),
};

