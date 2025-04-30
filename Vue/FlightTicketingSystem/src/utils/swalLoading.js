// src/utils/swalLoading.js
import Swal from 'sweetalert2'

const MIN_LOADING_TIME = 500; // 寫死最短顯示時間（毫秒）

let loadingSwalOpenTime = null;

export function showLoadingSwal(title = '請稍候...', text = '資料處理中') {
  loadingSwalOpenTime = Date.now();
  Swal.fire({
    title,
    text,
    allowOutsideClick: false,
    allowEscapeKey: false,
    showConfirmButton: false,
    didOpen: () => {
      Swal.showLoading()
    }
  })
}

export function closeLoadingSwal() {
  if (loadingSwalOpenTime) {
    const elapsed = Date.now() - loadingSwalOpenTime;
    if (elapsed < MIN_LOADING_TIME) {
      setTimeout(() => {
        Swal.close();
        loadingSwalOpenTime = null;
      }, MIN_LOADING_TIME - elapsed);
      return;
    }
  }
  Swal.close();
  loadingSwalOpenTime = null;
}

/**
 * 顯示操作完成提示
 * @param {string} title - 標題
 * @param {string} text - 內容
 */
export function showSuccessSwal(title = '操作完成', text = '') {
    Swal.fire({
      title,
      text,
      icon: 'success',
      timer: 3000,
      showConfirmButton: false
    })
  }