import request from "@/utils/request";

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

/**
 * @description: 获取菜单列表
 */
export function getMenuList() {
  return request({
    method: "get",
    url: "/userInfo{1}",
  });
}

export function fetchCoursesInside(studentNumber) {
  const url = "/evaluation?StudentNumber=" + String(studentNumber);
  return request({
    method: "get",
    url: url,
  });
}

export function submitRatingInside(payLoad) {
  return request({
    method: "post",
    url: "/evaluation",
    payLoad,
  });
}
