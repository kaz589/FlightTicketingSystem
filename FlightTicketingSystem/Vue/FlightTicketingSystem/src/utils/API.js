import instance from "./http";

// 機場相關 API
export const ApiAirport = {
  testPage: (page, size) => instance.get(`/airports/Test/${page}/${size}`),
  DistinctCities: () => instance.get("/airports/distinct-cities"),
  DistinctCountryRegions: () =>
    instance.get("/airports/distinct-country-regions"),
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

//會員相關API
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
  increaseMiles: (id, plus) => instance.put(`/member/IncreaseMiles/${id}/${plus}`),
  decreaseMiles: (id, minus) => instance.put(`/member/DecreaseMiles/${id}/${minus}`),
};

//管理員相關API
export const ApiAdmin = {
  getAdmin: (id) => instance.get(`/admin/GetAdmin/${id}`),
  insertAdmin: (adminInsert) =>
    instance.post(`/admin/InsertAdmin`, adminInsert).then((response) => {
      console.log("API 請求成功:", response);
      return response.data; // 返回 API 響應資料
    })
      .catch((error) => {
        console.error("API 請求錯誤:", error);
        throw error; // 抛出錯誤以便外層處理
      }),
  login: (admin) =>
    instance.post(`/admin/GetPassword`, admin)

}

//里程兌換-商品管理相關API
export const ApiProducts = {
  //用id查單筆
  searchById: (id) => instance.get(`/products/${id}`),
  // 根據商品名稱查詢商品
  searchProByName: (name) => instance.get(`/products/name`, {
    params: { name }
  }),
  // 查詢低庫存商品
  searchlowstock: (threshold) => instance.get(`/products/lowstock`, {
    params: { threshold }
  }),

  //查全部商品
  getAllProduct: () => instance.get(`/products/all`),

  // 新增商品
  addProduct: (product) => instance.post('/products', product),

  // 刪除商品
  deleteProduct: (id) => instance.delete(`/products/${id}`),
  // 更新商品
  updateProduct: (id, products) => instance.put(`/products/${id}`, products),


}
