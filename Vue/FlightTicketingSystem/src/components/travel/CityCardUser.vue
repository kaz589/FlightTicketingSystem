<template>
  <div
    class="rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-transform hover:scale-[1.03] w-[300px] bg-white relative"
  >
    <v-img
      :src="`http://localhost:8080${city.image}`"
      :alt="city.name"
      height="180"
      cover
      class="rounded-t-xl"
    />
    <button
      @click="toggle(city)"
      class="absolute top-2 right-2 w-8 h-8 flex items-center justify-center text-white bg-black/60 hover:bg-black/80 rounded-full transition-transform hover:scale-110"
    >
      <i
        :class="[
          'mdi',
          isFavourite(city.id)
            ? 'mdi-heart text-red-500'
            : 'mdi-heart-outline text-gray-300',
          'text-base cursor-pointer',
        ]"
      ></i>
    </button>

    <div class="p-4 text-center">
      <h2 class="text-lg font-semibold text-gray-800 tracking-tight">
        {{ city.name }}
      </h2>
      <p class="text-xs text-gray-500 mt-1">{{ city.country }}</p>

      <div class="mt-4 flex justify-center gap-3 flex-wrap">
        <button
          @click="handleOpenAttractions(city)"
          class="flex items-center gap-1 px-3 py-1.5 text-sm border border-blue-600 text-blue-600 rounded-md hover:bg-blue-50 transition"
        >
          <i class="mdi mdi-eye text-base"></i>
          查看景點
        </button>
        <button
          @click="travel.setDestination(city.name)"
          class="flex items-center gap-1 px-3 py-1.5 text-sm bg-green-500 text-white rounded-md hover:bg-green-600 transition"
        >
          <i class="mdi mdi-airplane text-base"></i>
          設為目的地
        </button>
      </div>
    </div>
  </div>

  <v-dialog v-model="travel.showAttractionModal" max-width="700">
    <template #default>
      <v-card class="pa-6 elevation-0">
        <v-card-title class="text-h6 font-bold">
          {{ travel.currentCity.name }} 的熱門景點
        </v-card-title>

        <v-card-text>
          <div class="relative">
            <swiper
              :slides-per-view="1"
              :space-between="16"
              :modules="[Pagination, Navigation]"
              pagination
              :navigation="{
                nextEl: '.swiper-custom-next',
                prevEl: '.swiper-custom-prev',
              }"
              loop
              class="rounded-lg"
            >
              <swiper-slide
                v-for="(attraction, index) in travel.currentCity.attractions"
                :key="attraction.id || index"
              >
                <v-card
                  flat
                  class="d-flex flex-column justify-between h-full px-6 py-4"
                >
                  <div>
                    <div class="mb-4">
                      <swiper
                        :modules="[Thumbs]"
                        :slides-per-view="1"
                        :slide-to-clicked-slide="true"
                        class="rounded mb-2"
                        @swiper="
                          (swiper) => {
                            swiperRefs[index] = swiper;
                            const thumb = thumbs[index];
                            if (thumb && swiper.thumbs) {
                              swiper.thumbs.swiper = thumb;
                              swiper.thumbs.update();
                            }
                          }
                        "
                      >
                        <swiper-slide
                          v-for="(photo, j) in attraction.photos"
                          :key="photo.id"
                        >
                          <v-img
                            :src="`http://localhost:8080${photo.url}`"
                            cover
                            class="rounded"
                            height="250"
                          >
                            <template #placeholder>
                              <div
                                class="flex items-center justify-center h-full bg-gray-100"
                              >
                                載入中...
                              </div>
                            </template>
                          </v-img>
                        </swiper-slide>
                      </swiper>

                      <swiper
                        :modules="[Thumbs, FreeMode]"
                        slides-per-view="4"
                        free-mode
                        watch-slides-progress
                        :slide-to-clicked-slide="true"
                        @swiper="(swiper) => (thumbs[index] = swiper)"
                        class="rounded-sm"
                      >
                        <swiper-slide
                          v-for="(photo, j) in attraction.photos"
                          :key="photo.id + '-thumb'"
                        >
                          <v-img
                            :src="`http://localhost:8080${photo.url}`"
                            height="60"
                            class="rounded-sm border border-gray-300"
                            cover
                          />
                        </swiper-slide>
                      </swiper>
                    </div>

                    <h3 class="text-lg font-semibold mb-2">
                      {{ attraction.name }}
                    </h3>
                    <p class="text-sm text-gray-600 mb-1">
                      ⭐ 評價：{{ attraction.rating }}
                    </p>
                    <p class="text-sm text-gray-600 mb-1">
                      📍 地址：{{ attraction.address }}
                    </p>
                    <p class="text-sm text-gray-600 mb-1">
                      🏷️ 分類：{{ attraction.category?.join(", ") }}
                    </p>
                    <p class="text-sm mt-2 text-gray-700">
                      {{ attraction.description }}
                    </p>
                  </div>
                </v-card>
              </swiper-slide>
            </swiper>
            <div
              class="absolute -left-10 top-1/2 -translate-y-1/2 z-50 swiper-custom-prev cursor-pointer"
            >
              <v-icon size="32">mdi-chevron-left</v-icon>
            </div>
            <div
              class="absolute -right-10 top-1/2 -translate-y-1/2 z-9999 swiper-custom-next cursor-pointer"
            >
              <v-icon size="32">mdi-chevron-right</v-icon>
            </div>
          </div>
        </v-card-text>

        <v-card-actions class="justify-end mt-4">
          <v-btn @click="travel.showAttractionModal = false" text color="grey">
            關閉
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import { useTravelStore } from "@/stores/travelStore";
import { useFavouriteStore } from "@/stores/favourtieStore";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Pagination, Thumbs, FreeMode, Navigation } from "swiper/modules";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/thumbs";
import "swiper/css/free-mode";
import "swiper/css/navigation";

defineProps({
  city: Object,
});

const travel = useTravelStore();
const favourite = useFavouriteStore();
const thumbs = ref([]);
const swiperRefs = ref([]);

const toggle = (city) => favourite.toggle(city);
const isFavourite = (id) => favourite.isFavourite(id);

const handleOpenAttractions = async (city) => {
  await travel.openAttractions(city);
  await nextTick();
  const len = travel.currentCity.attractions?.length || 0;
  thumbs.value = Array(len).fill(null);
  swiperRefs.value = Array(len).fill(null);
};

onMounted(() => {
  favourite.loadFavourites();
});
</script>

<style>
.v-overlay__scrim {
  background-color: rgba(33, 33, 33, 0.25) !important;
}
.swiper-pagination-bullet {
  background: rgba(0, 0, 0, 0.5);
  width: 8px;
  height: 8px;
  opacity: 1;
}
.swiper-pagination-bullet-active {
  background: black;
}
</style>
