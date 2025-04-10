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
        // 將登入狀態保存到 localStorage
        localStorage.setItem('isLoggedIn', 'true')
        localStorage.setItem('user', JSON.stringify(user))
      },
      logout() {
        this.isAuthenticated = false;  // 設置為未登入狀態
        this.user = null
        localStorage.removeItem('isLoggedIn')
        localStorage.removeItem('user')
      },

      checkLoginStatus() {
        // 檢查 localStorage 中是否有保存登入狀態
        const isLoggedIn = localStorage.getItem('isLoggedIn');
        const user = JSON.parse(localStorage.getItem('user'));
  
        if (isLoggedIn === 'true' && user) {
          this.isAuthenticated = true;
          this.user = user;
        } else {
          this.isAuthenticated = false;
          this.user = null;
        }
      }

    },
  });