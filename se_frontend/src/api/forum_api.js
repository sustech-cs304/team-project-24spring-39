import request from "@/utils/request";

export function fetchAllPostsInside() {
  const url = "/forum/get_post_by_condition";
  return request({
    method: "get",
    url: url,
  });
}

export function fetchMyPostsInside(SID) {
  const url = "/forum/get_post_by_condition?authorId=" + String(SID);
  return request({
    method: "get",
    url: url,
  });
}

export function fetchMajorCoursesInside() {
  const url = "/forum/get_department";
  return request({
    method: "get",
    url: url,
  });
}

export function createPostInside(payLoad) {
  const url = "/forum/create";
  return request({
    method: "post",
    url: url,
    data: payLoad,
  });
}

export function deletePostInside(postID) {
  const url = "/forum/delete_post/" + String(postID);
  return request({
    method: "delete",
    url: url,
  });
}

export function makeCommentInside(payLoad) {
  const url = "/forum/comment";
  return request({
    method: "post",
    url: url,
    data: payLoad,
  });
}

export function applyFilterInside(payLoad) {
  const url =
    "/forum/get_post_by_condition?" +
    "majorTag=" +
    String(payLoad.majorTag) +
    "&courseTag=" +
    String(payLoad.courseTag);
  return request({
    method: "get",
    url: url,
  });
}

export function getPageNum() {
  const url = "/forum/get_post_count";
  return request({
    method: "get",
    url: url,
  });
}
