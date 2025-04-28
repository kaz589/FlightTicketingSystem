<template>
  <div>
    <Pie :data="chartData" :options="chartOptions" />
  </div>
</template>

<script>
import { Pie } from "vue-chartjs";
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement } from "chart.js";

// 註冊 Chart.js 的元件，不然不會顯示
ChartJS.register(Title, Tooltip, Legend, ArcElement);

export default {
  components: {
    Pie,
  },
  data() {
    return {
      chartData: {
        labels: ["普通會員", "銀卡會員", "金卡會員", "鑽石會員"],
        datasets: [
          {
            label: "顏色分佈",
            data: [40, 40, 15, 5], // 這裡是各區塊的數據
            backgroundColor: ["#B0BEC5", "#C0C0C0", "#FFD700", "#00BFFF"],
            hoverOffset: 10,
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
};
</script>

<style scoped>
div {
  width: 400px;
  height: 400px;
}
</style>
