import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem("token") || "",
    isAuthenticated: false, // 記錄用戶是否登入
    user: null, // 存儲用戶信息
    roles: JSON.parse(localStorage.getItem("roles") || "[]"), // 改成陣列
  }),

  actions: {
    login(user, token, externalRoles = []) {
      this.isAuthenticated = true; // 設置為登入狀態
      this.user = user;
      this.token = token;
      this.roles = externalRoles.length > 0 ? externalRoles : user.roles || []; // 若有外部角色資料則用外部傳回的角色
      // 將登入狀態保存到 localStorage
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("user", JSON.stringify(user));
      localStorage.setItem("token", token);
      localStorage.setItem("roles", JSON.stringify(this.roles));
    },
    logout() {
      this.isAuthenticated = false; // 設置為未登入狀態
      this.user = null;
      this.token = "";
      this.roles = [];
      localStorage.removeItem("isLoggedIn");
      localStorage.removeItem("user");
      localStorage.removeItem("token");
      localStorage.removeItem("roles");
    },

    checkLoginStatus() {
      // 檢查 localStorage 中是否有保存登入狀態
      const isLoggedIn = localStorage.getItem("isLoggedIn");
      const user = JSON.parse(localStorage.getItem("user"));
      const roles = JSON.parse(localStorage.getItem("roles") || "[]");

      if (isLoggedIn === "true" && user) {
        this.isAuthenticated = true;
        this.user = user;
        this.roles = roles;
      } else {
        this.isAuthenticated = false;
        this.user = null;
        this.roles = [];
      }
    },
    // 🔍 加一個方法來判斷是否有某個角色
    hasRole(role) {
      console.log("當前角色", role);

      return this.roles.includes(role);
    },
    // 加一個方法來即時更新角色權限
    updateAuthorityDetail(newAuthorityDetail) {
      if (this.user) {
        this.user.authorityDetail = newAuthorityDetail;
      }
    },
    // 加一個方法來即時更新角色
    updateAuthority(newAuthority) {
      if (this.user) {
        this.user.authority = newAuthority;
      }
    },
  },
});
