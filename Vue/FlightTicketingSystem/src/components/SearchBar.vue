<template>
  <div class="min-h-screen bg-white text-black p-6">
    <h1 class="text-4xl font-bold text-center mb-6">Where to?</h1>

    <div class="flex justify-center space-x-6 text-gray-700 mb-4">
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'cities'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="setSelectedTab('cities')"
      >
        Cities
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'attractions'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="setSelectedTab('attractions')"
      >
        Attractions
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'addCities'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="addCity"
      >
        Add Cities
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'add'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="openAddModal"
      >
        Add Attraction
      </div>
    </div>

    <div class="flex justify-center mt-6">
      <div
        class="flex w-full max-w-xl shadow-md rounded-full border border-gray-200 overflow-hidden"
      >
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Places to go..."
          class="w-full px-6 py-3 focus:outline-none"
        />
        <button
          class="bg-emerald-400 text-white px-6 py-3 font-semibold hover:bg-emerald-500"
          @click="searchCities"
        >
          Search
        </button>
      </div>
    </div>

    <!-- City Cards -->
    <div
      v-if="selectedTab === 'cities' && results.length"
      class="mt-8 max-w-6xl mx-auto"
    >
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        <div
          v-for="city in results"
          :key="city.id"
          class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[300px] mx-auto"
        >
          <img
            :src="`http://localhost:8080${city.image}`"
            alt="City Image"
            class="w-[260px] h-[160px] object-cover rounded-lg mb-4"
          />
          <div class="text-base font-semibold text-center mb-4">
            {{ city.name }}
          </div>
          <div class="flex space-x-3 mt-auto">
            <button
              class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded text-sm"
              @click="updateCityImage(city)"
            >
              Edit Image
            </button>
            <button
              class="bg-red-600 hover:bg-red-700 text-white px-4 py-1 rounded text-sm"
              @click="confirmDeleteCity(city.id)"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Attraction Cards -->
    <div
      v-if="selectedTab === 'attractions' && results.length"
      class="mt-8 max-w-6xl mx-auto"
    >
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        <div
          v-for="attraction in results"
          :key="attraction.id"
          class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[380px] mx-auto"
        >
          <div class="text-base font-semibold mb-2 text-center">
            {{ attraction.name }}
          </div>
          <div v-if="attraction.photoUrl" class="mb-2">
            <img
              :src="attraction.photoUrl"
              alt="Attraction photo"
              class="w-[260px] h-[160px] object-cover rounded-lg"
            />
          </div>
          <div class="text-center mt-2">
            <div class="flex justify-center items-center space-x-1">
              <span class="text-yellow-500">
                <i
                  class="fas fa-star"
                  v-for="i in Math.floor(attraction.rating)"
                  :key="i"
                ></i>
              </span>
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
              @click="editAttraction(attraction)"
            >
              Edit
            </button>
            <button
              class="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700 text-sm"
              @click="uploadImages(attraction.id, attraction.name)"
            >
              Upload
            </button>
            <button
              class="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 text-sm"
              @click="confirmDeleteAttraction(attraction.id, attraction.name)"
            >
              Delete
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

const openAddModal = () => {
  addAttraction();
};

const fetchAllCities = async () => {
  try {
    const res = await axios.get("http://localhost:8080/cities");
    cities.value = res.data;
  } catch (err) {
    console.error("Failed to fetch cities", err);
  }
};

const searchCities = async () => {
  if (!searchQuery.value) return;

  try {
    const query = encodeURIComponent(searchQuery.value.trim());
    const url = `${apiPaths[selectedTab.value]}/${query}`;
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

const updateCityImage = async (city) => {
  await Swal.fire({
    title: "Upload New City Image",
    html: `
      <div class="flex flex-col gap-2">
        <div class="flex items-center gap-0 border border-gray-300 rounded overflow-hidden">
          <span class="px-4 py-2 bg-gray-100 text-sm text-gray-800 whitespace-nowrap">Choose File</span>
          <input 
            type="file" 
            id="cityImageFile" 
            accept="image/*" 
            class="flex-1 text-sm px-3 py-2 file:hidden focus:outline-none"
          />
        </div>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: "Upload",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton:
        "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
      cancelButton:
        "bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded",
    },
    preConfirm: async () => {
      const file = document.getElementById("cityImageFile").files[0];
      if (!file) {
        Swal.showValidationMessage("Please select an image");
        return;
      }

      const formData = new FormData();
      formData.append("image", file);

      try {
        await axios.post(
          `http://localhost:8080/cities/imageUrl/${city.id}`,
          formData
        );
        await Swal.fire({
          title: "Success",
          text: "Image updated!",
          icon: "success",
          confirmButtonText: "OK",
          customClass: {
            confirmButton:
              "bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded",
          },
        });
        await searchCities();
      } catch (err) {
        console.error("Upload failed", err);
        await Swal.fire({
          title: "Error",
          text: "Image upload failed",
          icon: "error",
          confirmButtonText: "OK",
          customClass: {
            confirmButton:
              "bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded",
          },
        });
      }
    },
  });
};

