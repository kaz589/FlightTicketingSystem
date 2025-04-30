<template>
  <v-container
    fluid
    class="pa-6"
    style="background-color: #f9f9f9; min-height: 100vh">
    <NavigationTabs v-model:selectedTab="currentTab" />
    <v-slide-y-transition>
      <template v-if="searchBarTabs.includes(currentTab)">
        <SearchBar v-model="searchQuery" @search="handleSearch" />
      </template>
    </v-slide-y-transition>
    <div class="d-flex justify-space-between align-center mb-4">
      <v-btn-toggle v-model="viewMode" borderless>
        <v-tooltip text="卡片模式">
          <template #activator="{ props }">
            <v-btn icon value="Card" v-bind="props">
              <v-icon>mdi-view-grid</v-icon>
            </v-btn>
          </template>
        </v-tooltip>
        <v-tooltip text="表格模式">
          <template #activator="{ props }">
            <v-btn icon value="Table" v-bind="props">
              <v-icon>mdi-table</v-icon>
            </v-btn>
          </template>
        </v-tooltip>
      </v-btn-toggle>
    </div>

    <div class="mb-4 font-weight-bold">
      <v-icon>mdi-table-eye</v-icon> 當前檢視：{{
        viewMode === "Card" ? "卡片模式" : "表格模式"
      }}
    </div>

    <v-window v-model="currentTab" class="mt-6">
      <v-window-item
        v-for="tab in ['cities', 'allCities', 'attractions', 'allAttractions']"
        :key="tab"
        :value="tab">
        <component :is="getViewComponent(tab)" v-bind="getViewProps(tab)" />
      </v-window-item>
    </v-window>

    <!-- Snackbar -->
    <v-snackbar
      v-model="snackbar"
      color="success"
      timeout="3000"
      location="bottom center">
      {{ snackbarMessage }}
    </v-snackbar>
  </v-container>

  <CreateCityModal v-model="modals.createCity" />

  <CreateAttractionModal
    v-model="modals.createAttraction"
    v-bind="modalProps" />

  <EditCityModal
    v-model="modals.editCity"
    :city="selectedCity"
    @updated="handleCityUpdated" />

  <EditAttractionModal
    v-model="modals.editAttraction"
    :cities="cities"
    :attraction="selectedAttraction"
    v-show="!!selectedAttraction"
    @updated="handleAttractionUpdated" />

  <DeleteConfirmDialog
    v-model="deleteDialogVisible"
    :title="`刪除「${selectedItem?.name}」？`"
    message="刪除後無法復原，確定嗎？"
    @confirm="confirmDelete"
    @cancel="deleteDialogVisible = false" />
</template>

<script setup>
import { ref, watch, reactive, computed, onMounted } from "vue";
import axios from "axios";
import NavigationTabs from "@/components/travel/NavigationTabs.vue";
import SearchBar from "@/components/travel/SearchBarAdmin.vue";
import CreateCityModal from "@/components/travel/CreateCityModal.vue";
import EditCityModal from "@/components/travel/EditCityModal.vue";
import CreateAttractionModal from "@/components/travel/CreateAttractionModal.vue";
import EditAttractionModal from "@/components/travel/EditAttractionModal.vue";
import DeleteConfirmDialog from "@/components/travel/DeleteConfirmDialog.vue";
import { useTabView } from "@/composables/useTabView.js";
import { useCityStore } from "@/stores/cityStore";
import { useAttractionStore } from "@/stores/attractionStore";

const cityStore = useCityStore();
const attractionStore = useAttractionStore();
const cities = computed(() => cityStore.cities);
const searchBarTabs = ["cities", "attractions"];
const currentTab = ref("cities");
const searchQuery = ref("");

onMounted(() => {
  cityStore.fetchCities();
});

watch(currentTab, async (tab) => {
  searchQuery.value = "";
  results.value = [];

  if (tab === "allCities") {
    await cityStore.fetchCities();
  } else if (tab === "allAttractions") {
    await attractionStore.fetchAttractions();
  } else if (tab === "addCities") {
    modals.createCity = true;
  } else if (tab === "addAttractions") {
    modals.createAttraction = true;
  }
});

const ViewModes = {
  CARD: "Card",
  TABLE: "Table",
};
const viewMode = ref(ViewModes.CARD);

