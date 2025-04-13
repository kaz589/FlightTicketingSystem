<template>
  <div class="min-h-screen bg-white text-black p-6">
    <h1 class="text-4xl font-bold text-center mb-6">請選擇操作</h1>

    <div class="flex justify-center space-x-6 text-gray-700 mb-4">
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'cities'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="setSelectedTab('cities')">
        查詢城市
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'attractions'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="setSelectedTab('attractions')">
        查詢景點
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'allCities'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="showAllCities()">
        全部城市
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'allAttractions'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="showAllAttractions()">
        全部景點
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'addCities'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="addCity">
        增加城市
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'add'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="addAttraction">
        增加景點
      </div>
    </div>

    <div class="flex justify-center mt-6">
      <div
        class="flex w-full max-w-xl shadow-md rounded-full border border-gray-200 overflow-hidden">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="請輸入關鍵字"
          class="w-full px-6 py-3 focus:outline-none" />
        <button
          class="bg-emerald-400 text-white px-6 py-3 font-semibold hover:bg-emerald-500"
          @click="search">
          Search
        </button>
      </div>
    </div>

    <!-- City Cards -->
    <div
      v-if="selectedTab === 'cities' && results.length"
      class="mt-8 max-w-6xl mx-auto">
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        <div
          v-for="city in results"
          :key="city.id"
          class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[300px] mx-auto">
          <img
            :src="`http://localhost:8080${city.image}`"
            alt="City Image"
            class="w-[260px] h-[160px] object-cover rounded-lg mb-4" />
          <div class="text-base font-semibold text-center mb-4">
            <div>{{ city.name }}</div>
            <div>{{ city.country }}</div>
          </div>
          <div class="flex space-x-3 mt-auto">
            <button
              class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded text-sm"
              @click="editCity(city)">
              修改
            </button>
            <button
              class="bg-red-600 hover:bg-red-700 text-white px-4 py-1 rounded text-sm"
              @click="confirmDeleteCity(city.id)">
              刪除
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Attraction Cards -->
    <div
      v-if="selectedTab === 'attractions' && results.length"
      class="mt-8 max-w-6xl mx-auto">
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        <div
          v-for="attraction in results"
          :key="attraction.id"
          class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[380px] mx-auto">
          <div class="text-base font-semibold mb-2 text-center">
            {{ attraction.name }}
          </div>
          <div v-if="attraction.photoUrl" class="mb-2">
            <img
              :src="attraction.photoUrl"
              alt="Attraction photo"
              class="w-[260px] h-[160px] object-cover rounded-lg" />
          </div>
          <div class="text-center mt-2">
            <div class="flex justify-center items-center space-x-1">
              <span class="text-gray-700 text-sm"
                >{{ attraction.rating }} ⭐</span
              >
            </div>
          </div>
          <div class="text-sm text-gray-600 text-center mb-4">
            {{ attraction.address }}
          </div>
          <div class="flex justify-center space-x-4 mt-auto mb-2">
            <button
              class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 text-sm"
              @click="editAttraction(attraction)">
              編輯
            </button>
            <button
              class="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700 text-sm"
              @click="showAllImages(attraction.id, attraction.name)">
              查看所有圖片
            </button>
            <button
              class="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 text-sm"
              @click="confirmDeleteAttraction(attraction.id, attraction.name)">
              刪除
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import Swal from "sweetalert2";

const searchQuery = ref("");
const results = ref([]);
const selectedTab = ref("cities");
const cities = ref([]);
const apiPaths = {
  cities: "http://localhost:8080/cities/city",
  attractions: "http://localhost:8080/attractions",
};

const setSelectedTab = async (tab) => {
  selectedTab.value = tab;
  results.value = [];
  if (tab === "attractions" || tab === "cities" || tab === "flights") {
    searchQuery.value = "";
  }
};

const fetchAllCities = async () => {
  try {
    const res = await axios.get("http://localhost:8080/cities");
    cities.value = res.data;
  } catch (err) {
    console.error("Failed to fetch cities", err);
  }
};

