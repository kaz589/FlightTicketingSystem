import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";

export const useTravelStore = defineStore("travel", () => {
  const selectedTab = ref("航班");
  const flightDestination = ref("");
  const showAttractionModal = ref(false);
  const currentCity = ref(null);
  const showImage = ref(true);

  function hideImage() {
    showImage.value = false;
  }

  function resetImage() {
    showImage.value = true;
  }

  function setDestination(cityName) {
    selectedTab.value = "航班";
    flightDestination.value = cityName;
  }

  async function openAttractions(city) {
    currentCity.value = {
      ...city,
      attractions: [],
    };
    showAttractionModal.value = true;

    try {
      const res = await axios.get(
        `http://localhost:8080/attractions/city/${city.id}`
      );
      const attractions = res.data;

      const withPhotos = await Promise.all(
        attractions.map(async (a) => {
          try {
            const photoRes = await axios.get(
              `http://localhost:8080/photos/attraction/${a.id}`
            );

            return {
              ...a,
              photos: photoRes.data || [],
            };
          } catch (err) {
            console.warn(`景點 ${a.name} 照片讀取失敗`, err);
            return { ...a, photos: [] };
          }
        })
      );

      currentCity.value.attractions = withPhotos;
    } catch (err) {
      console.error("拉取景點失敗：", err);
      showAttractionModal.value = false;
    }
  }

  return {
    selectedTab,
    showImage,
    hideImage,
    resetImage,
    flightDestination,
    showAttractionModal,
    currentCity,
    setDestination,
    openAttractions,
  };
});