const confirmDeleteCity = (id) => {
  Swal.fire({
    title: "Are you sure?",
    text: "This city will be permanently deleted.",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Yes, delete it!",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton: "bg-red-600 text-white px-4 py-2 rounded",
      cancelButton: "bg-gray-500 text-white px-4 py-2 rounded ml-2",
    },
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        await axios.delete(`http://localhost:8080/cities/${id}`);
        Swal.fire("Deleted!", "The city has been removed.", "success");
        await searchCities();
      } catch (err) {
        console.error("Delete failed", err);
        Swal.fire("Error", "Delete failed", "error");
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
          <label for="name" class="w-24 text-sm font-medium">Name</label>
          <input id="name" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="City Name" />
        </div>

        <div class="flex items-center gap-2">
          <label for="country" class="w-24 text-sm font-medium">Country</label>
          <input id="country" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Country" />
        </div>

        <div class="flex items-center gap-2">
          <label for="latitude" class="w-24 text-sm font-medium">Latitude</label>
          <input id="latitude" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Latitude" />
        </div>

        <div class="flex items-center gap-2">
          <label for="longitude" class="w-24 text-sm font-medium">Longitude</label>
          <input id="longitude" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Longitude" />
        </div>

        <div class="flex items-center gap-0 border border-gray-300 rounded overflow-hidden">
          <span class="px-4 py-2 bg-gray-100 text-sm text-gray-800 whitespace-nowrap">Choose File</span>
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

        await searchCities();
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
          <label for="name" class="w-24 text-sm font-medium">Name</label>
          <input id="name" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Name">
        </div>

        <div class="flex items-center gap-2">
          <label for="description" class="w-24 text-sm font-medium">Description</label>
          <input id="description" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Description">
        </div>

        <div class="flex items-center gap-2">
          <label for="address" class="w-24 text-sm font-medium">Address</label>
          <input id="address" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Address">
        </div>

        <div class="flex items-center gap-2">
          <label for="latitude" class="w-24 text-sm font-medium">Latitude</label>
          <input id="latitude" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Latitude">
        </div>

        <div class="flex items-center gap-2">
          <label for="longitude" class="w-24 text-sm font-medium">Longitude</label>
          <input id="longitude" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Longitude">
        </div>

        <div class="flex items-center gap-2">
          <label for="rating" class="w-24 text-sm font-medium">Rating</label>
          <input id="rating" type="number" min="1" max="5" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Rating">
        </div>

        <div class="flex items-center gap-2">
          <label for="category" class="w-24 text-sm font-medium">Category</label>
          <input id="category" autocomplete="off" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm" placeholder="Category (comma separated)">
        </div>

        <div class="flex items-center gap-2">
          <label for="cityId" class="w-24 text-sm font-medium">City</label>
          <select id="cityId" class="flex-1 px-3 py-2 border border-gray-300 rounded text-sm">
            ${cityOptions}
          </select>
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
        "bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded",
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
        await searchCities();
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

        await searchCities();

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
      } catch (err) {
        console.error("Update failed", err);
        Swal.showValidationMessage("Failed to update attraction");
      }
    },
  });
}

async function uploadImages(attractionId, attractionName) {
  await Swal.fire({
    title: "Upload Photo",
    html: `
      <div class="flex flex-col gap-2">
        <div class="flex items-center gap-0 border border-gray-300 rounded overflow-hidden">
          <span class="px-4 py-2 bg-gray-100 text-sm text-gray-800 whitespace-nowrap">Choose File</span>
          <input 
            type="file" 
            id="imageFile" 
            accept="image/*" 
            class="flex-1 text-sm px-3 py-2 file:hidden focus:outline-none"
          />
        </div>
      </div>
    `,
    focusConfirm: false,
    showCancelButton: true,
    confirmButtonText: "Upload",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton:
        "bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded",
      cancelButton:
        "bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded",
    },
    preConfirm: async () => {
      const imageFile = document.getElementById("imageFile").files[0];

      if (!imageFile) {
        Swal.showValidationMessage("Please select an image");
        return;
      }

      try {
        const formData = new FormData();
        formData.append("image", imageFile);

        const uploadRes = await axios.post(
          "http://localhost:8080/photos/upload",
          formData
        );

        const imageUrl = uploadRes.data.url;

        await axios.post("http://localhost:8080/photos", {
          attractionId,
          url: imageUrl,
          caption: attractionName,
        });

        Swal.fire({
          icon: "success",
          title: "Photo uploaded!",
          confirmButtonText: "OK",
          customClass: {
            confirmButton:
              "bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded",
          },
        }).then(() => {
          searchCities();
        });
      } catch (error) {
        console.error("Upload failed:", error);
        Swal.showValidationMessage("Upload failed, please try again.");
      }
    },
  });
}

const confirmDeleteAttraction = (id) => {
  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Yes, delete it!",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton: "bg-red-600 text-white px-4 py-2 rounded",
      cancelButton: "bg-gray-600 text-white px-4 py-2 rounded ml-2",
    },
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        await axios.delete(`http://localhost:8080/attractions/${id}`);
        await searchCities();
      } catch (err) {
        console.error("Delete failed", err);
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
