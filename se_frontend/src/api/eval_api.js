import request from "@/utils/request";

export function fetchCoursesInside() {
  const url = "/course/taken";
  return request({
    method: "get",
    url: url,
  });
}

export function submitRatingInside(payLoad) {
  return request({
    method: "post",
    url: "/evaluation",
    payLoad,
  });
}
