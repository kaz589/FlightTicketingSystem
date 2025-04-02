import instance from "./http";

// 機場相關 API
export const ApiAirport = {
    testPage: (page, size) => instance.get(`/airports/Test/${page}/${size}`),
    DistinctCities: () => instance.get('/airports/distinct-cities'),
    DistinctCountryRegions: () => instance.get('/airports/distinct-country-regions'),
    searchAirports: (keyword, city, countryRegion, page, size, sortBy, sortOrder) =>
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
    addAirport: (airport) => instance.post('/airports', airport),

    // 刪除機場
    deleteAirport: (id) => instance.delete(`/airports/${id}`),
    // 更新機場
    updateAirport: (id,airport) => instance.put(`/airports/${id}`, airport),
   
};



