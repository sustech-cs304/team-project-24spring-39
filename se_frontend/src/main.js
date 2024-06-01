import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
// 引入全局样式
import "./assets/global.css";
// 引入ElementPlus的图标
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
// 引入ElementPlus暗黑模式样式
import "element-plus/theme-chalk/dark/css-vars.css";
// 滑动验证码
import MakeitCaptcha from "makeit-captcha";
import "makeit-captcha/dist/captcha.min.css";
import "element-plus/dist/index.css";
// 引入i18n
import i18n from "@/locales";
// 引入 MotionPlugin 插件来做动画
import { MotionPlugin } from "@vueuse/motion";

const app = createApp(App);

// 注册ElementPlus的图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

app
  .use(store)
  .use(router)
  .use(MakeitCaptcha)
  .use(i18n)
  .use(MotionPlugin)
  .mount("#app");
