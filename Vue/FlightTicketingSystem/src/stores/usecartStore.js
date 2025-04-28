import { defineStore } from "pinia";
import { ref, computed } from "vue";
export const usecartStore = defineStore(
    "cartStore",
    () => {
        const selectcarts = ref([]);
        function removeProduct(productId) {
            console.log(" removeProduct 被呼叫了，productId：", productId);
            const index = selectcarts.value.findIndex((p) => p.id === productId);
            if (index !== -1) {
                selectcarts.value.splice(index, 1);
            }
        }
        function clearcartStore() {
            selectcarts.value = [];
        }
        function addProduct(product) {
            // 禁止選擇已下架的商品
            if (product.unavailable) return;

            const existing = selectcarts.value.find(
                (p) => p.id === product.id
            )

            if (existing) {
                // 同商品數量 +1
                existing.quantity += 1
            } else {
                // 新商品加入購物車，數量設為 1
                selectcarts.value.push({
                    ...product,
                    quantity: 1,
                    selected: true,
                })
            }

            console.log(selectcarts);

        }
        const productTypesCount = computed(() => selectcarts.value.length);
        return {
            selectcarts,
            addProduct,
            removeProduct,
            productTypesCount,
            clearcartStore
        }
    },
    {
        persist: true,
    }
);
