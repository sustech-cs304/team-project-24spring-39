import request from "@/utils/request";

export function register(data) {
  return request({
    method: "post",
    url: "/register",
    data,
  });
}

/**
 * @description 用户登录
 */
export function login(data) {
  return request({
    method: "post",
    url: "/login",
    data,
  });
}
