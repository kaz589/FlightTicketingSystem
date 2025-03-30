import instance from "./http";

//機場相關API
export const testapi = (page,size) => instance.get(`/Test/${page}/${size}`);