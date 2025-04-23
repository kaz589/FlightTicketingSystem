<template>
  <div class="header-section">
    <div
      style="display: flex; justify-content: space-between; align-items: center"
    >
      <a href="htmlTemplate.html" id="logo" style="display: inline-block">
        <h1 class="text-left mb-4" style="display: flex; align-items: center">
          <img
            src="@/assets/Easytrip_logo_adjust-removebg-preview.png"
            alt="My Icon"
            width="50"
            height="50"
            style="margin-right: 10px"
          />
          Easytrip
        </h1>
      </a>
      <a href="htmlTemplate.html" id="login" style="display: inline-block">
        <i class="fa-solid fa-earth-asia" id="region"></i>
        <i class="fa-solid fa-circle-user" id="user">
          <span id="login-text">Log in</span></i
        >
      </a>
    </div>

    <ul class="nav nav-pills" style="background-color: #619da5">
      <li class="nav-item">
        <a class="nav-link active nav-button" aria-current="page" href="#"
          >Flights</a
        >
      </li>
      <li class="nav-item">
        <a class="nav-link nav-button" href="#">Travel</a>
      </li>
    </ul>

    <div class="row gx-1" id="search-bar">
      <div class="col d-flex align-items-center position-relative">
        <div class="form-floating">
          <input
            type="text"
            class="form-control left-col"
            id="originName"
            placeholder="Enter departure city"
            autocomplete="off"
          />
          <label for="originName">From</label>
          <ul
            id="originSuggestions"
            class="list-group position-absolute w-100"
          ></ul>
        </div>

        <button
          type="button"
          class="btn btn-light d-flex align-items-center justify-content-center position-absolute"
          id="switch"
        >
          <i class="fa-solid fa-arrow-right-arrow-left"></i>
        </button>
      </div>

      <div class="col">
        <div class="form-floating">
          <input
            type="text"
            class="form-control mid-col ps-4"
            id="destinationName"
            placeholder="Enter destination city"
            autocomplete="off"
          />
          <label for="destinationName" class="ps-4" id="arrival-label"
            >To</label
          >
          <ul
            id="destinationSuggestions"
            class="list-group position-absolute w-100"
          ></ul>
        </div>
      </div>

      <div class="col">
        <div class="form-floating">
          <input
            type="text"
            class="form-control mid-col departure-date"
            id="departureTime"
            placeholder="Select departure time"
          />
          <label for="departureTime">Departure</label>
        </div>
      </div>

      <div class="col">
        <div class="form-floating">
          <input
            type="text"
            class="form-control mid-col arrival-date"
            id="arrivalTime"
            placeholder="Select arrival time"
          />
          <label for="arrivalTime">Arrival</label>
        </div>
      </div>

      <div class="col">
        <div class="form-floating dropdown w-100">
          <input
            type="text"
            class="form-control right-col"
            id="travellers"
            placeholder="Add travellers"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            readonly
          />
          <label for="travellers">Travellers</label>
          <!-- 下拉菜单 -->
          <div class="dropdown-menu" aria-labelledby="travellers">
            <div class="mb-3">
              <label for="cabinClass" class="form-label">Cabin class</label>
              <select class="form-select" id="cabinClass">
                <option value="Economy">Economy</option>
                <option value="Business">Business</option>
                <option value="First">First Class</option>
              </select>
            </div>
            <!-- Adults 数量选择 -->
            <div class="mb-3">
              <label for="adults" class="form-label">Adults (Aged 18+)</label>
              <div class="input-group">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="adultsMinus"
                >
                  -
                </button>
                <input
                  type="text"
                  class="form-control text-center"
                  id="adults"
                  value="1"
                  readonly
                />
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="adultsPlus"
                >
                  +
                </button>
              </div>
            </div>
            <!-- Kids 数量选择 -->
            <div class="mb-3">
              <label for="kids" class="form-label">Kids (Aged 0 to 17)</label>
              <div class="input-group">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="kidsMinus"
                >
                  -
                </button>
                <input
                  type="text"
                  class="form-control text-center"
                  id="kids"
                  value="0"
                  readonly
                />
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  id="kidsPlus"
                >
                  +
                </button>
              </div>
            </div>
            <p class="text-muted small">
              Your age at time of travel must be valid for the age category
              booked. Airlines have restrictions on under 18s travelling alone.
            </p>
            <p class="text-muted small">
              Age limits and policies for travelling with children may vary so
              please check with the airline before booking.
            </p>
            <button
              type="button"
              class="btn btn-primary w-100"
              id="applyButton"
            >
              Apply
            </button>
          </div>
        </div>
      </div>

      <div class="col">
        <div class="search">
          <button type="button" class="btn btn-primary" id="search-btn">
            Search
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- Write your codes here -->

  <v-container>
    <v-row align="start" justify="center">
      <v-col
        v-for="flight in flights"
        :key="flight.id"
        align-self="center"
        cols="9"
        md="9"
      >
        <v-card>
          <v-card-title> </v-card-title>
          <v-card-text>
            <div class="flight-info">
              <div class="departure">
                <v-subheader>{{ flight.departureCity }}</v-subheader>
                <br />
                <v-text>{{ flight.departureTime }}</v-text>
              </div>
              <div class="line"></div>
              <div class="arrival">
                <v-subheader>{{ flight.arrivalCity }}</v-subheader>
                <br />
                <v-text>{{ flight.arrivalTime }}</v-text>
              </div>
            </div>
          </v-card-text>
          <v-card-actions style="display: flex; justify-content: flex-end">
            <v-btn
              color="#2196F3"
              text="選擇座位"
              @click="reveal = true"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import flatpickr from "flatpickr";
