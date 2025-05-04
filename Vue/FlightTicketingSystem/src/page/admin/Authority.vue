<template>
  <div class="pa-4">
    <v-row>
      <h2 class="text-h5 mb-4">管理員清單</h2>
      <v-btn class="ml-6" color="primary" @click="dialogNewAdmin = true"
        >新增管理員</v-btn
      >
    </v-row>
    <v-data-table
      :headers="headers"
      :items="adminList"
      :items-per-page="5"
      class="elevation-1"
    >
      <template #item.index="{ index }">
        {{ index + 1 }}
      </template>

      <template #item.actions="{ item }">
        <v-btn color="green" @click="openDialogRole(item)">編輯角色</v-btn>
        <v-btn color="info" @click="openDialog(item)">編輯權限</v-btn>
      </template>
    </v-data-table>

    <!-- 權限編輯彈窗 -->
    <v-dialog v-model="dialog" max-width="500">
      <v-card>
        <v-card-title>編輯使用者權限</v-card-title>
        <v-card-text>
          <v-checkbox
            v-for="role in availableRoles"
            :key="role"
            v-model="editedRoles"
            :label="role"
            :value="role"
          />
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn text @click="dialog = false">取消</v-btn>
          <v-btn color="primary" @click="saveRoles">儲存</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 角色編輯彈窗 -->
    <v-dialog v-model="dialogRole" max-width="500">
      <v-card>
        <v-card-title>編輯使用者角色</v-card-title>
        <v-card-text>
          <v-checkbox
            v-for="role in availableRolesUSER"
            :key="role"
            v-model="editedRolesUSER"
            :label="role"
            :value="role"
          />
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn text @click="dialogRole = false">取消</v-btn>
          <v-btn color="primary" @click="saveRolesUser">儲存</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 新增管理員彈窗 -->
    <v-dialog v-model="dialogNewAdmin" max-width="1000">
      <v-card>
        <v-card-title>新增管理員</v-card-title>
        <v-card-text>
          <v-form ref="form">
            <v-row>
              <v-col cols="12" md="4">
                <v-text-field
                  v-model="insertData.fullName"
                  label="全名"
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="4">
                <v-text-field
                  v-model="insertData.username"
                  label="使用者名稱"
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="4">
                <v-text-field
                  v-model="insertData.password"
                  label="使用者密碼"
                  type="password"
                  :rules="[rules.required, rules.password]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="4">
                <v-text-field
                  v-model="insertData.email"
                  label="信箱"
                  :rules="[rules.required, rules.email]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="4">
                <v-text-field
                  v-model="insertData.phoneNumber"
                  label="電話"
                  :rules="[rules.required, rules.phone]"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn text @click="dialogNewAdmin = false">取消</v-btn>
          <v-btn color="primary" @click="submitForm">儲存</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { ApiMember } from "@/utils/API";
import { useAuthStore } from "@/stores/auth"; // 引入 Pinia store

const authStore = useAuthStore(); // 使用 store

// 欄位設定
const headers = [
  //   { title: "編號", key: "index", sortable: false },
  { title: "編號", key: "memberId" },
  { title: "全名", key: "fullName" },
  { title: "管理員名稱", key: "username" },
  { title: "信箱", key: "email" },
  { title: "角色", key: "authority" },
  { title: "權限", key: "authorityDetail" },
  { title: "操作", key: "actions", sortable: false },
];

// 所有管理員資料
const adminList = ref([]);

// 權限彈窗控制
const dialog = ref(false);
const selectedUser = ref(null);
const editedRoles = ref([]);

// 可用權限列表（依據你的系統設定）
const availableRoles = ["會員", "航線", "產品", "旅遊"];

// 取得管理員列表
function getAllAdmin() {
  ApiMember.getAllAdminList().then((res) => {
    adminList.value = res.data;
  });
}

// 開啟彈窗
function openDialog(user) {
  console.log("要編輯的使用者：", user);
  selectedUser.value = user;
  console.log("使用者權限 (openDialog)：", user.authorityDetail);

  const currentRoles = user.authorityDetail
    ? user.authorityDetail.split(",")
    : [];
  console.log("分割後的權限 (openDialog)：", currentRoles);
  console.log("可用權限 (availableRoles)：", availableRoles);

  // 檢查分割後的權限是否包含在 availableRoles 中
  currentRoles.forEach((role) => {
    console.log(
      `檢查權限 "${role}" 是否在 availableRoles 中：`,
      availableRoles.includes(role)
    );
  });

  editedRoles.value = currentRoles;
  dialog.value = true;
}

