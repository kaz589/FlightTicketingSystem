<script setup>
import { ref, computed, onMounted, watch } from "vue";
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import zhTwLocale from "@fullcalendar/core/locales/zh-tw";
import { useCityStore } from "@/stores/cityStore";
import { useAttractionStore } from "@/stores/attractionStore";
import { useEventStore } from "@/stores/eventStore";

const calendarRef = ref(null);
const dialog = ref(false);
const isEditing = ref(false);
const currentEvent = ref(null);

const startTime = ref("");
const endTime = ref("");
const selectedCityId = ref(null);
const selectedSpot = ref("");

const cityStore = useCityStore();
const attractionStore = useAttractionStore();
const eventStore = useEventStore();

const cities = computed(() => cityStore.cities);

const selectedCityName = computed(() => {
  return cities.value.find((c) => c.id === selectedCityId.value)?.name || "";
});

const filteredSpots = computed(() => {
  const cityName = selectedCityName.value;
  return attractionStore.attractions
    .filter((a) => a.city === cityName)
    .map((a) => a.name);
});

const updateSpots = () => {
  selectedSpot.value = "";
};

const getTitle = () => {
  return `${selectedCityName.value} - ${selectedSpot.value}`;
};

const toDatetimeLocal = (isoString) => {
  const str =
    typeof isoString === "string"
      ? isoString
      : isoString?.toISOString?.() || "";
  return str.slice(0, 16);
};

const refreshCalendar = () => {
  const calendarApi = calendarRef.value?.getApi?.();
  if (calendarApi) {
    calendarApi.removeAllEvents();
    eventStore.events.forEach((e) => calendarApi.addEvent({ ...e }));
  }
};

const openDialog = ({ editing, event = null, start = "", end = "" }) => {
  isEditing.value = editing;
  currentEvent.value = event;
  startTime.value = toDatetimeLocal(start);
  endTime.value = toDatetimeLocal(end);
  if (event) {
    const [cityRaw, spot] = event.title.split(" - ");
    const matchedCity = cities.value.find((c) => c.name === cityRaw);
    selectedCityId.value = matchedCity?.id || null;
    selectedSpot.value = spot || "";
  } else {
    selectedCityId.value = null;
    selectedSpot.value = "";
  }
  dialog.value = true;
};

const saveEvent = () => {
  if (
    !selectedCityId.value ||
    !selectedSpot.value ||
    !startTime.value ||
    !endTime.value
  )
    return;

  const title = getTitle();
  const id =
    isEditing.value && currentEvent.value?.id
      ? String(currentEvent.value.id)
      : String(Date.now());

  const eventData = {
    id,
    title,
    start: startTime.value,
    end: endTime.value,
    color: "#4285F4",
  };

  if (isEditing.value) {
    eventStore.updateEvent(eventData);
  } else {
    eventStore.addEvent(eventData);
  }

  refreshCalendar();
  dialog.value = false;
};

const deleteEvent = () => {
  if (!currentEvent.value) return;
  const id = String(currentEvent.value.id);
  const exists = eventStore.events.some((e) => String(e.id) === id);
  if (!exists) {
    console.warn("🔴 Tried to remove non-existent event:", id);
    return;
  }
  eventStore.removeEvent(id);
  refreshCalendar();
  dialog.value = false;
};

onMounted(() => {
  cityStore.fetchCities?.();
  attractionStore.fetchAttractions?.();
  refreshCalendar();
});

watch(
  () => eventStore.events,
  () => refreshCalendar(),
  { deep: true }
);

