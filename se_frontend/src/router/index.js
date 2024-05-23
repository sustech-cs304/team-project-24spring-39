import { createRouter, createWebHistory } from "vue-router";
import store from "@/store";
import useNProgress from "@/hooks/useNProgress";

const NProgress = useNProgress();

//布局组件
const Layout = () => import("../layout/Layout.vue");

const routes = [
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/LoginPage.vue"),
  },
  {
    path: "/",
    name: "layout",
    component: Layout,
    children: [
      {
        path: "/",
        name: "Home",
        meta: {
          title: "首页",
          breadcrumb: ["首页"],
        },
        component: () => import("../views/Home.vue"),
      },
      {
        path: "/selection",
        name: "courseSelection",
        meta: {
          title: "我要选课",
          breadcrumb: ["选课", "我要选课"],
        },
        component: () => import("../views/courseSelect.vue"),
      },
      {
        path: "/evaluation",
        name: "courseEvaluation",
        meta: {
          title: "评教",
          breadcrumb: ["评教"],
        },
        component: () => import("../views/courseEvaluate.vue"),
      },
      {
        path: "/forum",
        name: "forum",
        meta: {
          title: "课程论坛",
          breadcrumb: ["课程论坛"],
        },
        component: () => import("../views/forum.vue"),
      },
      {
        path: "/post/:id",
        name: "postShow",
        component: () => import("../views/postShow.vue"),
        props: true,
      },
      {
        path: "/reservation",
        name: "reservation",
        meta: {
          title: "自习室预约",
          breadcrumb: ["自习室预约"],
        },
        component: () => import("../views/Reservation.vue"),
      },
      {
        path: "/reservation-locations",
        name: "reservationLocations",
        meta: {
          title: "地点管理",
          breadcrumb: ["自习室预约", "地点管理"],
        },
        component: () => import("../views/LocationsManagement.vue"),
      },
      {
        path: "/reservation-records",
        name: "reservationRecords",
        meta: {
          title: "记录管理",
          breadcrumb: ["自习室预约", "记录管理"],
        },
        component: () => import("../views/RecordsManagement.vue"),
      },
    ],
  },
  // 匹配404路由
  {
    path: "/:pathMatch(.*)*",
    name: "NotFoundPage",
    component: () =>
      import(/* webpackChunkName: "NotFoundPage" */ "../views/NotFound.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  NProgress.start();
  if (to.path === "/login") {
    next();
  } else {
    // const token = localStorage.getItem("token");
    const token = store.state.userStore.token;
    if (!token) {
      next("/login");
    } else {
      next();
    }
  }
});

router.afterEach(() => {
  NProgress.done();
});

export default router;
