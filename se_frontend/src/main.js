import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
// 引入ElementPlus的图标
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
//滑动验证码
import MakeitCaptcha from "makeit-captcha";
import "makeit-captcha/dist/captcha.min.css";
import "element-plus/dist/index.css";

const app = createApp(App);

// 注册ElementPlus的图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

app.use(store).use(router).use(MakeitCaptcha).mount("#app");
