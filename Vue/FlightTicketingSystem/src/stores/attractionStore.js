import { defineStore } from "pinia";
import axios from "axios";

export const useAttractionStore = defineStore("attraction", {
  state: () => ({
    attractions: [],
    loading: false,
    pagination: {
      page: 1,
      size: 9,
      totalPages: 1,
    },
  }),

  actions: {
    async fetchAttractions() {
      this.loading = true;
      try {
        const res = await axios.get("http://localhost:8080/attractions", {
          params: {
            page: this.pagination.page - 1,
            size: this.pagination.size,
          },
        });
        this.attractions = res.data.content;
        this.pagination.totalPages = res.data.totalPages;
      } catch (err) {
        console.error("❌ 載入景點失敗", err);
      } finally {
        this.loading = false;
      }
    },

    async searchByName(name) {
      try {
        const res = await axios.get(
          `http://localhost:8080/attractions/${name}`
        );
        return res.data;
      } catch (err) {
        console.error("❌ 搜尋景點失敗", err);
        return [];
      }
    },
  },
});