const cityHeaders = [
  { title: "名稱", key: "name" },
  { title: "國家", key: "country" },
  { title: "經度", key: "longitude" },
  { title: "緯度", key: "latitude" },
  { title: "操作", key: "actions", sortable: false },
];
const attractionHeaders = [
  { title: "名稱", key: "name" },
  { title: "城市", key: "city" },
  { title: "地址", key: "address" },
  { title: "評分", key: "rating" },
  { title: "分類", key: "category" },
  { title: "操作", key: "actions", sortable: false },
];

const results = ref([]);
const selectedCity = ref(null);
const selectedAttraction = ref(null);

const modals = reactive({
  createCity: false,
  createAttraction: false,
  editCity: false,
  editAttraction: false,
});
const modalProps = {
  cities: cities.value,
};

const snackbar = ref(false);
const snackbarMessage = ref("");

function showSnackbar(msg) {
  snackbarMessage.value = msg;
  snackbar.value = true;
}
function handleCityUpdated() {
  showSnackbar("城市已更新");

  if (["cities", "attractions"].includes(currentTab.value)) {
    searchQuery.value = "";
    results.value = [];
  } else {
    if (["allCities"].includes(currentTab.value)) {
      cityStore.fetchCities();
    } else if (["allAttractions"].includes(currentTab.value)) {
      attractionStore.fetchAttractions();
    }
  }
}

function handleAttractionUpdated() {
  showSnackbar("景點已更新");

  if (["cities", "attractions"].includes(currentTab.value)) {
    searchQuery.value = "";
    results.value = [];
  } else {
    if (["allCities"].includes(currentTab.value)) {
      cityStore.fetchCities();
    } else if (["allAttractions"].includes(currentTab.value)) {
      attractionStore.fetchAttractions();
    }
  }
}

function handleAttractionDeleted(item) {
  showSnackbar("景點已刪除");
}

const handleEdit = (item) => {
  if (["cities", "allCities"].includes(currentTab.value)) {
    selectedCity.value = item;
    modals.editCity = true;
  } else if (["attractions", "allAttractions"].includes(currentTab.value)) {
    selectedAttraction.value = item;
    modals.editAttraction = true;
  }
};

const { getViewComponent, getViewProps } = useTabView({
  viewMode,
  currentTab,
  cityStore,
  attractionStore,
  searchResults: results,
  handlers: {
    edit: handleEdit,
    delete: handleDelete,
  },
  headers: {
    city: cityHeaders,
    attraction: attractionHeaders,
  },
});

const apiPaths = {
  cities: "http://localhost:8080/cities/city",
  attractions: "http://localhost:8080/attractions",
};

const handleSearch = async (done) => {
  try {
    const base = apiPaths[currentTab.value];
    const query = searchQuery.value?.trim();

    if (!query) {
      results.value = []; // ← 搜索关键字为空也清空结果
      done();
      return;
    }

    const url = `${base}/${encodeURIComponent(query)}`;
    const response = await axios.get(url);

    if (response.data && response.data.length > 0) {
      if (currentTab.value === "attractions") {
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
    } else {
      // 🧹 如果后端返回空数组，主动清空 results
      results.value = [];
    }
  } catch (error) {
    console.error("Error fetching data:", error);
    results.value = []; // 🧹 错误时也清空，不要残留旧数据
  } finally {
    done();
  }
};

const deleteDialogVisible = ref(false);
const selectedItem = ref(null);

function handleDelete(item) {
  selectedItem.value = item;
  deleteDialogVisible.value = true;
}

async function confirmDelete() {
  if (!selectedItem.value) return;
  try {
    if (["cities", "allCities"].includes(currentTab.value)) {
      await axios.delete(
        `http://localhost:8080/cities/${selectedItem.value.id}`
      );
      showSnackbar("城市已刪除");
      cityStore.fetchCities();
    } else if (["attractions", "allAttractions"].includes(currentTab.value)) {
      await axios.delete(
        `http://localhost:8080/attractions/${selectedItem.value.id}`
      );
      showSnackbar("景點已刪除");
      attractionStore.fetchAttractions();
    }
  } catch (err) {
    console.error("刪除失敗", err);
    showSnackbar("刪除失敗");
  } finally {
    deleteDialogVisible.value = false;
    selectedItem.value = null;
  }
}
</script>

<style scoped></style>
