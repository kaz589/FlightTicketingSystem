import { defineStore } from "pinia";

export const useEventStore = defineStore("event", {
  state: () => ({
    events: [],
  }),

  actions: {
    addEvent(event) {
      this.events.push(event);
    },

    updateEvent(updatedEvent) {
      const idx = this.events.findIndex((e) => e.id === updatedEvent.id);
      if (idx !== -1) {
        this.events[idx] = { ...this.events[idx], ...updatedEvent };
      }
    },

    removeEvent(id) {
      const index = this.events.findIndex((e) => e.id === id);
      if (index !== -1) {
        this.events.splice(index, 1);
      } else {
        console.warn("🔴 Tried to remove non-existent event:", id);
      }
    },

    clearAll() {
      this.events = [];
      localStorage.removeItem("event");
    },
  },

  persist: true,
});
