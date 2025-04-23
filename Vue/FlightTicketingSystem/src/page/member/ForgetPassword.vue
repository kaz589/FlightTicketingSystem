<template>
  <!-- 忘記密碼表單 -->
  <v-container class="d-flex justify-center align-center" style="height: 100vh">
    <v-card elevation="4" width="400">
      <v-card-title class="text-h6 text-center">忘記密碼</v-card-title>
      <v-card-text>
        <v-form
          @submit.prevent="requestPasswordReset"
          ref="formRef"
          v-model="isValid"
        >
          <v-text-field
            v-model="email"
            label="請輸入您的電子信箱"
            type="email"
            :rules="[rules.required, rules.email]"
            prepend-inner-icon="mdi-email"
            outlined
            dense
          ></v-text-field>

          <br />
          <v-progress-linear
            v-if="hide"
            color="green"
            indeterminate
          ></v-progress-linear>

          <v-btn
            type="submit"
            color="primary"
            class="mt-4"
            block
            :disabled="!isValid"
          >
            發送重設密碼郵件
          </v-btn>
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
import { ref } from "vue";
import { ApiEmail } from "@/utils/API";
import Swal from "sweetalert2";
import { useRouter } from "vue-router";

const router = useRouter();

const email = ref(""); // 儲存電子信箱
const hide = ref(false); //讀條

// const formRef = ref(null);
const isValid = ref(false);

const rules = {
  required: (value) => !!value || "此欄位為必填",
  email: (value) =>
    /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value) || "請輸入有效的信箱地址",
};

async function requestPasswordReset() {
  //   isValid = await formRef.value.validate(); // 這會是 true 或 false

  //送出驗證
  ApiEmail.requestReset(email.value).then((res) => {
    if (res.status === 200) {
      Swal.fire({
        title: "已送出驗證信!",
        icon: "success",
        draggable: true,
      });
    }

    console.log("送出重設密碼請求給：", email.value);

    console.log(hide.value);

    router.push("/loginUser");
  });
  //當尚未完成寄信的時候，顯示讀條(因為在then()外面，所以會先執行)
  hide.value = true;
  //   console.log(await formRef.value.validate());

  // 呼叫後端 API 發送重設密碼信
}
</script>

<style scoped></style>
