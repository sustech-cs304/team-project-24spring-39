import request from "@/utils/request";

/**
 * @description 根据地点查询预约情况
 * @param placeId
 * @returns {*}
 */
export function fetchBookingsByPlace(placeId) {
  return request({
    method: "get",
    url: "/reservation/bookings", // 根据你的实际API端点修改
    params: { placeId }, // 假设后端API使用'placeId'作为查询地点的参数
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
  return request({
    method: "post",
    url: "/reservation/submit",
    data,
  });
}
