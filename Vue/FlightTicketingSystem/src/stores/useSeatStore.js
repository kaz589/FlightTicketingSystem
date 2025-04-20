import { defineStore } from 'pinia';
import { ref ,computed} from 'vue';
export const useSeatStore = defineStore('seatStore', () => {
    const selectseats = ref([]);
  
    function toggleSeat(seat) {
      seat.booked = !seat.booked;
      console.log(`Toggling seat: ${seat.seatNumber}, booked: ${seat.booked}`);
      if (seat.booked) {
        selectseats.value.push(seat);
      } else {
        const index = selectseats.value.findIndex(
          (s) => s.seatNumber === seat.seatNumber
        );
        if (index !== -1) {
          selectseats.value.splice(index, 1);
        }
      }
    }
    const totalPrice = computed(() => {
        // console.log(selectseats.value.reduce((sum, seat) => sum + seat.price, 0));
        return selectseats.value.reduce((sum, seat) => sum + seat.price, 0);
        console.log('Total price calculated:', price);
      });
    return { selectseats, toggleSeat,totalPrice };
}, {
    persist: true
  });