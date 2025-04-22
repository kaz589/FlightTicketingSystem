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