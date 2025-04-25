export function formatDepartureTime(str) {
    if (!str || typeof str !== 'string') return '';
    const date = new Date(str.replace(/-/g, '/')); // Safari 相容性
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const weekDayNames = ['日', '一', '二', '三', '四', '五', '六'];
    const weekDay = weekDayNames[date.getDay()];
    const hour = String(date.getHours()).padStart(2, '0');
    const min = String(date.getMinutes()).padStart(2, '0');
    return `${year}年${month}月${day}日 週${weekDay} ${hour}:${min}`;
  }

  export  function getFlightDuration(departureTime, arrivalTime) {
    if (!departureTime || !arrivalTime) {
      return "出發或抵達時間未設定";
    }
    // 1. 解析成 Date 物件
    const dep = new Date(departureTime.replace(/-/g, '/'));
    const arr = new Date(arrivalTime.replace(/-/g, '/'));
  
    // 2. 算出毫秒差
    let diffMs = arr - dep;
  
    if (diffMs < 0) return "抵達時間早於出發時間！";
  
    // 3. 換算成小時和分鐘
    const diffMins = Math.floor(diffMs / (1000 * 60));
    const hours = Math.floor(diffMins / 60);
    const minutes = diffMins % 60;
  
    // 4. 格式化
    return `${hours}:${minutes.toString().padStart(2, '0')}`;
  }


  export function formatDate(dateStr, type = 'full') {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    if (type === 'date') {
      return date.toLocaleDateString('zh-TW', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      });
    }
    if (type === 'time') {
      return date.toLocaleTimeString('zh-TW', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
      });
    }
    // 預設 full
    return (
      date.toLocaleDateString('zh-TW', { year: 'numeric', month: '2-digit', day: '2-digit' }) +
      ' ' +
      date.toLocaleTimeString('zh-TW', { hour: '2-digit', minute: '2-digit', hour12: true })
    );
  }