const flights = ref([
  {
    id: 1,
    departureCity: '紐約',
    departureTime: '4/11 10:00 AM',
    arrivalCity: '倫敦',
    arrivalTime: '4/11 8:00 PM',
    link: '#',
  },
  {
    id: 2,
    departureCity: '洛杉磯',
    departureTime: '4/12 11:00 AM',
    arrivalCity: '東京',
    arrivalTime: '4/13 3:00 PM',
    link: '#',
  },
  {
    id: 3,
    departureCity: '巴黎',
    departureTime: '4/12 9:00 AM',
    arrivalCity: '柏林',
    arrivalTime: '4/12 11:30 AM',
    link: '#',
  },
  {
    id: 4,
    departureCity: '香港',
    departureTime: '4/13 2:00 PM',
    arrivalCity: '悉尼',
    arrivalTime: '4/14 10:00 AM',
    link: '#',
  },
  {
    id: 5,
    departureCity: '上海',
    departureTime: '4/14 6:00 PM',
    arrivalCity: '舊金山',
    arrivalTime: '4/14 2:00 PM',
    link: '#',
  },
]);
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=DM+Serif+Text:ital@0;1&display=swap");

.dm-serif-text-regular {
  font-family: "DM Serif Text", serif;
  font-weight: 400;
  font-style: normal;
}

.dm-serif-text-regular-italic {
  font-family: "DM Serif Text", serif;
  font-weight: 400;
  font-style: italic;
}

html {
  height: 100%;
  background-color:#619da5; /* 淺藍色背景 */
}

body {
  width: 100%;
  height: 100%;
  font-family: "Roboto", sans-serif;
  background-color: white;
}

.header-section {
  padding: 15px 5%;
  overflow: visible;
  box-sizing: border-box;
  background-color: #619da5; /* 淺藍色背景 */
}

#logo {
  display: flex;
  align-items: center;
  cursor: pointer;
  text-decoration: none;
  color: inherit;
}

#logo:hover {
  opacity: 0.8;
}

#region {
  margin-right: 1em;
}

#login {
  color: #ffffff;
  text-decoration: none;
}

#login-text {
  font-size: 20px;
  font-weight: 400;
  font-family: "Arial", sans-serif;
  color: #ffffff;
  text-decoration: none;
  margin-left: 0.3em;
}

.nav-button {
  display: inline-block;
  width: 6.5em;
  height: 2.25em;
  justify-content: center;
  text-align: center;
  border-radius: 8em !important;
  margin-inline-end: 0.5rem;
  background-color:#619da5;
  color: #ffffff;
  border: 1px solid #d3d3d3;
  font-weight: bold;
  transition: background-color 0.3s ease, color 0.3s ease,
    border-color 0.3s ease;
}