const search = async () => {
  try {
    const base = apiPaths[selectedTab.value];
    const query = searchQuery.value?.trim();
    if (!query) {
      return;
    }
    const url = query ? `${base}/${encodeURIComponent(query)}` : base;

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
              ? `http://localhost:8080${relativeUrl}`
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

const showAllCities = async (page = 0, size = 9) => {
  setSelectedTab("allCities");
  try {
    const res = await axios.get(`http://localhost:8080/cities`, {
      params: { page, size },
    });

    const cities = res.data.content;
    const totalPages = res.data.totalPages;

    if (!cities || cities.length === 0) {
      Swal.fire("没有城市資料", "", "info");
      return;
    }

    const html = `
      <div class="grid grid-cols-3 gap-4">
        ${cities
          .map(
            (city) => `
          <div class="border rounded-lg p-4 shadow text-center">
            <img src="http://localhost:8080${city.image}" alt="${city.name}"
              class="w-full h-48 object-cover rounded mb-2" />
            <h3 class="text-xl font-semibold mb-1">${city.name}</h3>
            <p class="text-gray-600 text-sm mb-1">國家：${city.country}</p>
            <p class="text-gray-500 text-sm mb-1">緯度：${city.latitude}，經度：${city.longitude}</p>
          </div>
        `
          )
          .join("")}
      </div>

      <div class="flex justify-center mt-6 gap-2">
        <button class="bg-gray-600 text-white px-4 py-2 rounded" ${
          page === 0 ? "disabled" : ""
        } onclick="showAllCities(${page - 1}, ${size})">上一頁</button>
        <button class="bg-blue-600 text-white px-4 py-2 rounded" ${
          page + 1 >= totalPages ? "disabled" : ""
        } onclick="showAllCities(${page + 1}, ${size})">下一頁</button>
      </div>
    `;

    Swal.fire({
      title: "城市列表",
      html,
      width: "80%",
      showConfirmButton: false,
    });
  } catch (err) {
    console.error("載入城市失敗:", err);
    Swal.fire("錯誤", "無法載入城市資料", "error");
  }
};

const showAllAttractions = async (page = 0, size = 9) => {
  setSelectedTab("allAttractions");
  try {
    const res = await axios.get(`http://localhost:8080/attractions`, {
      params: { page, size },
    });
    const attractionsRaw = res.data.content;
    const totalPages = res.data.totalPages;

    // 取得每個景點的第一張照片
    const attractions = await Promise.all(
      attractionsRaw.map(async (attr) => {
        try {
          const photoRes = await axios.get(
            `http://localhost:8080/photos/attraction/${attr.id}`
          );
          const relativeUrl = photoRes.data[0]?.url || "";
          attr.photoUrl = relativeUrl
            ? `http://localhost:8080${relativeUrl}`
            : "";
        } catch {
          attr.photoUrl = "";
        }
        return attr;
      })
    );

    if (!attractions || attractions.length === 0) {
      Swal.fire("没有景點資料", "", "info");
      return;
    }

    // 渲染卡片 HTML
    const html = `
      <div class="grid grid-cols-3 gap-4">
        ${attractions
          .map(
            (attr) => `
            <div class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[380px] mx-auto">
              <div class="text-base font-semibold mb-2 text-center">
                ${attr.name}
              </div>
              ${
                attr.photoUrl
                  ? `<div class="mb-2">
                      <img src="${attr.photoUrl}" alt="Attraction photo"
                        class="w-[260px] h-[160px] object-cover rounded-lg" />
                    </div>`
                  : ""
              }
              <div class="text-center mt-2">
                <div class="flex justify-center items-center space-x-1">
                  <span class="text-gray-700 text-sm">
                    ${attr.rating || ""} ⭐
                  </span>
                </div>
              </div>
              <div class="text-sm text-gray-600 text-center mb-4">
                ${attr.address || ""}
              </div>
              <div class="flex justify-center space-x-4 mt-auto mb-2">
                <button class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 text-sm"
                  onclick="editAttraction(${attr.id})">編輯</button>
                <button class="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700 text-sm"
                  onclick="showAllImages(${attr.id}, '${
              attr.name
            }')">查看所有圖片</button>
                <button class="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 text-sm"
                  onclick="confirmDeleteAttraction(${attr.id}, '${
              attr.name
            }')">刪除</button>
              </div>
            </div>
          `
          )
          .join("")}
      </div>
      <div class="flex justify-center mt-4 gap-2">
        <button class="bg-gray-600 text-white px-3 py-1 rounded" ${
          page === 0 ? "disabled" : ""
        } onclick="showAllAttractions(${page - 1}, ${size})">上一頁</button>
        <button class="bg-blue-600 text-white px-3 py-1 rounded" ${
          page + 1 >= totalPages ? "disabled" : ""
        } onclick="showAllAttractions(${page + 1}, ${size})">下一頁</button>
      </div>
    `;

    Swal.fire({
      title: "景點列表",
      html,
      width: "80%",
      showConfirmButton: false,
    });
  } catch (err) {
    console.error("載入景點失敗:", err);
    Swal.fire("錯誤", "無法載入景點資料", "error");
  }
};

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

        await fetchAllCities();
        await search();
      } catch (error) {
        console.error("Update failed", error);
        Swal.showValidationMessage("更新失敗，請稍後再試");
      }
    },
  });
};

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
        await search();
      } catch (err) {
        console.error("Delete failed", err);
        Swal.fire("Error", "刪除失敗", "error");
      }
    }
  });
};

