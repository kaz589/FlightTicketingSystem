    FlightTicketingSystem/
    ├── src/
    │   ├── main/
    │   │   ├── java/                              # Java 程式碼目錄
    │   │   │   └── com/
    │   │   │       └── demo/                     # 加入 demo 層
    │   │   │           ├── controller/          # 控制層
    │   │   │           │   ├── FlightSearchController.java
    │   │   │           │   ├── BookingController.java
    │   │   │           │   └── UserController.java     
    │   │   │           ├── service/             # 業務邏輯層（Service）
    │   │   │           │    ├──UserService.java
    │   │   │           │    └── spec/            # service的Interface檔案(規範查詢用)
    │   │   │           │        └──IUser.java
    │   │   │           ├── repository/          # 資料訪問層（Repository/DAO）
    │   │   │           ├── model/               # 資料模型層（Model/Entity）
    │   │   │           ├── config/              # 配置類
    │   │   │           └── util/                # 工具類
    │   │   ├── webapp/
    │   │   │   ├── static/                      # 靜態資源（CSS、JS、圖片等）
    │   │   │   │   ├── frontend/                # 前台靜態資源
    │   │   │   │   │   ├── css/
    │   │   │   │   │   ├── js/
    │   │   │   │   │   └── images/
    │   │   │   │   └── admin/                   # 後台靜態資源
    │   │   │   │       ├── css/
    │   │   │   │       ├── js/
    │   │   │   │       └── images/
    │   │   │   ├── templates/                   # HTML 模板
    │   │   │   │   ├── frontend/                # 前台頁面模板
    │   │   │   │   │   ├── index.html           # 首頁
    │   │   │   │   │   ├── search.html          # 航班搜索頁面
    │   │   │   │   │   ├── booking.html         # 訂票頁面
    │   │   │   │   │   └── confirmation.html    # 訂票確認頁面
    │   │   │   │   └── admin/                   # 後台頁面模板
    │   │   │   │       ├── dashboard.html       # 後台儀表板
    │   │   │   │       ├── manage-flights.html  # 航班管理頁面
    │   │   │   │       ├── manage-bookings.html # 訂票管理頁面
    │   │   │   │       └── manage-users.html    # 用戶管理頁面
    │   ├── test/                                # 測試檔案
    │   │   ├── java/
    │   │   │   └── com/
    │   │   │       └── demo/                    # 測試檔案與主程式保持一致
    │   │   │           ├── controller/
    │   │   │           ├── service/
    │   │   │           └── repository/
    │   └── README.md                            # 專案簡介
    ├── pom.xml                                  # Maven 配置檔案
    ├── Vue/                                     # VUE檔案存放處(詳細檔案結構看裡面的README.md)
    └── .gitignore                               # Git 忽略規則
