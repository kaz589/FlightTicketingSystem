        FlightTicketingSystem/
        ├── public/                           # 公共資源（不經 Webpack 處理）
        │   ├── index.html                    # 主 HTML 文件
        │   ├── favicon.ico                   # 網站圖標
        │   └── assets/                       # 靜態資源（圖片等）
        │       ├── logo.png
        │       └── ...
        ├── src/                              # 源碼目錄
        │   ├── api/                          # API 請求封裝
        │   │   ├── frontend/                 # 前台 API
        │   │   │   ├── flight.js             # 航班相關 API
        │   │   │   ├── booking.js            # 訂票相關 API
        │   │   │   └── user.js               # 用戶相關 API
        │   │   └── admin/                    # 後台 API
        │   │       ├── manageFlights.js      # 航班管理 API
        │   │       ├── manageBookings.js     # 訂票管理 API
        │   │       └── manageUsers.js        # 用戶管理 API
        │   ├── assets/                       # 靜態資源（經 Webpack 處理）
        │   │   ├── images/                   # 圖片
        │   │   ├── styles/                   # 全局樣式
        │   │   │   ├── variables.scss        # SCSS 變數
        │   │   │   ├── mixins.scss           # SCSS 混合
        │   │   │   └── global.scss           # 全局樣式
        │   │   └── fonts/                    # 字體
        │   ├── components/                   # 可重用的 Vue 組件
        │   │   ├── common/                   # 通用組件
        │   │   │   ├── Header.vue            # 頁面頭部
        │   │   │   ├── Footer.vue            # 頁面底部
        │   │   │   ├── Pagination.vue        # 分頁組件
        │   │   │   └── Modal.vue             # 模態框組件
        │   │   ├── frontend/                 # 前台專用組件
        │   │   │   ├── FlightSearch.vue      # 航班搜索組件
        │   │   │   ├── BookingForm.vue       # 訂票表單
        │   │   │   └── Confirmation.vue      # 訂票確認
        │   │   └── admin/                    # 後台專用組件
        │   │       ├── FlightManagement.vue  # 航班管理組件
        │   │       ├── BookingManagement.vue # 訂票管理組件
        │   │       └── UserManagement.vue    # 用戶管理組件
        │   ├── layouts/                      # 頁面佈局
        │   │   ├── FrontendLayout.vue        # 前台佈局
        │   │   └── AdminLayout.vue           # 後台佈局
        │   ├── pages/                        # 頁面級組件
        │   │   ├── frontend/                 # 前台頁面
        │   │   │   ├── Home.vue              # 首頁
        │   │   │   ├── Search.vue            # 航班搜索頁面
        │   │   │   ├── Booking.vue           # 訂票頁面
        │   │   │   └── Confirmation.vue      # 訂票確認頁面
        │   │   └── admin/                    # 後台頁面
        │   │       ├── Dashboard.vue         # 儀表板
        │   │       ├── ManageFlights.vue     # 航班管理頁面
        │   │       ├── ManageBookings.vue    # 訂票管理頁面
        │   │       └── ManageUsers.vue       # 用戶管理頁面
        │   ├── router/                       # 路由配置
        │   │   ├── index.js                  # 路由主入口
        │   │   ├── frontend.js               # 前台路由
        │   │   └── admin.js                  # 後台路由
        │   ├── store/                        # Vuex 狀態管理
        │   │   ├── modules/                  # 狀態模組
        │   │   │   ├── flight.js             # 航班相關狀態
        │   │   │   ├── booking.js            # 訂票相關狀態
        │   │   │   └── user.js               # 用戶相關狀態
        │   │   ├── index.js                  # Vuex 主入口
        │   │   └── getters.js                # 全局 Getters
        │   ├── utils/                        # 工具函數
        │   │   ├── request.js                # Axios 請求封裝
        │   │   ├── formatDate.js             # 日期格式化
        │   │   └── validation.js             # 表單驗證工具
        │   ├── App.vue                       # Vue 根組件
        │   └── main.js                       # Vue 入口文件
        ├── tests/                            # 測試文件
        │   ├── unit/                         # 單元測試
        │   │   └── components/               # 測試組件
        │   ├── e2e/                          # 端到端測試
        │   └── setup.js                      # 測試環境配置
        ├── babel.config.js                   # Babel 配置
        ├── vue.config.js                     # Vue CLI 配置
        ├── package.json                      # 項目依賴和腳本
        ├── README.md                         # 專案簡介
        └── .gitignore                        # Git 忽略規則
