import { defineStore } from "pinia";
import axios from "axios";

export const useCityStore = defineStore("city", {
  state: () => ({
    cities: [],
    loading: false,
  }),
  actions: {
    async fetchCities(page = 0, size = 9) {
      this.loading = true;
      try {
        const res = await axios.get("http://localhost:8080/cities", {
          params: { page, size },
        });

        const cities = res.data.content;

        const enrichedCities = await Promise.all(
          cities.map(async (city) => {
            try {
              const spotRes = await axios.get(
                `http://localhost:8080/attractions/city/${city.id}`
              );
              return {
                ...city,
                spots: spotRes.data,
              };
            } catch (e) {
              console.warn(`景點載入失敗：${city.name}`, e);
              return {
                ...city,
                spots: [],
              };
            }
          })
        );

        this.cities = enrichedCities;
      } catch (err) {
        console.error("載入城市失敗", err);
      } finally {
        this.loading = false;
      }
    },
    async searchByName(name) {
      try {
        const res = await axios.get(
          `http://localhost:8080/cities/city/${name}`
        );
        return Array.isArray(res.data) ? res.data : [res.data];
      } catch (err) {
        console.error("搜尋城市失敗", err);
        return [];
      }
    },
  },
});
