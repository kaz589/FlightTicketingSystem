<template>
  <!-- Search bar -->
  <div class="flex justify-center items-center gap-4 mb-10">
    <div class="flex flex-wrap gap-3 mt-4">
      <button
        v-for="mode in modes"
        :key="mode.value"
        @click="setMode(mode.value)"
        :class="[
          baseBtnClass,
          activeMode === mode.value
            ? 'bg-blue-100 text-blue-800 border-blue-300'
            : 'bg-white text-gray-700 border-gray-300 hover:bg-gray-50 hover:text-blue-800 hover:border-blue-300 hover:scale-[1.02] active:bg-gray-200',
        ]"
      >
        {{ mode.label }}
      </button>
    </div>
  </div>

  <div v-if="activeMode === 'All'" class="mx-auto" style="padding-left: 7em">
    <v-select
      v-model="sortKey"
      :items="sortOptions"
      label="排序方式"
      variant="outlined"
      density="compact"
      class="mb-2 ml-4"
      style="max-width: 200px"
      prepend-inner-icon="mdi-sort"
    />

    <div
      v-if="sortKey === 'distance'"
      class="d-flex align-center text-gray-700 text-sm mb-4 ml-2"
    >
      <v-icon class="mr-1" color="deep-orange">mdi-map-marker</v-icon>
      <div v-if="userLocation">
        目前位置：{{ userLocation.lat.toFixed(5) }},
        {{ userLocation.lng.toFixed(5) }}
      </div>
      <div v-else-if="locationError" class="text-red-500">
        <v-icon class="mr-1" color="error">mdi-alert-circle</v-icon>
        {{ locationError }}
      </div>
      <div v-else>
        <v-icon class="mr-1" color="grey">mdi-timer-sand</v-icon>
        正在取得目前位置...
      </div>
    </div>
    <CityCardGrid :cities="sortedCities" />
  </div>

  <div v-if="activeMode === 'Favourite'">
    <div
      v-if="favouriteCities.length > 0"
      class="mx-auto"
      style="padding-left: 7em"
    >
      <CityCardGrid :cities="favouriteCities" />
    </div>
    <div v-else class="text-center text-gray-500 mt-10">
      暫無收藏的城市，快去探索一下吧！
    </div>
  </div>

  <div v-if="activeMode === 'AI'" class="p-6 max-w-xl mx-auto">
    <h2 class="text-xl font-bold mb-4">智能推薦景點</h2>

    <textarea
      v-model="userPrompt"
      rows="4"
      placeholder="請輸入您的需求"
      class="w-full p-3 border border-gray-300 rounded mb-4 resize-none"
    ></textarea>

    <button
      @click="getRecommendation"
      :disabled="loading || !userPrompt"
      class="bg-green-600 hover:bg-green-700 text-white px-6 py-2 rounded disabled:opacity-50 transition"
    >
      {{ loading ? "正在思考中..." : "智能推薦" }}
    </button>

    <div v-if="recommendation" class="mt-6 space-y-4">
      <h3 class="text-lg font-semibold">AI 推薦：</h3>
      <div
        v-for="(item, index) in recommendation"
        :key="index"
        class="p-4 border border-gray-200 rounded shadow-sm"
      >
        <p class="font-bold">{{ item.name }}</p>
        <p class="text-sm text-gray-600">{{ item.attractions }}</p>
        <div class="flex justify-end mt-4">
          <v-btn
            size="small"
            color="blue"
            variant="outlined"
            @click="bookCity(item.name)"
          >
            <i class="mdi mdi-airplane mr-2"></i> 設為目的地
          </v-btn>
        </div>
      </div>
    </div>
  </div>
  <div v-if="activeMode === 'Calendar'">
    <FullCalendar />
  </div>
</template>

<script setup>
import { ref, watch, computed, onMounted, onUnmounted } from "vue";
import { useCityStore } from "@/stores/cityStore";
import { useTravelStore } from "@/stores/travelStore";
import { useFavouriteStore } from "@/stores/favourtieStore";
import CityCardGrid from "./CityCardGridUser.vue";
import axios from "axios";
import FullCalendar from "@/components/travel/BasicCalendar.vue";

const baseBtnClass =
  "px-4 py-2 min-w-[96px] rounded-lg border font-medium text-sm transition duration-150 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transform cursor-pointer";

const modes = [
  { value: "AI", label: "智能推薦" },
  { value: "Favourite", label: "我的最愛" },
  { value: "All", label: "所有城市" },
  { value: "Calendar", label: "行程計劃" },
];

const cityStore = useCityStore();
const travel = useTravelStore();
const favouriteStore = useFavouriteStore();

const allCities = ref([]);
const activeMode = ref("");

