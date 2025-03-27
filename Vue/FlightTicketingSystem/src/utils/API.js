import instance from "./http";

//機場相關API
export const testapi = () => instance.get('/getAll');