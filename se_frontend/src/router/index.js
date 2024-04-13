import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/LoginPage.vue"),
  },
  {
    path: "/",
    name: "layout",
    component: () => import("../layout/Layout.vue"),
    children: [
      { path: "", name: "Home", component: () => import("../views/Home.vue") },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
