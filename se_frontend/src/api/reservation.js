import request from "@/utils/request";

/**
 * @description 根据条件查询预约情况
 * @param {Object} params - 查询参数
 * @param {string} [params.date] - 查询的日期
 * @param {string[]} [params.placeIds] - 查询的地点ID
 * @returns {Promise} 请求的Promise
 */
export function fetchBookings(params) {
  return request({
    method: "get",
    url: "/reservation/bookings/2", // 根据你的实际API端点修改
    params: params, // 将所有查询参数传递给后端API
  });
}

/**
 * @description 根据学号（sid）查询学生信息
 * @param {string} studentId - 用户输入的学号
 */
export function searchStudentBySid(studentId) {
  return request({
    method: "get",
    url: "/reservation/search-student", // 根据你的实际API端点修改
    params: { sid: studentId }, // 假设后端API使用'sid'作为查询学号的参数
  });
}

/**
 * @description 提交预约信息
 * @param data
 * @returns {*}
 */
export function submitReservation(data) {
  // 构造查询字符串
  const queryParams = new URLSearchParams(data).toString();
  return request({
    method: "post",
    url: `/reservation/submit?${queryParams}`,
  });
}

// 地点管理
/**
 * @description 获取所有地点信息
 * @returns {*}
 */
export function fetchLocations() {
  return request({
    method: "get",
    url: "/reservation/locations",
  });
}

export function submitLocation(data) {
  const queryParams = new URLSearchParams(data).toString();
  return request({
    method: "post",
    url: `/reservation/submit-location?${queryParams}`,
  });
}
