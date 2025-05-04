//建立APP
import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);
//router插件
import router from "./router";
app.use(router);

//Pinia插件
import { createPinia } from "pinia";
import persisted from "pinia-plugin-persistedstate";

const pinia = createPinia();
pinia.use(persisted);
app.use(pinia);

//vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignicons.css";

import VueObserveVisibility from "vue-observe-visibility";
app.use(VueObserveVisibility);

const vuetify = createVuetify({
  components,
  directives,
});

app.use(vuetify); // 使用 Vuetify

//mdb-ui-kit

import "mdb-ui-kit/css/mdb.min.css";
import "mdb-ui-kit/js/mdb.min.js";

app.mount("#app");

//chart.js

// chart.js
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
  LinearScale,
  Filler,
  PieController,
} from "chart.js";

// 註冊 Chart.js 所需的控制器
ChartJS.register(
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
  LinearScale,
  Filler,
  PieController
);