const addCity = async () => {
  await Swal.fire({
    title: "Add City",
    html: `
      <div class="flex flex-col gap-2">
        <div class="flex items-center gap-2">
          <label for="name" class="w-24 text-sm font-medium">城市</label>
          <input id="name" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="City Name" />
        </div>

        <div class="flex items-center gap-2">
          <label for="country" class="w-24 text-sm font-medium">國家</label>
          <input id="country" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Country" />
        </div>

        <div class="flex items-center gap-2">
          <label for="latitude" class="w-24 text-sm font-medium">經度</label>
          <input id="latitude" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Latitude" />
        </div>

        <div class="flex items-center gap-2">
          <label for="longitude" class="w-24 text-sm font-medium">緯度</label>
          <input id="longitude" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Longitude" />
        </div>

        <div class="flex items-center gap-0 border border-gray-300 rounded overflow-hidden">
          <span class="px-4 py-2 bg-gray-100 text-sm text-gray-800 whitespace-nowrap">上傳圖片</span>
          <input type="file" id="cityImage" accept="image/*" class="flex-1 text-sm px-3 py-2 file:hidden focus:outline-none" />
        </div>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: "Create",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton:
        "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
      cancelButton:
        "bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded",
    },
    preConfirm: async () => {
      const name = document.getElementById("name").value.trim();
      const country = document.getElementById("country").value.trim();
      const latitude = parseFloat(document.getElementById("latitude").value);
      const longitude = parseFloat(document.getElementById("longitude").value);
      const imageFile = document.getElementById("cityImage").files[0];

      if (!name || !country || isNaN(latitude) || isNaN(longitude)) {
        Swal.showValidationMessage("Please fill all fields correctly.");
        return;
      }

      try {
        const cityRes = await axios.post("http://localhost:8080/cities", {
          name,
          country,
          latitude,
          longitude,
        });

        const cityId = cityRes.data.id;

        if (imageFile) {
          const formData = new FormData();
          formData.append("image", imageFile);

          await axios.post(
            `http://localhost:8080/cities/imageUrl/${cityId}`,
            formData
          );
        }

        await Swal.fire({
          icon: "success",
          title: "City added!",
          confirmButtonText: "OK",
          customClass: {
            confirmButton:
              "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
          },
        });

        await fetchAllCities();
        await search();
      } catch (err) {
        console.error("Add city failed", err);
        Swal.showValidationMessage("Failed to add city.");
      }
    },
  });
};

