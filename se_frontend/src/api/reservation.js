import request from "@/utils/request";

/**
 * @description 根据条件查询预约情况
 * @param {{date: (string|string), room_id: Identifier}} params - 查询参数
 * @param {string} params.date - 查询的日期
 * @param {id} params.room_id - 查询的地点ID
 * @returns {Promise} 请求的Promise
 */
export async function fetchBookings(params) {
  console.log("Params:", params); // 打印参数，检查是否有多余的参数
  const queryParams = new URLSearchParams(params).toString();
  return request({
    method: "get",
    url: `/reservation/bookings?${queryParams}`,
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
    params: { student_id: studentId }, // 假设后端API使用'sid'作为查询学号的参数
  });
}

/**
 * @description 提交预约信息
 * @param data
 * @returns {*}
 */
export function submitReservation(data) {
  // 构造query参数
  const queryParams = new URLSearchParams(data).toString();
  return request({
    method: "post",
    url: `/reservation/submit?${queryParams}`,
  });
}

export function deleteReservation(recordId) {
  return request({
    method: "delete",
    url: `/reservation/delete`,
    params: { reservation_id: recordId },
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

export function submitBuilding(params) {
  return request({
    method: "post",
    url: "/reservation/add_building",
    params,
  });
}

export function updateBuilding(params) {
  return request({
    method: "post",
    url: "/reservation/update_building_status",
    params,
  });
}

export function deleteBuilding(name) {
  return request({
    method: "delete",
    url: "/reservation/delete_building",
    params: { name: name },
  });
}

export function submitRoom(params) {
  return request({
    method: "post",
    url: `/reservation/add_room`,
    params,
  });
}

export function updateRoom(params) {
  return request({
    method: "post",
    url: "/reservation/update_room_status",
    params,
  });
}

export function deleteRoom(room_id) {
  return request({
    method: "delete",
    url: "/reservation/delete_room",
    params: { room_id: room_id },
  });
}
