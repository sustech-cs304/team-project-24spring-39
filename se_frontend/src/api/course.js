import request from "@/utils/request";

export function fetchDataByType(type) {
  return request({
    method: "get",
    url: `/Course/getCourse/${type}`, // 使用模板字符串插入type变量
  });
}

export function ReturnSelectedCourse() {
  return request({
    method: "get",
    url: `/Course/showSelectedCourse`,
  });
}