// 儲存權限
function saveRoles() {
  console.log("要儲存的角色：", editedRoles.value);
  console.log("要儲存的使用者：", selectedUser.value.memberId);
  const updatedAuthorityDetail = editedRoles.value.join(",");
  const result = {
    id: selectedUser.value.memberId,
    authorityDetail: updatedAuthorityDetail, // 👉 轉成 "ADMIN_FLIGHT,ADMIN_TICKET" 格式
  };
  // TODO: 呼叫 API 更新後端的權限
  ApiMember.updateMemberAuthorityDetail(result).then((res) => {
    if (res) {
      console.log("更新成功");

      // ✅ 若是目前登入使用者的權限被修改，也同步更新到 authStore
      if (authStore.user?.memberId === selectedUser.value.memberId) {
        authStore.updateAuthorityDetail(updatedAuthorityDetail);
      }
      // 立即重新取得管理員列表
      getAllAdmin();
    }
  });
  dialog.value = false;
}

// 可用權限列表（依據你的系統設定）
const availableRolesUSER = ["USER", "ADMIN"];
// const availableRoles = ["會員", "航線", "旅遊"];

const dialogRole = ref(false);
const editedRolesUSER = ref([]);

// 開啟角色編輯彈窗
function openDialogRole(user) {
  console.log("要編輯角色的使用者：", user);
  selectedUser.value = user;

  const currentRolesUser = user.authority ? user.authority.split(",") : [];
  console.log("分割後的權限 (openDialog)：", currentRolesUser);

  console.log("使用者角色 (openDialog)：", user.authority);

  editedRolesUSER.value = currentRolesUser;
  dialogRole.value = true;
}

// 儲存角色
function saveRolesUser() {
  console.log("按下按鈕");
  console.log("要儲存的角色：", editedRolesUSER.value);
  console.log("要儲存的使用者：", selectedUser.value.memberId);
  const updatedAuthority = editedRolesUSER.value.join(",");
  const resultRole = {
    id: selectedUser.value.memberId,
    authority: updatedAuthority, // 👉 轉成 "ADMIN_FLIGHT,ADMIN_TICKET" 格式
  };
  // TODO: 呼叫 API 更新後端的權限
  ApiMember.updateMemberAuthority(resultRole).then((res) => {
    if (res) {
      console.log("更新成功");

      // ✅ 若是目前登入使用者的權限被修改，也同步更新到 authStore
      if (authStore.user?.memberId === selectedUser.value.memberId) {
        authStore.updateAuthority(updatedAuthority);
      }
      // 立即重新取得管理員列表
      getAllAdmin();
    }
  });
  dialogRole.value = false;
}

//新增管理員
// 提交前先驗證整個 form
const form = ref(null);
const submitForm = () => {
  form.value.validate().then((success) => {
    if (success) {
      saveNewAdmin();
    }
  });
};

// 驗證規則
const rules = {
  required: (v) => !!v || "此欄位為必填",
  email: (v) => /.+@.+\..+/.test(v) || "請輸入有效的 Email",
  phone: (v) => /^09\d{8}$/.test(v) || "請輸入正確的手機格式",
  password: (v) => v.length >= 6 || "密碼需至少 6 碼",
};

const dialogNewAdmin = ref(false);
const DEFAULT_INSERT = ref({
  fullName: "",
  username: "",
  password: "",
  email: "",
  phoneNumber: "",
});
const insertData = ref({ ...DEFAULT_INSERT.value });

//開啟新增管理員dialog
function openDialogNewAdmin() {
  dialogNewAdmin.value = true;
}
//儲存新增管理員
function saveNewAdmin() {
  console.log("按下按鈕");
  console.log("要儲存的使用者：", insertData.value);
  ApiMember.insertMemberDefaultAdmin(insertData.value).then((res) => {
    if (res) {
      console.log("新增成功");
      // 立即重新取得管理員列表
      getAllAdmin();
      dialogNewAdmin.value = false;

      // ✅ 清空表單
      insertData.value = {
        fullName: "",
        username: "",
        password: "",
        email: "",
        phoneNumber: "",
      };

      // ✅reset 驗證狀態
      form.value?.resetValidation?.();
    }
  });
}

onMounted(() => {
  getAllAdmin();
});
</script>

<style scoped></style>
