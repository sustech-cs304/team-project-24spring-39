import request from "@/utils/request";

// export function fetchAvatar(sid) {
//   return request({
//     method: "get",
//     url: "/user/get_avatar",
//     params: { SID: sid },
//   });
// }

export function submitAvatar(data) {
  return request({
    method: "post",
    url: "/student/update_avatar",
    headers: {
      "Content-Type": "application/json", // 确保使用 JSON 格式
    },
    data: JSON.stringify(data), // 将数据序列化为 JSON 字符串
  });
}

export function fetchTodoList(type) {
  return request({
    method: "get",
    url: "/message/get_message",
    params: { type: type },
  });
}

/**
 * @description 提交待办事项
 * @param data
 * @returns {*}
 */
export function submitTodo(data) {
  return request({
    method: "post",
    url: "/message/add_message",
    data,
  });
}
