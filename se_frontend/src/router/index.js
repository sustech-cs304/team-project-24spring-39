import { createRouter, createWebHistory } from "vue-router";

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
        component: () => import("../views/courseEvaluate.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
