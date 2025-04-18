import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      // component: () => import("@/layouts/mainlayout.vue"),
      component: () => import("@/layouts/memberLayout.vue"),
      children: [
        {
          path: "",
          component: () => import("@/page/member/test1.vue"),
        },
        {
          path: "test2",
          component: () => import("@/page/member/test2.vue"),
        },
        {
          path: "test3",
          component: () => import("@/page/member/Test.vue"),
        },
        {
          //座位選擇
          path: "seatSelection",
          component: () => import("@/page/member/seatSelection.vue"),
        },
        {
          //會員專區
          path: "memberFront",
          component: () => import("@/page/member/MemberPageFront.vue"),
        },
      ],

      // component: () => import("@/layouts/userView.vue"),
    },
    {
      path: "/admin",
      component: () => import("@/layouts/adminView.vue"),
      meta: { requiresAuth: true }, // 需要登錄的頁面
      children: [
        {
          path: "airport",
          component: () => import("@/page/admin/Airportspage.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },
        {
          path: "test",
          component: () => import("@/page/admin/TEST.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },

        {
          path: "members",
          component: () => import("@/page/admin/Memberpage.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },

        {
          path: "products",
          component: () => import("@/page/admin/Products.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },

        {
          path: "flight",
          component: () => import("@/page/admin/Flight.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },
        {
          path: "Seats",
          component: () => import("@/page/admin/Seats.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },
        {
          path: "Ticket",
          component: () => import("@/page/admin/Ticket.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },
        {
          path: "travel",
          component: () => import("@/components/TravelPage.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },
      ],
    },
    {
      path: "/login",
      component: () => import("@/page/auth/LoginPage.vue"),
    },
    {
      path: "/testtt",
      component: () => import("@/page/admin/TESTTT.vue"),
      meta: { requiresAuth: true }, // 需要登錄的頁面
    },
    {
      path: "/ss",
      component: () => import("@/layouts/memberLayout.vue"),
      meta: { requiresAuth: true }, // 需要登錄的頁面
    },
    {
      //會員登入
      path: "/loginUser",
      component: () => import("@/page/member/LoginMember.vue"),
    },
    {
      path: "/SignUp",
      component: () => import("@/page/member/SignUp.vue"),
    },
    //google 第三方登入(GOOGLE , FACEBOOK)
    {
      path: "/oauth2/success",
      name: "OAuthSuccess",
      component: () => import("@/page/oauth/OAuthSuccess.vue"),
    },

    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue'),
    // },
  ],
});

// 路由守衛
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  console.log("路由守衛觸發，當前登錄狀態：", authStore.isAuthenticated);

  // 如果目標頁面需要登錄並且用戶尚未登錄
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    console.log("用戶未登錄，跳轉到登錄頁面");
    next({ path: "/login" }); // 重定向到登錄頁面
  } else {
    console.log("允許訪問，繼續進行");
    next(); // 允許路由繼續
  }
});

export default router;