const addAttraction = () => {
  let cityOptions = "";
  cities.value.forEach((city) => {
    cityOptions += `<option value="${city.id}">${city.name}</option>`;
  });

  Swal.fire({
    title: "Add Attraction",
    html: `
     <div class="flex flex-col gap-2">
        <div class="flex items-center gap-2">
          <label for="name" class="w-24 text-sm font-medium">地點</label>
          <input id="name" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Name">
        </div>

        <div class="flex items-center gap-2">
          <label for="cityId" class="w-24 text-sm font-medium">城市</label>
          <select id="cityId" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm">
            ${cityOptions}
          </select>
        </div>

        <div class="flex items-center gap-2">
          <label for="address" class="w-24 text-sm font-medium">地址</label>
          <input id="address" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Address">
        </div>

        <div class="flex items-center gap-2">
          <label for="latitude" class="w-24 text-sm font-medium">經度</label>
          <input id="latitude" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Latitude">
        </div>

        <div class="flex items-center gap-2">
          <label for="longitude" class="w-24 text-sm font-medium">緯度</label>
          <input id="longitude" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Longitude">
        </div>

        <div class="flex items-center gap-2">
          <label for="description" class="w-24 text-sm font-medium">簡介</label>
          <input id="description" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Description">
        </div>

        <div class="flex items-center gap-2">
          <label for="rating" class="w-24 text-sm font-medium">評分</label>
          <input id="rating" type="number" min="1" max="5" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Rating">
        </div>

        <div class="flex items-center gap-2">
          <label for="category" class="w-24 text-sm font-medium">類型</label>
          <input id="category" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Category (comma separated)">
        </div>

        <div class="flex items-center gap-0 border border-gray-300 rounded overflow-hidden">
          <span class="px-4 py-2 bg-gray-100 text-sm text-gray-800 whitespace-nowrap">上傳圖片</span>
          <input type="file" id="attractionImage" multiple accept="image/*" class="flex-1 text-sm px-3 py-2 file:hidden focus:outline-none" />
        </div>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: "Create",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton:
        "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
      cancelButton:
        "bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded",
    },
    preConfirm: async () => {
      const name = document.getElementById("name").value;
      const description = document.getElementById("description").value;
      const address = document.getElementById("address").value;
      const latitude = parseFloat(document.getElementById("latitude").value);
      const longitude = parseFloat(document.getElementById("longitude").value);
      const rating = parseFloat(document.getElementById("rating").value);
      const category = document
        .getElementById("category")
        .value.split(",")
        .map((c) => c.trim());
      const cityId = parseInt(document.getElementById("cityId").value);
      const imageFile = document.getElementById("image").files[0];

      try {
        await axios.post("http://localhost:8080/attractions", {
          name,
          description,
          address,
          latitude,
          longitude,
          rating,
          category,
          cityId,
        });

        const attractionId = attractionRes.data.id;

        if (imageFile) {
          const formData = new FormData();
          formData.append("image", imageFile);

          await axios.post(`http://localhost:8080/uploads/images`, formData);

          await axios.post("http://localhost:8080/photos", {
            attractionId,
            caption: name,
            url: `/uploads/images/${imageFile.name}`,
          });
        }

        await Swal.fire({
          icon: "success",
          title: "Attraction added!",
          confirmButtonText: "OK",
          customClass: {
            confirmButton:
              "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
          },
        });
        await fetchAllCities();
        await search();
      } catch (err) {
        console.error("Add failed", err);
        Swal.showValidationMessage("Failed to add attraction");
      }
    },
  });
};

