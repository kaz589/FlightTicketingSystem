import { defineStore } from "pinia";
import { ref, computed } from "vue";
export const useSeatStore = defineStore(
  "seatStore",
  () => {
    const selectseats = ref([]);

    function toggleSeat(seat) {
      // 禁止選擇已被預訂的座位
      if (seat.booked) return;

      seat.selected = !seat.selected;

      if (seat.selected) {
        // 避免重複加入
        if (!selectseats.value.some((s) => s.seatNumber === seat.seatNumber)) {
          selectseats.value.push(seat);
        }
      } else {
        // 取消選取

        const index = selectseats.value.findIndex(
          (s) => s.seatNumber === seat.seatNumber
        );
        if (index !== -1) {
          selectseats.value.splice(index, 1);
        }
      }
    }
    // 結帳完成時清空選位
    function clearSelectedSeats() {
      selectseats.value = [];
    }
    //計算總價
    const totalPrice = computed(() => {
      return selectseats.value.reduce((sum, seat) => sum + seat.price, 0);
    });
    //所有選擇的座位名稱
    const selectedSeatNumbers = computed(() => {
      return selectseats.value
        .map((seat) => `${seat.seatNumber}(${seat.seatclasname})`)
        .join("#");
    });
    //所有選擇的座位ID
    const selectedSeatIds = computed(() => {
      return selectseats.value.map((seat) => seat.seatId); // 確保 seat.id 是數字
    });
    //總里程
    const totalDistance = computed(() => {
      // 累加已選座位的 estimatedDistance
      return selectseats.value.reduce(
        (sum, seat) => sum + (seat.flight.estimatedDistance || 0),
        0
      );
    });
    return {
      selectseats,
      toggleSeat,
      totalPrice,
      selectedSeatNumbers,
      selectedSeatIds,
      totalDistance,
      clearSelectedSeats
    };
  },
  {
    persist: true,
  }
);