.nav-button.active {
  background-color: #154679 !important;
  color: white !important;
  border-color: #154679 !important;
}

.nav-button:hover {
  background-color: #154679;
  color: #ffffff;
}

.nav-button:focus {
  background-color: #154679;
  color: #ffffff;
  border-color: #154679;
  outline: none;
  box-shadow: 0 0 0 2px rgba(21, 71, 121, 0.5);
}

.nav-button:not(:focus) {
  background-color: #05203c !important;
  color: #ffffff !important;
  border-color: #d3d3d3 !important;
}

h1 {
  font-family: "DM Serif Text", serif;
  color: #ffffff;
}

#search-bar {
  height: 25vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
}

.list-group {
  position: absolute;
  top: calc(100% + 2px);
  left: 0;
  z-index: 1050;
  background: white;
  border: 1px solid #ddd;
  max-height: 250px;
  width: 200%;
  overflow-y: auto;
  display: none;
  list-style: none;
  padding: 0;
  margin: 0;
}

.list-group-item {
  cursor: pointer;
  padding: 8px;
  transition: background-color 0.2s ease-in-out;
}

.list-group-item:hover,
.list-group-item.active {
  color: #000000;
  border-color: #ffffff;
  background-color: #eff3f8;
}

.flatpickr-calendar {
  background-color: white;
  color: #05203c;
  font-family: Helvetica, sans-serif;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.flatpickr-day {
  background-color: white;
  color: #05203c;
  font-family: Helvetica, sans-serif;
  border-radius: 50%;
  border: none;
  transition: background-color 0.3s ease;
}

.departure-day,
.arrival-day {
  background-color: #05203c !important;
  color: white !important;
}

.flatpickr-day:hover {
  background-color: #05203c !important;
  color: white !important;
}

.range-day {
  background-color: #1a3a5e !important;
  color: white !important;
}

.flatpickr-current-month,
.flatpickr-weekdays {
  color: #05203c;
}

.flatpickr-prev-month,
.flatpickr-next-month {
  color: #05203c;
}

.flatpickr-prev-month:hover,
.flatpickr-next-month:hover {
  color: #2196f3;
}

#arrival-label {
  background-color: transparent !important;
  z-index: 1;
}

.left-col {
  width: 100%;
  height: 40px;
  border-radius: 4px 0 0 4px !important;
}

.mid-col {
  width: 100%;
  height: 40px;
  border-radius: 0 !important;
}

.right-col {
  width: 100%;
  height: 40px;
  border-radius: 0 4px 4px 0 !important;
}

label {
  font-size: 12px;
  color: #666;
}

.position-relative {
  display: flex;
  justify-content: center;
  align-items: center;
}

#switch {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  border: 3px solid #05203c;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex-shrink: 0;
  right: -1em;
  z-index: 2;
}

.dropdown-menu {
  width: 300px;
  padding: 1rem;
  z-index: 1000;
}

#search-btn {
  margin-right: 0.1rem !important;
  height: 58px;
  padding-inline: 3rem;
  white-space: nowrap;
}

.footer {
  background: #05203c;
  color: white;
  text-align: center;
  padding: 20px;
  font-family: "Roboto", sans-serif;
}

.region-selector {
  background: #1c3556;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.footer-links {
  display: flex;
  justify-content: center;
  gap: 250px;
  flex-wrap: wrap;
  margin-bottom: 30px;
}
.footer-links div {
  display: flex;
  flex-direction: column;
}
.footer-links a {
  color: white;
  text-decoration: none;
  margin: 5px 0;
}
.footer-links a:hover {
  text-decoration: underline;
}
.footer-bottom {
  margin-top: 20px;
  font-size: 14px;
}

.card {
  width: 700px;
  height: 150px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 20px;
}
.flight-info {
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
  align-items: center;
}

.departure,
.arrival {
  text-align: center;
}

.line {
  width: 40%; /* Full width line */
  height: 1px;
  background-color: #ccc; /* Line color */
  margin: 10px 0; /* Adjust spacing as needed */
}
</style>