async function editAttraction(attraction) {
  const { data: cities } = await axios.get("http://localhost:8080/cities");
  const cityOptions = cities
    .map(
      (city) =>
        `<option value="${city.id}" ${
          city.id === attraction.cityId ? "selected" : ""
        }>${city.name}</option>`
    )
    .join("");

  await Swal.fire({
    title: "Edit Attraction",
    html: `
      <div class="flex flex-col gap-2">
        <div class="flex items-center gap-2">
          <label for="name" class="w-24 text-sm font-medium">Name</label>
          <input id="name" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" value="${attraction.name}">
        </div>

        <div class="flex items-center gap-2">
          <label for="description" class="w-24 text-sm font-medium">Description</label>
          <input id="description" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" value="${attraction.description}">
        </div>

        <div class="flex items-center gap-2">
          <label for="address" class="w-24 text-sm font-medium">Address</label>
          <input id="address" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" value="${attraction.address}">
        </div>

        <div class="flex items-center gap-2">
          <label for="latitude" class="w-24 text-sm font-medium">Latitude</label>
          <input id="latitude" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" value="${attraction.latitude}">
        </div>

        <div class="flex items-center gap-2">
          <label for="longitude" class="w-24 text-sm font-medium">Longitude</label>
          <input id="longitude" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" value="${attraction.longitude}">
        </div>

        <div class="flex items-center gap-2">
          <label for="rating" class="w-24 text-sm font-medium">Rating</label>
          <input id="rating" type="number" min="1" max="5" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" value="${attraction.rating}">
        </div>

        <div class="flex items-center gap-2">
          <label for="cityId" class="w-24 text-sm font-medium">City</label>
          <select id="cityId" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm">
            ${cityOptions}
          </select>
        </div>

        <div class="flex items-center gap-0 border border-gray-300 rounded overflow-hidden">
          <span class="px-4 py-2 bg-gray-100 text-sm text-gray-800 whitespace-nowrap">上傳圖片</span>
          <input type="file" id="imageFiles" multiple accept="image/*" class="flex-1 text-sm px-3 py-2 file:hidden focus:outline-none" />
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
      const name = document.getElementById("name").value;
      const description = document.getElementById("description").value;
      const address = document.getElementById("address").value;
      const latitude = parseFloat(document.getElementById("latitude").value);
      const longitude = parseFloat(document.getElementById("longitude").value);
      const rating = parseFloat(document.getElementById("rating").value);
      const cityId = parseInt(document.getElementById("cityId").value);
      const imageFiles = document.getElementById("imageFiles").files;

      if (imageFiles.length > 5) {
        Swal.showValidationMessage("最多上傳5張圖片");
        return;
      }

      try {
        await axios.put(`http://localhost:8080/attractions/${attraction.id}`, {
          name,
          description,
          address,
          latitude,
          longitude,
          rating,
          category: attraction.category,
          cityId,
        });

        for (const file of imageFiles) {
          const formData = new FormData();
          formData.append("image", file);

          const uploadRes = await axios.post(
            "http://localhost:8080/photos/upload",
            formData
          );

          const imageUrl = uploadRes.data.url;

          await axios.post("http://localhost:8080/photos", {
            attractionId: attraction.id,
            url: imageUrl,
            caption: name,
          });
        }

        await Swal.fire({
          title: "Success",
          text: "Attraction updated successfully!",
          icon: "success",
          confirmButtonText: "OK",
          customClass: {
            confirmButton:
              "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
          },
        });

        await search();
      } catch (err) {
        console.error("Update failed", err);
        Swal.showValidationMessage("Failed to update attraction");
      }
    },
  });
}

