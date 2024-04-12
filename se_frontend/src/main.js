import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

const app = createApp(App);

//滑动验证码
import MakeitCaptcha from "makeit-captcha";
import "makeit-captcha/dist/captcha.min.css";
app.use(MakeitCaptcha);
app.use(store).use(router).mount("#app");
