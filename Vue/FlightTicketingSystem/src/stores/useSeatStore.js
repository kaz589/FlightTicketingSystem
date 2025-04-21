import { defineStore } from 'pinia';
import { ref ,computed} from 'vue';
export const useSeatStore = defineStore('seatStore', () => {
    const selectseats = ref([]);
  
    function toggleSeat(seat) {
      // 禁止選擇已被預訂的座位
      if (seat.booked) return;
  
      seat.selected = !seat.selected;
  
      if (seat.selected) {
        // 避免重複加入
        if (!selectseats.value.some(s => s.seatNumber === seat.seatNumber)) {
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
    const totalPrice = computed(() => {
    
        return selectseats.value.reduce((sum, seat) => sum + seat.price, 0);
      
      });

      const selectedSeatNumbers = computed(() => {
        return selectseats.value
          .map(seat => `${seat.seatNumber}(${seat.seatclasname})`)
          .join('#');
      });
      const selectedSeatIds = computed(() => {
        return selectseats.value.map(seat => seat.seatId); // 確保 seat.id 是數字
      });
    return { selectseats, toggleSeat,totalPrice,selectedSeatNumbers,selectedSeatIds };
}, {
    persist: true
  });