import request from "@/utils/request";

export function fetchDataByType(type) {
  return request({
    method: "get",
    url: `/course/get_course/${type}`, // 使用模板字符串插入type变量
  });
}

export function ReturnSelectedCourse() {
  return request({
    method: "get",
    url: `/course/showSelectedCourse`,
  });
}
export function fetchDataByCourseId(id) {
  return request({
    method: "get",
    url: `/course/showSelectedStudents/${id}`,
  });
}
export function submitSelectedCourse(data) {
  return request({
    method: "post",
    url: `/course/addCourse`,
    data,
  });
}
export function searchTeacherBySid(Id) {
  return request({
    method: "get",
    url: "/course/search-teacher", // 根据你的实际API端点修改
    params: { sid: Id }, // 假设后端API使用'sid'作为查询学号的参数
  });
}
export function submitCourse(data) {
  return request({
    method: "post",
    url: "/course/submit",
    data,
  });
}
