import { login } from "@/api/user";
import router from "@/router/index";

export default {
  state: {
    isLogin: localStorage.getItem("ISLOGIN") || false,

    // 用户的菜单列表数据
    menuList:
      // []
      JSON.parse(localStorage.getItem("MENU_LIST") || "[]"),
  },
  mutations: {
    // 设置登录状态
    setLoginState(state, flag) {
      state.isLogin = flag;
      localStorage.setItem("ISLOGIN", flag);
    },
    setMenuList(state, menuList) {
      state.menuList = menuList;
      localStorage.setItem("MENU_LIST", JSON.stringify(menuList));
    },
  },
  actions: {
    // 处理登录的业务逻辑
    async handleLogin({ commit }, data) {
      // 发送登录的网络请求
      try {
        const res = await login(data);
        console.log("登录成功：", res);

        // 1. 设置登录状态
        commit("setLoginState", true);
        // 2. 设置token
        // commit("setTooken", res.data.token);
        // 3. 设置菜单列表
        commit("setMenuList", res.data.menuList);

        // 4. 跳转到首页
        await router.push("/");
      } catch (error) {
        console.log("登录失败：", error);
      }
    },
  },
};
