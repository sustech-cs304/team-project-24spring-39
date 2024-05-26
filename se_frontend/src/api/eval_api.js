import request from "@/utils/request";

export function fetchCoursesInside(studentNumber) {
  const url = "/Course/taken/student_id?sid=" + String(studentNumber);
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
