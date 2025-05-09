import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store
import { useSeatStore } from "@/stores/useSeatStore";
import Swal from "sweetalert2";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",

      component: () => import("@/layouts/Header.vue"),

      children: [
        {
          path: "",
          component: () => import("@/page/member/homepage.vue"),
        },
        {
          //里程兌換
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
          //支付頁面
          path: "SeatPayment",
          component: () => import("@/page/member/SeatPayment.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },
        {
          //票務訂單
          path: "456",
          component: () => import("@/page/member/TicketOrderList.vue"),
        },
        {
          //會員專區
          path: "memberFront",
          component: () => import("@/page/member/MemberPageFront.vue"),
        },
        {
          //購物車明細查看
          path: "checkout",
          component: () => import("@/page/member/Checkout.vue"),
        },
        {
          //結帳確認畫面
          path: "checkoutconfirm",
          component: () => import("@/page/member/checkoutConfirm.vue"),
        },
        {
          //會員查看訂單
          path: "checkredeem",
          component: () => import("@/page/member/checkredeem.vue"),
        },
        {
          path: "travel",
          component: () => import("@/layouts/userView.vue"),
        },
      ],
    },
    {
      path: "/admin",
      component: () => import("@/layouts/adminView.vue"),
      meta: { requiresAuth: true, roles: ["ADMIN", "MANAGER"] },
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
          path: "redeemmanage",
          component: () => import("@/page/admin/RedeemManage.vue"),
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
          component: () => import("@/page/admin/TravelAdmin.vue"),
          meta: { requiresAuth: true }, // 需要登錄的頁面
        },
        {
          path: "authority",
          component: () => import("@/page/admin/Authority.vue"),
          meta: { requiresAuth: true, roles: ["MANAGER"] },
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
    //忘記密碼
    {
      path: "/forgetPassword",
      name: "forgetPassword",
      component: () => import("@/page/member/ForgetPassword.vue"),
    },
    // 重設密碼
    {
      path: "/reset-password", // 匹配 URL 中的 /reset-password
      name: "reset-password",
      component: () => import("@/page/member/ResetPassword.vue"), // 你的 ResetPassword.vue 組件
      props: (route) => ({ token: route.query.token }), // 透過 query 參數將 token 傳給 ResetPassword 組件
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
  scrollBehavior(to, from, savedPosition) {
    // 每次切換路由時，頁面滾動到最頂端
    return { top: 0 };
  },
});

// 路由守衛
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  const seatStore = useSeatStore();
  console.log("路由守衛觸發，當前登錄狀態：", authStore.isAuthenticated);

  // 只在 seatSelection 或 SeatPayment 頁面保留選位
  const keepSeatPaths = ["/seatSelection", "/SeatPayment"];
  const isSeatPage = keepSeatPaths.includes(to.path);

  // 進入 SeatPayment 前必須有選座位
  if (to.path === "/SeatPayment") {
    if (!seatStore.selectedSeatIds || seatStore.selectedSeatIds.length === 0) {
      Swal.fire({
        icon: "warning",
        title: "請先選擇座位",
        text: "請先完成選位，再進行付款。",
      });

      // 從 from.query.flightid 取得 flightid（假設是從 seatSelection 跳過來的）
      const flightid = from.query.flightid || to.query.flightid;

      // 如果有 flightid 就帶回 seatSelection
      if (flightid) {
        return next({ path: "/seatSelection", query: { flightid } });
      } else {
        // 沒有 flightid 就回首頁
        return next("/");
      }
    }
  }
  if (to.path === "/seatSelection" && !to.query.flightid) {
    Swal.fire({
      icon: "warning",
      title: "請選擇航班",
      text: "請從正確的航班入口進入選位。",
    });
    return next("/");
  }
  if (!isSeatPage) {
    seatStore.clearSelectedSeats && seatStore.clearSelectedSeats();
  }
  // 如果目標頁面需要登錄並且用戶尚未登錄
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    console.log("用戶未登錄，跳轉到登錄頁面");
    return next({ path: "/loginUser" }); // 重定向到登錄頁面
  }
  // 檢查角色權限

  const requiredRoles = to.meta.roles;
  const userRoles = authStore.roles;

  console.log("用戶角色：", userRoles);

  // 如果有角色限制，但用戶的角色沒有符合
  if (
    requiredRoles &&
    !requiredRoles.some((role) => userRoles.includes(role))
  ) {
    console.log("用戶角色不符合，跳轉未授權頁面");
    Swal.fire({
      icon: "error",
      title: "用戶角色不符合",
      text: "請以正確的帳號登入",
    });

    return next("/");
  }

  // 都通過才放行
  console.log("允許訪問，繼續進行");
  next();
});

export default router;
