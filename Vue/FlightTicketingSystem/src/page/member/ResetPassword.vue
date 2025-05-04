<template>
  <v-container class="d-flex justify-center align-center" style="height: 100vh">
    <v-card width="400">
      <v-card-title class="text-h6 text-center">重設密碼</v-card-title>
      <v-card-text v-if="tokenValid">
        <v-form ref="formRef" @submit.prevent="submitNewPassword" v-model="isValid">
          <v-text-field
            v-model="newPassword"
            label="新密碼"
            type="password"
            :rules="[rules.required]"
          />
          <v-text-field 
          v-model="passwordConfirm"
          label="確認密碼"
          type="password"
          :rules="[rules.confirm]"
          />
          <v-btn type="submit" color="primary" block class="mt-2" :disabled="!isValid">送出</v-btn>
        </v-form>
      </v-card-text>
      <v-card-text v-else>
        <p class="text-error text-center">Token 無效或已過期</p>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";
import { ApiEmail } from "@/utils/API";

const router = useRouter();
const route = useRoute();
const token = route.query.token;
const newPassword = ref("");
const tokenValid = ref(false);
const passwordConfirm = ref("");
const isValid = ref(false);

const rules = {
  required: (v) => !!v || "密碼不能為空",
  confirm: (C) => C == newPassword.value || "密碼不一致" 
};

onMounted(async () => {
  try {
    const res = await ApiEmail.tokenIsValid(token);
    if (res.status === 200) {
      tokenValid.value = true;
    }
  } catch (e) {
    tokenValid.value = false;
  }
});

const submitNewPassword = async () => {
  try {
    const res = await ApiEmail.resetPassword(token, newPassword.value);

    if (res.status === 200) {
      Swal.fire("成功", "密碼已成功重設", "success");
      router.push("/loginUser");
    } else {
      Swal.fire("錯誤", "密碼重設失敗，請稍後再試", "error");
    }
  } catch (e) {
    console.error(e); // 顯示錯誤細節在控制台
    const errorMessage = e.response?.data || "無法聯繫伺服器，請稍後再試";
    Swal.fire("錯誤", errorMessage, "error");
  }
};
</script>
