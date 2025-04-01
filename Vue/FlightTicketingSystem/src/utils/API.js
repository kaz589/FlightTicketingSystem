import instance from "./http";

// 機場相關 API
const ApiAirport = {
    testPage: (page, size) => instance.get(`/airports/Test/${page}/${size}`),
    DistinctCities: () => instance.get('/airports/distinct-cities'),
    DistinctCountryRegions: () => instance.get('/airports/distinct-country-regions'),
    searchAirports: (keyword, city, countryRegion, page, size) => 
        instance.get(`/airports/search/${keyword}/${city}/${countryRegion}/${page}/${size}`),
    // 新增機場
    addAirport: (airport) => instance.post('/airports', airport),

    // 刪除機場
    deleteAirport: (id) => instance.delete(`/airports/${id}`)
   
};
export default ApiAirport;


