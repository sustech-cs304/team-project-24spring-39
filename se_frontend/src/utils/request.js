import axios from "axios";
import useMessage from "@/hooks/useMessage";

const { ElMessage } = useMessage();

const service = axios.create({
  // 根据项目所处的环境，自动切换所需的 baseURL
  baseURL: process.env.VUE_APP_URL,
  // 请求超时时间
  timeout: 1000 * 60 * 5,
});

// 请求拦截器
service.interceptors.request.use(
  function (config) {
    // 在发送请求之前做些什么
    console.log("请求拦截器", config);
    return config;
  },
  function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  function (response) {
    // 对响应数据做点什么
    if (response.data.meta.status !== 200) {
      ElMessage({
        showClose: true,
        message: response.data.msg,
        type: "error",
      });
    } else {
      console.log("响应拦截器", response.data);
      return response.data;
    }
  },
  function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);

export default service;
