import { getTranslation } from "@/locales";

export default [
  {
    path: "/",
    name: "Home",
    meta: {
      title: getTranslation("breadcrumbs.home"),
      breadcrumb: [getTranslation("breadcrumbs.home")],
    },
    component: () => import("@/views/Home.vue"),
  },
  {
    path: "/selection",
    name: "courseSelection",
    meta: {
      title: getTranslation("breadcrumbs.selectCourse"),
      breadcrumb: [
        getTranslation("breadcrumbs.courseSelection"),
        getTranslation("breadcrumbs.selectCourse"),
      ],
    },
    component: () => import("@/views/courseSelect.vue"),
  },
  {
    path: "/evaluation",
    name: "courseEvaluation",
    meta: {
      title: getTranslation("breadcrumbs.evaluate"),
      breadcrumb: [getTranslation("breadcrumbs.courseEvaluation")],
    },
    component: () => import("@/views/courseEvaluate.vue"),
  },
  {
    path: "/forum",
    name: "forum",
    meta: {
      title: getTranslation("breadcrumbs.courseForum"),
      breadcrumb: [getTranslation("breadcrumbs.courseForum")],
    },
    component: () => import("@/views/forum.vue"),
  },
  {
    path: "/post/:id",
    name: "postShow",
    component: () => import("@/views/postShow.vue"),
    props: true,
  },
  {
    path: "/reservation",
    name: "reservation",
    meta: {
      title: getTranslation("breadcrumbs.reservation"),
      breadcrumb: [getTranslation("breadcrumbs.reservation")],
    },
    component: () => import("@/views/Reservation.vue"),
  },
];
