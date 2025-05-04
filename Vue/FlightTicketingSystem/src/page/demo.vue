<template>
    <v-container class="pa-4">
      <v-row>
        <v-col cols="12">
          <h1 class="text-h5">Hello Vue + Vuetify</h1>
          <v-btn color="primary" @click="handleClick">點我</v-btn>
          <p v-if="showMessage" class="mt-4">你點了按鈕！</p>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  
  const showMessage = ref(false)
  
  const handleClick = () => {
    showMessage.value = true
  }

  // import { ref,onMounted} from "vue";
// import { ApiProducts } from '@/utils/API';


// 搜尋用關鍵字
const search = ref('')

// 商品資料
const products = [
  // 咖啡豆類 1~9
  { id: 1, name: '咖啡豆 A', needmiles: 2500, image: 'https://picsum.photos/200?random=1' },
  { id: 2, name: '咖啡豆 B', needmiles: 2700, image: 'https://picsum.photos/200?random=2' },
  { id: 3, name: '咖啡豆 C', needmiles: 3200, image: 'https://picsum.photos/200?random=3' },
  { id: 4, name: '咖啡豆 D', needmiles: 2800, image: 'https://picsum.photos/200?random=4' },
  { id: 5, name: '咖啡豆 E', needmiles: 3100, image: 'https://picsum.photos/200?random=5' },
  { id: 6, name: '咖啡豆 F', needmiles: 3300, image: 'https://picsum.photos/200?random=6' },
  { id: 7, name: '咖啡豆 G', needmiles: 2900, image: 'https://picsum.photos/200?random=7' },
  { id: 8, name: '咖啡豆 H', needmiles: 2750, image: 'https://picsum.photos/200?random=8' },
  { id: 9, name: '咖啡豆 I', needmiles: 2600, image: 'https://picsum.photos/200?random=9' },

  // 酒類 10~18
  { id: 10, name: '紅酒 A', needmiles: 8800, image: 'https://picsum.photos/200?random=10' },
  { id: 11, name: '紅酒 B', needmiles: 11000, image: 'https://picsum.photos/200?random=11' },
  { id: 12, name: '威士忌 A', needmiles: 25000, image: 'https://picsum.photos/200?random=12' },
  { id: 13, name: '威士忌 B', needmiles: 28000, image: 'https://picsum.photos/200?random=13' },
  { id: 14, name: '清酒 A', needmiles: 7200, image: 'https://picsum.photos/200?random=14' },
  { id: 15, name: '清酒 B', needmiles: 9600, image: 'https://picsum.photos/200?random=15' },
  { id: 16, name: '啤酒 A', needmiles: 1800, image: 'https://picsum.photos/200?random=16' },
  { id: 17, name: '啤酒 B', needmiles: 2100, image: 'https://picsum.photos/200?random=17' },
  { id: 18, name: '水果酒 A', needmiles: 5200, image: 'https://picsum.photos/200?random=18' },

  // 禮盒 19~27
  { id: 19, name: '茶葉禮盒 A', needmiles: 7000, image: 'https://picsum.photos/200?random=19' },
  { id: 20, name: '茶葉禮盒 B', needmiles: 8300, image: 'https://picsum.photos/200?random=20' },
  { id: 21, name: '咖啡禮盒 A', needmiles: 9900, image: 'https://picsum.photos/200?random=21' },
  { id: 22, name: '餅乾禮盒 A', needmiles: 4200, image: 'https://picsum.photos/200?random=22' },
  { id: 23, name: '餅乾禮盒 B', needmiles: 4600, image: 'https://picsum.photos/200?random=23' },
  { id: 24, name: '酒類禮盒 A', needmiles: 30000, image: 'https://picsum.photos/200?random=24' },
  { id: 25, name: '綜合禮盒 A', needmiles: 28000, image: 'https://picsum.photos/200?random=25' },
  { id: 26, name: '綜合禮盒 B', needmiles: 25000, image: 'https://picsum.photos/200?random=26' },
  { id: 27, name: '禮盒組合 C', needmiles: 27000, image: 'https://picsum.photos/200?random=27' },

  // 雜誌類 28~36
  { id: 28, name: '旅遊雜誌 A', needmiles: 1800, image: 'https://picsum.photos/200?random=28' },
  { id: 29, name: '旅遊雜誌 B', needmiles: 1900, image: 'https://picsum.photos/200?random=29' },
  { id: 30, name: '藝術雜誌 A', needmiles: 2200, image: 'https://picsum.photos/200?random=30' },
  { id: 31, name: '藝術雜誌 B', needmiles: 2500, image: 'https://picsum.photos/200?random=31' },
  { id: 32, name: '時尚雜誌 A', needmiles: 2100, image: 'https://picsum.photos/200?random=32' },
  { id: 33, name: '時尚雜誌 B', needmiles: 2300, image: 'https://picsum.photos/200?random=33' },
  { id: 34, name: '商業雜誌 A', needmiles: 3000, image: 'https://picsum.photos/200?random=34' },
  { id: 35, name: '科技雜誌 A', needmiles: 2800, image: 'https://picsum.photos/200?random=35' },
  { id: 36, name: '心理雜誌 A', needmiles: 2600, image: 'https://picsum.photos/200?random=36' }
]

// 依據搜尋欄位過濾商品
const filteredProducts = computed(() =>
    products.filter(
        (p) =>
            p.name.toLowerCase().includes(search.value.toLowerCase())
    )
);
// 加入購物車
const addToCart = (product) => {
    console.log("加入購物車",product.name);
    
}



  </script>
  
  <style scoped>
  </style>
  