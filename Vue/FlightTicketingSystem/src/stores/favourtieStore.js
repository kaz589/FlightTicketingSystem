import { defineStore } from "pinia";
import axios from "axios";

export const useFavouriteStore = defineStore("favourite", {
  state: () => ({
    favouriteMap: new Map(), // cityId → favouriteId
    memberId: 1001, // 這裡應該由 authStore 或登入流程動態給
  }),
  getters: {
    isFavourite: (state) => (cityId) => state.favouriteMap.has(cityId),
    allFavourites: (state) => {
      return Array.from(state.favouriteMap.entries()).map(
        ([cityId, favId]) => ({
          cityId,
          id: favId,
        })
      );
    },
  },
  actions: {
    async loadFavourites() {
      if (!this.memberId) return;
      try {
        const res = await axios.get(
          `http://localhost:8080/favourites/member/${this.memberId}`
        );
        const map = new Map();
        res.data.forEach((fav) => {
          map.set(fav.cityId, fav.id);
        });
        this.favouriteMap = map;
      } catch (err) {
        console.error("載入收藏失敗", err);
      }
    },
    async toggle(city) {
      const cityId = city.id;
      if (!this.memberId) return;

      try {
        if (this.isFavourite(cityId)) {
          const favId = this.favouriteMap.get(cityId);
          await axios.delete(`http://localhost:8080/favourites/${favId}`);
          this.favouriteMap.delete(cityId);
          this.favouriteMap = new Map(this.favouriteMap);
        } else {
          const res = await axios.post(`http://localhost:8080/favourites`, {
            memberId: this.memberId,
            cityId,
          });
          this.favouriteMap.set(cityId, res.data.id);
          this.favouriteMap = new Map(this.favouriteMap);
        }
      } catch (err) {
        console.error("收藏操作失敗", err);
      }
    },
  },
});
