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
