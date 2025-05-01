<template>
  <div>
    <!-- Header -->
    <!-- <Header></Header> -->

    <!-- Main Content -->
    <main class="mt-10">
      <!-- Title -->
      <!-- <h1 class="text-4xl font-extrabold text-center mb-8">易趣輕鬆飛</h1> -->

      <!-- Tabs -->
      <NavigationTabsUser></NavigationTabsUser>

      <!--Image-->
      <HomePageImage></HomePageImage>
      <!-- Footer -->
      <Footer></Footer>
    </main>
  </div>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import NavigationTabsUser from "@/components/travel/NavigationTabsUser.vue";
import HomePageImage from "@/components/HomePageImage.vue";
</script>

<style scoped></style>
