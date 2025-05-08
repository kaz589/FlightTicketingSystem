<template>
    <v-container>
      <!-- 注意事項區塊 -->
      <v-alert
        class="notice-alert"
        border="left"
        elevation="2"
      >
        <h4>注意事項：</h4>
        <ul>
          <li>部分商品數量有限，兌換完畢後將不再補貨，請依系統顯示為準。</li>
          <li>商品寄送範圍及條件，依各兌換品項標示為主，部分商品僅限台灣本島配送。</li>
          <li>哩程兌換後，如遇商品缺貨或停產，將由合作廠商以等值商品或等值哩程方式替代。</li>
          <li>為保障您的權益，請正確填寫聯絡資訊，若因資料錯誤導致無法寄送，恕不補發亦不退還哩程。</li>
          <li>商品圖片僅供參考，實際內容以廠商出貨為準。</li>
          <li>部分兌換品項需依各地法規及相關限制辦理，請自行確認符合使用資格。</li>
          <li>一經完成兌換手續，即視為同意兌換規範，恕不接受任何形式之更換、退貨或退款。</li>
          <li>系統維護或不可抗力因素（如天災、疫情）可能影響出貨時程，敬請理解並耐心等候。</li>
        </ul>
      </v-alert>
    </v-container>
  </template>
  
  <script>
  export default {
    name: 'NoticeBlock',
  };
  </script>
  
  <style scoped>
  .notice-alert {
    background-color: #1e3a8a; /* 深藍色（Tailwind 的 blue-800） */
    color: white;
    border-radius: 12px;
    padding: 16px;
  }
  .notice-alert ul {
    margin: 8px 0 0 16px;
    padding: 0;
  }
  .notice-alert li {
    margin-bottom: 4px;
  }
  </style>
  