const sortKey = ref("name"); // 預設按名稱排序
const sortOptions = [
  { title: "名稱（A-Z）", value: "name" },
  { title: "名稱（Z-A）", value: "name-desc" },
  { title: "國家（A-Z）", value: "country" },
  { title: "國家（Z-A）", value: "country-desc" },
  { title: "距離最近", value: "distance" },
];

import pinyin from "pinyin";

const getPinyin = (text) => {
  return pinyin(text, { style: pinyin.STYLE_NORMAL }) // 普通拼音
    .flat()
    .join("");
};

const userLocation = ref(null); // ← 這次叫它 userLocation，好統一命名
const locationError = ref("");

const getUserLocation = () => {
  return new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(
      (pos) => {
        userLocation.value = {
          lat: pos.coords.latitude,
          lng: pos.coords.longitude,
        };
        resolve();
      },
      (err) => {
        locationError.value = err.message || "無法取得位置";
        reject(err);
      }
    );
  });
};

const haversine = (lat1, lon1, lat2, lon2) => {
  const R = 6371;
  const dLat = ((lat2 - lat1) * Math.PI) / 180;
  const dLon = ((lon2 - lon1) * Math.PI) / 180;
  const a =
    Math.sin(dLat / 2) ** 2 +
    Math.cos((lat1 * Math.PI) / 180) *
      Math.cos((lat2 * Math.PI) / 180) *
      Math.sin(dLon / 2) ** 2;
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return R * c;
};

const sortedCities = computed(() => {
  const citiesCopy = [...allCities.value];

  if (sortKey.value === "distance") {
    if (!userLocation.value) return citiesCopy;

    const withDistance = citiesCopy.map((city) => {
      const dist = haversine(
        userLocation.value.lat,
        userLocation.value.lng,
        city.latitude,
        city.longitude
      );
      console.log(`${city.name} 距離你：${dist.toFixed(2)} 公里`);
      return { ...city, _distance: dist };
    });

    return withDistance.sort((a, b) => a._distance - b._distance);
  }

  const [key, order] = sortKey.value.includes("-desc")
    ? [sortKey.value.replace("-desc", ""), "desc"]
    : [sortKey.value, "asc"];

  return citiesCopy.sort((a, b) => {
    const aVal = (a[key] || "").toString();
    const bVal = (b[key] || "").toString();
    return order === "asc"
      ? aVal.localeCompare(bVal, "zh-Hant-u-co-pinyin")
      : bVal.localeCompare(aVal, "zh-Hant-u-co-pinyin");
  });
});
watch(sortKey, async (key) => {
  if (key === "distance" && !userLocation.value) {
    try {
      await getUserLocation();
    } catch (err) {
      alert("無法獲取位置：" + err.message);
    }
  }
});

const favouriteCities = computed(() => {
  const cityMap = new Map(cityStore.cities.map((city) => [city.id, city]));
  return favouriteStore.allFavourites
    .map((fav) => cityMap.get(fav.cityId))
    .filter((city) => city);
});

const setMode = async (mode) => {
  travel.hideImage();
  activeMode.value = mode;

  if (mode === "Favourite") {
    await favouriteStore.loadFavourites();
  }
};

const userPrompt = ref("");
const loading = ref(false);
const recommendation = ref([]);

const bookCity = (cityName) => {
  travel.setDestination(cityName);
  travel.selectedTab = "航班";
};

const getRecommendation = async () => {
  loading.value = true;
  recommendation.value = [];

  try {
    const res = await axios.post(
      "https://openrouter.ai/api/v1/chat/completions",
      {
        model: "deepseek/deepseek-chat:free",
        messages: [
          {
            role: "system",
            content:
              '你是一位旅遊推薦助手，請根據使用者的需求推薦 5 個城市，必須使用 JSON 格式回應，例如 [{"name": "台北", "attractions": "台北101，士林夜市，故宮博物院，象山，台北動物園"}]',
          },
          {
            role: "user",
            content: userPrompt.value,
          },
        ],
      },
      {
        headers: {
          Authorization: `Bearer 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`,
          "HTTP-Referer": "http://localhost:5173",
        },
      }
    );

    const text = res.data.choices[0].message.content;
    console.log(text);

    try {
      recommendation.value = JSON.parse(text);
    } catch (e) {
      recommendation.value = [{ name: "回傳格式錯誤", description: text }];
    }
  } catch (err) {
    console.error("推薦失敗：", err);
    recommendation.value = [
      {
        name: "無法取得推薦",
        description: "可能是 API 金鑰錯誤或網路有問題",
      },
    ];
  } finally {
    loading.value = false;
  }
};

onMounted(async () => {
  await cityStore.fetchCities();
  allCities.value = cityStore.cities;
});

onUnmounted(() => {
  activeMode.value = "";
  travel.resetImage();
});
</script>
<style scoped></style>
