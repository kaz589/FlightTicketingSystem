import axios from "axios";
import querystring from "querystring";



  
//axios通用設定
const instance = axios.create({
  baseURL:'http://localhost:8080/api',
  timeout: 5000,
});
//request 攔截器
instance.interceptors.request.use(
  (config) => {
    
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

//response 攔截器
instance.interceptors.response.use(
  (response) => {
    return response.status===200?Promise.resolve(response):Promise.reject(response) ;
  },
  (error) => {
    const { response } = error;
    if (response) {
      errorHandle(response.status, response.info);
    }
    return Promise.reject(error);
  }
);

//錯誤處理
const errorHandle = (status, info) => {
    switch(status) {
      case 400:
        console.log("語義有誤");
        break;
      case 401:
        console.log("服務器認證失敗");
        break;
      case 403:
        console.log("服務器拒絕訪問");
        break;
      case 404:
        console.log("地址錯誤");
        break;
      case 500:
        console.log("服務器遇到意外");
        break;
      case 502:
        console.log("服務器無響應");
        break;
      default:
        console.log(info);
        break;
        
    }
    alert(info);
  }
export default instance;
