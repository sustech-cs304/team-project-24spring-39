import { register, login } from "@/api/user";
import router from "@/router/index";
import adminRoutes from "@/router/role/admin";
import userRoutes from "@/router/role/user";

export default {
  namespace: true,
  state: {
    // 是否是管理员
    isAdmin: false,
    // 用户信息
    userInfo:
      JSON.parse(localStorage.getItem(process.env.VUE_APP_USER_INFO)) || [],
    // 登录状态
    isLogin: localStorage.getItem("ISLOGIN") || false,

    token: localStorage.getItem("TOKEN") || "",

    // 是否动态添加路由
    isDynamicAddedRoute: false,

    // 菜单列表
    menuList: JSON.parse(localStorage.getItem("MENU_LIST")) || [
      {
        id: 0,
        pid: 0,
        title: "首页",
        icon: "House",
        url: "/",
        children: null,
      },
      {
        id: 1,
        pid: 0,
        title: "选课",
        icon: "House",
        url: "/selection",
        children: [
          {
            id: 6,
            pid: 1,
            title: "我要选课",
            icon: "House",
            url: "/selection",
            children: null,
          },
          {
            id: 7,
            pid: 1,
            title: "选课2",
            icon: "House",
            url: "/selection2",
            children: null,
          },
        ],
      },
      {
        id: 2,
        pid: 0,
        title: "评教",
        icon: "House",
        url: "/evaluation",
        children: null,
      },
      {
        id: 3,
        pid: 0,
        title: "课程论坛",
        icon: "House",
        url: "/forum",
        children: null,
      },
      {
        id: 4,
        pid: 0,
        title: "自习室预约",
        icon: "House",
        url: "/reservation",
        children: [
          {
            id: 8,
            pid: 4,
            title: "预约",
            icon: "House",
            url: "/reservation",
            children: null,
          },
          {
            id: 9,
            pid: 4,
            title: "地点管理",
            icon: "House",
            url: "/reservation-locations",
            children: null,
          },
          {
            id: 10,
            pid: 4,
            title: "记录管理",
            icon: "House",
            url: "/reservation-records",
            children: null,
          },
        ],
      },
      {
        id: 5,
        pid: 0,
        title: "设置",
        icon: "Setting",
        url: "/setting",
        children: null,
      },
    ],

    // 用户的菜单列表数据
    userMenuList: [
      {
        id: 0,
        pid: 0,
        title: "首页",
        icon: "House",
        url: "/",
        children: null,
      },
      {
        id: 1,
        pid: 0,
        title: "选课",
        icon: "House",
        url: "/selection",
        children: [
          {
            id: 6,
            pid: 1,
            title: "我要选课",
            icon: "House",
            url: "/selection",
            children: null,
          },
          {
            id: 7,
            pid: 1,
            title: "选课2",
            icon: "House",
            url: "/selection2",
            children: null,
          },
        ],
      },
      {
        id: 2,
        pid: 0,
        title: "评教",
        icon: "House",
        url: "/evaluation",
        children: null,
      },
      {
        id: 3,
        pid: 0,
        title: "课程论坛",
        icon: "House",
        url: "/forum",
        children: null,
      },
      {
        id: 4,
        pid: 0,
        title: "自习室预约",
        icon: "House",
        url: "/reservation",
        children: [
          {
            id: 8,
            pid: 4,
            title: "预约",
            icon: "House",
            url: "/reservation",
            children: null,
          },
          {
            id: 9,
            pid: 4,
            title: "地点管理",
            icon: "House",
            url: "/reservation-locations",
            children: null,
          },
          {
            id: 10,
            pid: 4,
            title: "记录管理",
            icon: "House",
            url: "/reservation-records",
            children: null,
          },
        ],
      },
      {
        id: 5,
        pid: 0,
        title: "设置",
        icon: "Setting",
        url: "/setting",
        children: null,
      },
    ],

    adminMenuList: [
      { name: "管理首页", link: "/admin/home" },
      { name: "用户管理", link: "/admin/users" },
    ],
  },
  mutations: {
    // 设置用户信息
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
      localStorage.setItem(
        process.env.VUE_APP_USER_INFO,
        JSON.stringify(userInfo)
      );
    },
    // 设置登录状态
    setLoginState(state, flag) {
      state.isLogin = flag;
      localStorage.setItem("ISLOGIN", flag);
    },
    // 设置token
    setToken(state, token) {
      state.token = token;
      localStorage.setItem("TOKEN", token);
    },
    setDynamicAddedRoute(state, flag) {
      state.isDynamicAddedRoute = flag;
    },
    setMenuList(state, menuList) {
      state.menuList = menuList;
      localStorage.setItem("MENU_LIST", JSON.stringify(menuList));
    },
  },
  actions: {
    // 处理注册的业务逻辑
    async handleRegister(data) {
      // 发送注册的网络请求
      try {
        const res = await register(data);
        console.log("注册成功：", res);
      } catch (error) {
        console.log("注册失败：", error);
      }
    },

    // 处理登录的业务逻辑
    async handleLogin({ state, commit }, data) {
      // 发送登录的网络请求
      try {
        const res = await login(data);
        // 1. 设置登录状态
        commit("setLoginState", true);
        // 2. 设置用户信息
        commit("setUserInfo", res.data);
        // 2. 设置token
        commit("setToken", res.data.token);
        // 3. 设置菜单列表
        let menuList = [];
        if (res.data.SID) {
          state.isAdmin = false;
          menuList = state.userMenuList;
        } else {
          state.isAdmin = true;
          menuList = state.adminMenuList;
        }
        console.log("menuList: ", menuList);
        commit("setMenuList", menuList); // 更新菜单列表

        // 4. 跳转到首页
        await router.push("/");
      } catch (error) {
        console.log("handleLogin error: ", error);
        return Promise.reject(error);
      }
    },
    // 处理退出登录
    logout({ commit }) {
      commit("setLoginState", false);
      commit("setUserInfo", []);
      commit("setToken", "");
      commit("setMenuList", []);
      localStorage.removeItem(process.env.VUE_APP_USER_INFO);
      localStorage.removeItem("ISLOGIN");
      localStorage.removeItem(process.env.VUE_APP_TOKEN_NAME);
      localStorage.removeItem("MENU_LIST");
      router.push("/login"); // 跳转到登录页面
    },

    loadAsyncRoute({ state, commit }) {
      // 根据登录身份动态添加路由
      if (state.isAdmin) {
        adminRoutes.forEach((route) => {
          router.addRoute("layout", route);
        });
        userRoutes.forEach((route) => {
          router.addRoute("layout", route);
        });
      } else {
        adminRoutes.forEach((route) => {
          router.addRoute("layout", route);
        });
        userRoutes.forEach((route) => {
          router.addRoute("layout", route);
        });
      }
      commit("setDynamicAddedRoute", true);
    },
  },
};
