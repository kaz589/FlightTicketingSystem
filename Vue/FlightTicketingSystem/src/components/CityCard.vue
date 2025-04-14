<template>
  <div
    class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[300px] mx-auto"
  >
    <img
      :src="`http://localhost:8080/${city.image}`"
      :alt="city.name"
      class="w-[260px] h-[160px] object-cover rounded-lg mb-4"
    />
    <div class="text-base font-semibold text-center mb-4">
      <div>{{ city.name }}</div>
      <div>{{ city.country }}</div>
    </div>
    <div class="flex space-x-3 mt-auto">
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
    </div>
  </div>
</template>

<script setup>
import { inject } from "vue";
import Swal from "sweetalert2";
import axios from "axios";

const props = defineProps({
  city: Object,
});

const fetchAllCities = inject("fetchAllCities");
const search = inject("search");

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
</script>

<style scoped></style>
