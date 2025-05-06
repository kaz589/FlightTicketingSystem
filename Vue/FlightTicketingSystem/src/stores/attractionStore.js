import { defineStore } from "pinia";
import axios from "axios";

export const useAttractionStore = defineStore("attraction", {
  state: () => ({
    attractions: [],
    loading: false,
  }),

  actions: {
    async fetchAttractions() {
      this.loading = true;
      try {
        const res = await axios.get("http://localhost:8080/attractions", {
          params: {
            page: 0,
            size: 9999,
          },
        });
        this.attractions = res.data.content;
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
