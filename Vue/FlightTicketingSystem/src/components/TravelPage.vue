<template>
  <div class="min-h-screen bg-gray-50 text-black p-6">
    <TabNavigation :selectedTab="selectedTab" @select-tab="setSelectedTab" />
    <SearchBar v-model="searchQuery" @search="searchTab" />
    <div class="mt-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <CityCard
        v-if="selectedTab === 'cities'"
        v-for="city in results"
        :key="city.id"
        :city="city"
      >
        <template #actions="{ city }">
          <button
            class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded text-sm"
            @click="editCity(city)"
          >
            修改
          </button>
          <button
            class="bg-red-600 hover:bg-red-700 text-white px-4 py-1 rounded text-sm"
            @click="confirmDeleteCity(city.id)"
          >
            刪除
          </button>
        </template>
      </CityCard>
      <AttractionCard
        v-if="selectedTab === 'attractions'"
        v-for="attr in results"
        :key="attr.id"
        :attraction="attr"
      >
        <template #actions="{ attraction }">
          <AttractionCardActions
            :attraction="attraction"
            @edit="(a) => console.log('edit', a)"
            @show-images="(id) => console.log('show-images', id)"
            @delete="(id) => console.log('delete', id)"
          />
        </template>
      </AttractionCard>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from "vue";
import TabNavigation from "./TabNavigation.vue";
import SearchBar from "./SearchBar.vue";
import CityCard from "./CityCard.vue";
import AttractionCard from "./AttractionCard.vue";
import AttractionCardActions from "./AttractionCardActions.vue";
import axios from "axios";
import Swal from "sweetalert2";

// 狀態
const selectedTab = ref("cities");
const searchQuery = ref("");
const results = ref([]);

// inject 依賴
const fetchAllCities = inject("fetchAllCities");
const search = inject("search");

// tab 控制
const setSelectedTab = (tab) => {
  selectedTab.value = tab;
  results.value = [];
};

// 搜尋功能
const apiPaths = {
  cities: "http://localhost:8080/cities/city",
  attractions: "http://localhost:8080/attractions",
};

const searchTab = async () => {
  try {
    const base = apiPaths[selectedTab.value];
    const query = searchQuery.value?.trim();
    if (!query) return;
    const url = `${base}/${encodeURIComponent(query)}`;
    const response = await axios.get(url);

    if (selectedTab.value === "attractions") {
      const attractions = await Promise.all(
        response.data.map(async (attr) => {
          try {
            const photoRes = await axios.get(
              `http://localhost:8080/photos/attraction/${attr.id}`
            );
            const relativeUrl = photoRes.data[0]?.url || "";
            attr.photoUrl = relativeUrl
              ? `http://localhost:8080/${relativeUrl}`
              : "";
          } catch {
            attr.photoUrl = "";
          }
          return attr;
        })
      );
      results.value = attractions;
    } else {
      results.value = response.data;
    }
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

// ✅ 加入編輯城市功能
const editCity = async (city) => {
  const result = await Swal.fire({
    title: "Edit City",
    html: `
      <div class="flex flex-col gap-2">
        <div class="flex items-center gap-2">
          <label for="name" class="w-24 text-sm font-medium">城市</label>
          <input id="name" value="${city.name}" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" />
        </div>
        <div class="flex items-center gap-2">
          <label for="country" class="w-24 text-sm font-medium">國家</label>
          <input id="country" value="${city.country}" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" />
        </div>
        <div class="flex items-center gap-2">
          <label for="latitude" class="w-24 text-sm font-medium">經度</label>
          <input id="latitude" value="${city.latitude}" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" />
        </div>
        <div class="flex items-center gap-2">
          <label for="longitude" class="w-24 text-sm font-medium">緯度</label>
          <input id="longitude" value="${city.longitude}" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" />
        </div>
        <div class="flex items-center gap-0 border border-gray-300 rounded overflow-hidden">
          <span class="px-4 py-2 bg-gray-100 text-sm text-gray-800 whitespace-nowrap">上傳新圖片</span>
          <input type="file" id="cityImage" accept="image/*" class="flex-1 text-sm px-3 py-2 file:hidden focus:outline-none" />
        </div>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: "Update",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton:
        "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
      cancelButton:
        "bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded",
    },
    preConfirm: async () => {
      const name = document.getElementById("name").value.trim();
      const country = document.getElementById("country").value.trim();
      const latitude = parseFloat(document.getElementById("latitude").value);
      const longitude = parseFloat(document.getElementById("longitude").value);
      const imageFile = document.getElementById("cityImage").files[0];

      if (!name || !country || isNaN(latitude) || isNaN(longitude)) {
        Swal.showValidationMessage("請填寫所有欄位");
        return false;
      }

      let imageUrl = city.image;

      try {
        if (imageFile) {
          const formData = new FormData();
          formData.append("image", imageFile);

          const uploadRes = await axios.post(
            "http://localhost:8080/photos/upload",
            formData
          );
          imageUrl = uploadRes.data.url;
        }

        await axios.put(`http://localhost:8080/cities/${city.id}`, {
          name,
          country,
          latitude,
          longitude,
          image: imageUrl,
        });

        await Swal.fire({
          icon: "success",
          title: "City updated!",
          confirmButtonText: "OK",
          customClass: {
            confirmButton:
              "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
          },
        });

        await fetchAllCities?.();
        await search?.();
      } catch (error) {
        console.error("Update failed", error);
        Swal.showValidationMessage("更新失敗，請稍後再試");
      }
    },
  });
};

// ✅ 加入刪除城市功能
const confirmDeleteCity = (id) => {
  Swal.fire({
    title: "您確定嗎?",
    text: "這筆資料將被永久刪除！",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "確認",
    cancelButtonText: "取消",
    customClass: {
      confirmButton: "bg-red-600 text-white px-4 py-2 rounded",
      cancelButton: "bg-gray-500 text-white px-4 py-2 rounded ml-2",
    },
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        await axios.delete(`http://localhost:8080/cities/${id}`);
        Swal.fire("刪除成功!", "這筆資料已被永久刪除", "success");
        await search?.();
      } catch (err) {
        console.error("Delete failed", err);
        Swal.fire("Error", "刪除失敗", "error");
      }
    }
  });
};
</script>