const calendarOptions = {
  plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
  headerToolbar: {
    left: "prev,next today",
    center: "title",
    right: "dayGridMonth,timeGridWeek,timeGridDay",
  },
  initialView: "timeGridWeek",
  selectable: true,
  editable: true,
  nowIndicator: true,
  locale: zhTwLocale,
  slotMinTime: "06:00:00",
  slotMaxTime: "22:00:00",
  slotDuration: "00:30:00",
  firstDay: 1,
  select: (info) =>
    openDialog({ editing: false, start: info.startStr, end: info.endStr }),
  eventClick: (info) =>
    openDialog({
      editing: true,
      event: info.event,
      start: info.event.startStr,
      end: info.event.endStr,
    }),
  eventDrop: (info) => {
    const event = info.event;
    eventStore.updateEvent({
      id: String(event.id),
      title: event.title,
      start: event.startStr,
      end: event.endStr,
      color: event.backgroundColor,
    });
  },

  eventResize: (info) => {
    const event = info.event;
    eventStore.updateEvent({
      id: String(event.id),
      title: event.title,
      start: event.startStr,
      end: event.endStr,
      color: event.backgroundColor,
    });
  },
};
</script>

<template>
  <div class="bg-white rounded-xl p-6 shadow-md max-w-5xl mx-auto">
    <h2
      class="text-2xl font-semibold mb-4 flex items-center gap-2 text-gray-800"
    >
      <i class="mdi mdi-calendar-blank-outline text-blue-500 text-3xl"></i>
      行程日曆
    </h2>
    <FullCalendar
      class="calendar"
      :options="calendarOptions"
      ref="calendarRef"
    />

    <v-dialog v-model="dialog" max-width="480" persistent>
      <v-card class="rounded-xl">
        <v-card-title class="text-lg font-semibold">
          {{ isEditing ? "編輯行程" : "新增行程" }}
        </v-card-title>
        <v-card-text>
          <v-select
            label="選擇城市"
            :items="cities"
            item-title="name"
            item-value="id"
            v-model="selectedCityId"
            @change="updateSpots"
            variant="outlined"
            density="compact"
            class="mb-3"
          />
          <v-select
            label="選擇景點"
            :items="filteredSpots"
            v-model="selectedSpot"
            variant="outlined"
            density="compact"
            class="mb-3"
          />
          <v-text-field
            label="開始時間"
            v-model="startTime"
            type="datetime-local"
            variant="outlined"
            density="compact"
            class="mb-3"
          />
          <v-text-field
            label="結束時間"
            v-model="endTime"
            type="datetime-local"
            variant="outlined"
            density="compact"
          />
        </v-card-text>
        <v-card-actions class="justify-end gap-2">
          <v-btn
            color="error"
            variant="outlined"
            v-if="isEditing"
            @click="deleteEvent"
            >刪除</v-btn
          >
          <v-btn color="primary" variant="outlined" @click="saveEvent">{{
            isEditing ? "更新" : "新增"
          }}</v-btn>
          <v-btn text variant="outlined" @click="dialog = false">取消</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap");

@import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap");

.calendar :deep(.fc) {
  font-family: "Inter", sans-serif;
  --fc-button-text-color: #ffffff;
  --fc-button-bg-color: #4285f4;
  --fc-button-border-color: #4285f4;
  --fc-button-hover-bg-color: #3367d6;
  --fc-button-hover-border-color: #3367d6;
  --fc-event-bg-color: #4285f4;
  --fc-event-border-color: #4285f4;
  --fc-event-text-color: white;
  --fc-today-bg-color: #e8f0fe;
  background-color: #ffffff;
  border-radius: 12px;
  padding: 12px;
}

.calendar :deep(.fc-toolbar-title) {
  font-size: 1.2rem;
  font-weight: 600;
  color: #202124;
}

.calendar :deep(.fc-button) {
  border-radius: 6px;
  font-weight: 500;
  text-transform: capitalize;
  padding: 0.4em 1em;
}

.calendar :deep(.fc-button-group > .fc-prev-button),
.calendar :deep(.fc-button-group > .fc-next-button) {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.4em 0.6em;
  font-size: 1.2rem;
}

.calendar :deep(.fc-event) {
  font-size: 0.875rem;
  padding: 6px 10px;
  border-radius: 6px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  transition: all 0.2s ease;
}

.calendar :deep(.fc-event:hover) {
  transform: scale(1.02);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  cursor: pointer;
}
</style>
