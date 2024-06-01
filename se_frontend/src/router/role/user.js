export default [
  {
    path: "/",
    name: "Home",
    meta: {
      title: "首页",
      breadcrumb: ["首页"],
    },
    component: () => import("@/views/Home.vue"),
  },
  {
    path: "/selection",
    name: "courseSelection",
    meta: {
      title: "我要选课",
      breadcrumb: ["选课", "我要选课"],
    },
    component: () => import("@/views/courseSelect.vue"),
  },
  {
    path: "/evaluation",
    name: "courseEvaluation",
    meta: {
      title: "评教",
      breadcrumb: ["评教"],
    },
    component: () => import("@/views/courseEvaluate.vue"),
  },
  {
    path: "/reservation",
    name: "reservation",
    meta: {
      title: "自习室预约",
      breadcrumb: ["自习室预约"],
    },
    component: () => import("@/views/Reservation.vue"),
  },
];
