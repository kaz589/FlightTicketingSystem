import { defineStore } from "pinia";

export const useAuthStore = defineStore('auth', {
    state: () => ({
      isAuthenticated: false,  // 記錄用戶是否登入
      user: null,  // 存儲用戶信息

    }),
    actions: {
      login(user) {
        this.isAuthenticated = true;  // 設置為登入狀態
        this.user = user
      },
      logout() {
        this.isAuthenticated = false;  // 設置為未登入狀態
        this.user = null
      },
    },
  });