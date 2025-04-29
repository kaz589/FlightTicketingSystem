<template>
  <div>
    <canvas ref="chartCanvas" class="chart"></canvas>
  </div>
</template>

<script>
import { onMounted, watch, ref } from "vue";
import { Chart } from "chart.js";

export default {
  props: {
    chartData: {
      type: Object,
      required: true,
    },
  },
  setup(props) {
    const chartInstance = ref(null); // 儲存圖表實例
    const chartCanvas = ref(null);

    // 在組件掛載後創建圖表
    onMounted(() => {
      setTimeout(() => {
        const ctx = chartCanvas.value.getContext("2d");
        chartInstance.value = new Chart(ctx, {
          type: "pie",
          data: props.chartData,
          options: {
            responsive: true,
            maintainAspectRatio: false, // 設置為 false 允許縮放
            plugins: {
              legend: {
                position: "top",
              },
            },
          },
        });
      }, 100); // 延遲初始化
    });

    // 監控 chartData 的變化並更新圖表
    watch(
      () => props.chartData,
      (newData) => {
        if (chartInstance.value) {
          chartInstance.value.data = newData; // 更新數據
          chartInstance.value.update(); // 強制更新圖表
        }
      }
    );

    return {
      chartCanvas,
    };
  },
};
</script>
<style scoped>
.chart {
  width: 300px; /* 設置寬度 */
  height: 300px; /* 設置高度 */
}
</style>
