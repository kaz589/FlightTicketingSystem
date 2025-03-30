import { defineStore } from "pinia";
import { ref } from "vue";

export const useMemberStore = defineStore(
  "memberStore",
  () => {
    const member = ref({ token: "AAAA", id: "", name: "" });

    const login = (info) => {
      member.value = info;
    };

    const logout = () => {
      member.value = { token: "", id: "", name: "" };
    };

    return {
      member,
      login,
      logout,
    };
  },
  { persist: true }
);
