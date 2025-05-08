<template>
  <div
    v-if="travel.showImage"
    class="relative w-[67%] mx-auto rounded-3xl overflow-hidden shadow-lg">
    <img
      src="@/assets/ArcticTern.webp"
      alt="Dreamy travel background"
      class="w-full h-full object-cover" />

    <!-- Text -->
    <div class="absolute inset-0 flex mt-12 justify-start">
      <h1
        class="text-white text-4xl md:text-6xl font-semibold drop-shadow-md px-4 text-left pl-12 font-montserrat">
        You are one step away from<br />
        your dream trip
      </h1>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useTravelStore } from "@/stores/travelStore";

const travel = useTravelStore();
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@500;600&display=swap");

.font-montserrat {
  font-family: "Montserrat", sans-serif;
}
</style>
