<template>
    <div class="seat-map">
      <div
        v-for="(row, rowIndex) in seats"
        :key="rowIndex"
        class="seat-row"
      >
        <div
          v-for="(seat, seatIndex) in row"
          :key="seatIndex"
          :class="['seat', { occupied: seat.occupied }]"
          @click="toggleSeat(rowIndex, seatIndex)"
        >
          {{ seat.label }}
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        seats: [
          [{ label: 'A1', occupied: false }, { label: 'A2', occupied: true }, { label: 'A3', occupied: false }],
          [{ label: 'B1', occupied: false }, { label: 'B2', occupied: false }, { label: 'B3', occupied: true }],
          // 更多行
        ],
      };
    },
    methods: {
      toggleSeat(rowIndex, seatIndex) {
        const seat = this.seats[rowIndex][seatIndex];
        if (!seat.occupied) {
          seat.occupied = !seat.occupied;
        }
      },
    },
  };
  </script>
  
  <style>
  .seat-map {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  
  .seat-row {
    display: flex;
    gap: 10px;
  }
  
  .seat {
    width: 40px;
    height: 40px;
    background-color: #90caf9;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    border-radius: 5px;
  }
  
  .seat.occupied {
    background-color: #e57373;
    cursor: not-allowed;
  }
  </style>
  