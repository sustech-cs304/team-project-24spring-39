import request from "@/utils/request";

export function fetchTodoList(type) {
  console.log("type: " + type);
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
