import request from "@/utils/request";

export function fetchCoursesInside(studentNumber) {
  String(studentNumber);
  const url = "/course/taken/student_id?sid=" + String(studentNumber);
  // const url = "/course/taken/12345678";
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
