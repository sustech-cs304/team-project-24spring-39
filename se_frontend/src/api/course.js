import request from "@/utils/request";

export function getAllCourse() {
  return request({
    method: "get",
    url: "/course",
  });
}

// export function addCourse(data) {
//   return request({
//     method: "post",
//     url: "/course",
//     data,
//   });
// }