const showAllImages = async (attractionId, attractionName) => {
  try {
    const res = await axios.get(
      `http://localhost:8080/photos/attraction/${attractionId}`
    );
    let photos = res.data;

    if (!photos || photos.length === 0) {
      Swal.fire("没有圖片", "這個景點目前沒有已上傳的圖片。", "info");
      return;
    }

    let currentIndex = 0;

    const renderCarouselHtml = () => {
      const indicators = photos
        .map(
          (_, index) =>
            `<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${index}" ${
              index === currentIndex ? 'class="active"' : ""
            }></button>`
        )
        .join("");

      const innerSlides = photos
        .map(
          (photo, index) => `
          <div class="carousel-item ${index === currentIndex ? "active" : ""}">
            <img src="http://localhost:8080${
              photo.url
            }" class="d-block w-100 rounded" style="max-height: 400px; object-fit: contain;" />
            <div class="text-center mt-2 text-gray-700 text-sm">${
              photo.caption || ""
            }</div>
          </div>
        `
        )
        .join("");

      return `
        <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" data-bs-interval="false">
          <div class="carousel-indicators">
            ${indicators}
          </div>
          <div class="carousel-inner">
            ${innerSlides}
          </div>
          <button class="carousel-control-prev" type="button" onclick="window.carouselPrev()">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          </button>
          <button class="carousel-control-next" type="button" onclick="window.carouselNext()">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
          </button>
        </div>
      `;
    };

    window.carouselPrev = () => {
      currentIndex = (currentIndex - 1 + photos.length) % photos.length;
      showImageModal();
    };

    window.carouselNext = () => {
      currentIndex = (currentIndex + 1) % photos.length;
      showImageModal();
    };

    const showImageModal = async () => {
      const result = await Swal.fire({
        title: `${attractionName} 的圖片`,
        html: renderCarouselHtml(),
        width: "60%",
        showConfirmButton: true,
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: "編輯",
        denyButtonText: "刪除",
        cancelButtonText: "退出",
        customClass: {
          confirmButton: "text-white px-4 py-2 rounded",
          denyButton: "text-white px-4 py-2 rounded",
          cancelButton: "text-white px-4 py-2 rounded",
        },
        didOpen: () => {
          const el = document.querySelector("#carouselExampleIndicators");
          if (el && window.bootstrap) {
            new window.bootstrap.Carousel(el, {
              interval: 3000,
              keyboard: true,
              ride: "carousel",
            });
            s;
          }
        },
      });

      const photo = photos[currentIndex];

      if (result.isConfirmed) {
        const { value: formValues } = await Swal.fire({
          title: "編輯圖片資訊",
          html: `
            <input type="text" id="newCaption" class="swal2-input" placeholder="新標題" value="${
              photo.caption || ""
            }">`,
          focusConfirm: false,
          confirmButtonText: "儲存",
          showCancelButton: true,
          cancelButtonText: "取消",
          customClass: {
            confirmButton: "text-white px-4 py-2 rounded",
            cancelButton: "text-white px-4 py-2 rounded",
          },
          preConfirm: () => {
            const caption = document.getElementById("newCaption").value.trim();
            return { caption };
          },
        });

        if (formValues) {
          await axios.put(`http://localhost:8080/photos/${photo.id}`, {
            caption: formValues.caption,
          });
          Swal.fire("成功", "圖片資訊已更新", "success");
          showAllImages(attractionId, attractionName);
        }
      }

      if (result.isDenied) {
        const confirmDelete = await Swal.fire({
          title: "確認刪除？",
          text: "此操作將永久刪除此圖片！",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "確認刪除",
          cancelButtonText: "取消",
          customClass: {
            confirmButton: "bg-red-600 text-white px-4 py-2 rounded",
            cancelButton: "bg-gray-600 text-white px-4 py-2 rounded ml-2",
          },
        });

        if (confirmDelete.isConfirmed) {
          await axios.delete(`http://localhost:8080/photos/${photo.id}`);
          Swal.fire("已刪除", "圖片已被刪除。", "success");
          photos.splice(currentIndex, 1);
          if (photos.length === 0) {
            Swal.fire("沒有圖片了", "", "info");
          } else {
            currentIndex = currentIndex % photos.length;
            showImageModal();
          }
        }
      }
    };

    showImageModal();
  } catch (err) {
    console.error("Failed to load photos:", err);
    Swal.fire("加載失敗", "無法獲取景點的圖片。", "error");
  }
};

const confirmDeleteAttraction = (id) => {
  Swal.fire({
    title: "您確定嗎?",
    text: "這筆資料將被永久刪除！",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "確認",
    cancelButtonText: "刪除",
    customClass: {
      confirmButton: "bg-red-600 text-white px-4 py-2 rounded",
      cancelButton: "bg-gray-600 text-white px-4 py-2 rounded ml-2",
    },
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        await axios.delete(`http://localhost:8080/attractions/${id}`);
        await Swal.fire(
          "Deleted!",
          "The attraction has been removed.",
          "success"
        );
        await search();
      } catch (err) {
        console.error("Delete failed", err);
        Swal.fire("Error", "Delete failed", "error");
      }
    }
  });
};

onMounted(fetchAllCities);
</script>

<style scoped>
.aspect-square {
  aspect-ratio: 1 / 1;
}
</style>
