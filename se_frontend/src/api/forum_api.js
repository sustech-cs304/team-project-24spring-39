import request from "@/utils/request";

export function fetchAllPostsInside() {
  const url = "/forum/all";
  return request({
    method: "get",
    url: url,
  });
}

export function fetchMyPostsInside() {
  const url = "/forum/my";
  return request({
    method: "get",
    url: url,
  });
}

export function fetchMajorCoursesInside() {
  const url = "/forum/major_courses";
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
    payLoad,
  });
}

export function deletePostInside(postID) {
  const url = "/forum/delete/" + String(postID);
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
    payLoad,
  });
}

export function applyFilterInside(payLoad) {
  const url = "/forum/filter";
  return request({
    method: "post",
    url: url,
    payLoad,
  });
}
