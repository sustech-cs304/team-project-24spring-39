<template>
  <div class="login-container">
    <div :class="['container', { 'show-register': showRegister }]">
      <img
        src="../assets/imgs/login_banner2.jpg"
        alt=""
        @click="toggleForm"
        class="banner-img"
      />
      <div
        class="left_box"
        v-motion
        :initial="{ opacity: 0, x: -100 }"
        :enter="{ opacity: 1, x: 0 }"
        :delay="200"
      >
        <div v-if="showRegister" class="register-form">
          <h3
            class="title"
            v-motion
            :initial="{ opacity: 0, y: 100 }"
            :enter="{ opacity: 1, y: 0, scale: 1 }"
            :variants="{ custom: { scale: 2 } }"
            :hovered="{ scale: 1.2 }"
            :delay="200"
          >
            注册账号
          </h3>
          <div class="form-container">
            <el-form
              ref="registerFormRef"
              :model="registerForm"
              :rules="registerRules"
              label-width="120px"
              class="demo-ruleForm"
              size="large"
              status-icon
            >
              <el-form-item
                label=""
                prop="username"
                v-motion
                :initial="{ opacity: 0, y: 100 }"
                :enter="{ opacity: 1, y: 0, transition: { delay: 300 } }"
              >
                <el-input
                  v-model="registerForm.username"
                  placeholder="请输入用户名"
                >
                  <template #prefix>
                    <el-icon>
                      <Avatar />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item
                label=""
                prop="password"
                v-motion
                :initial="{ opacity: 0, y: 100 }"
                :enter="{ opacity: 1, y: 0, transition: { delay: 400 } }"
              >
                <el-input
                  type="password"
                  show-password
                  placeholder="请输入密码"
                  v-model="registerForm.password"
                >
                  <template #prefix>
                    <el-icon>
                      <Lock />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item
                label=""
                prop="confirmPassword"
                v-motion
                :initial="{ opacity: 0, y: 100 }"
                :enter="{ opacity: 1, y: 0, transition: { delay: 500 } }"
              >
                <el-input
                  type="password"
                  show-password
                  placeholder="请确认密码"
                  v-model="registerForm.confirmPassword"
                >
                  <template #prefix>
                    <el-icon>
                      <Lock />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </el-form>
            <div
              style="margin-top: 20px; width: 100%"
              v-motion
              :initial="{ opacity: 0, y: 100 }"
              :enter="{ opacity: 1, y: 0, transition: { delay: 600 } }"
            >
              <el-button
                style="height: 38px"
                type="primary"
                @click="handleRegister"
                :loading="regBtnLoading"
              >
                注册
              </el-button>
            </div>
          </div>
        </div>
      </div>
      <div class="right_box" v-if="!showRegister">
        <h3
          class="title"
          v-motion
          :initial="{ opacity: 0, y: 100 }"
          :enter="{ opacity: 1, y: 0, scale: 1 }"
          :variants="{ custom: { scale: 2 } }"
          :hovered="{ scale: 1.2 }"
          :delay="200"
        >
          登录账号
        </h3>
        <div class="form-container">
          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            label-width="120px"
            class="demo-ruleForm"
            size="large"
            status-icon
          >
            <el-form-item
              label=""
              prop="username"
              v-motion
              :initial="{ opacity: 0, y: 100 }"
              :enter="{ opacity: 1, y: 0, transition: { delay: 300 } }"
            >
              <el-input v-model="loginForm.username" placeholder="请输入用户名">
                <template #prefix>
                  <el-icon>
                    <Avatar />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item
              label=""
              prop="password"
              v-motion
              :initial="{ opacity: 0, y: 100 }"
              :enter="{ opacity: 1, y: 0, transition: { delay: 400 } }"
            >
              <el-input
                type="password"
                show-password
                placeholder="请输入密码"
                v-model="loginForm.password"
              >
                <template #prefix>
                  <el-icon>
                    <Lock />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
          </el-form>
          <!-- 滑动验证码：https://github.com/lirongtong/miitvip-captcha -->
          <div
            style="width: 100%"
            v-motion
            :initial="{ opacity: 0, y: 100 }"
            :enter="{ opacity: 1, y: 0, transition: { delay: 400 } }"
          >
            <mi-captcha
              ref="captcha"
              width="100%"
              :height="38"
              :radius="6"
              :bgColor="'#fff'"
              :textColor="'#000'"
              :borderColor="'#dcdfe6'"
              :logo="logoImg"
              @success="captchaSuccess"
            />
          </div>
          <!-- 提交表单 -->
          <div
            style="margin-top: 20px; width: 100%"
            v-motion
            :initial="{ opacity: 0, y: 100 }"
            :enter="{ opacity: 1, y: 0, transition: { delay: 500 } }"
          >
            <el-button
              style="height: 38px"
              type="primary"
              @click="handleLogin"
              :loading="logBtnLoading"
            >
              {{ "signIn" }}
            </el-button>
          </div>
        </div>
        <!-- 国际化 -->
        <!--        <Language class="language" />-->
        <!-- 主题切换 -->
        <!--        <Theme class="theme" />-->
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, toRaw } from "vue";
import { Avatar, Lock } from "@element-plus/icons-vue";
import { useStore } from "vuex";
// import { login } from "@/api/user";
import useMessage from "@/hooks/useMessage";

const showRegister = ref(false);

// 切换表单
function toggleForm() {
  showRegister.value = !showRegister.value;
}

const logoImg = require("@/assets/imgs/logo.png");

// 获取到vuex的store
const store = useStore();

const { ElMessage } = useMessage();

///////////////////////////////////////////////////////////// 登录表单
const loginFormRef = ref(null);
const loginForm = reactive({
  username: "12345678",
  password: "password1",
  captchaSuccess: true,
});
// 登录按钮的加载loading
const logBtnLoading = ref(false);
// 登录表单的校验规则
const loginRules = reactive({
  username: [{ required: true, message: "userError", trigger: "blur" }],
  password: [
    { required: true, message: "PWError", trigger: "blur" },
    { min: 3, message: "length > 3", trigger: "blur" },
  ],
});
// 滑动验证码校验成功
function captchaSuccess() {
  loginForm.captchaSuccess = true;
}
// 处理登录
async function handleLogin() {
  if (!loginForm.captchaSuccess) {
    ElMessage.error("请先完成滑动验证");
    return;
  }
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        logBtnLoading.value = true;
        // const res = await login(loginForm)
        // 用通过vuex发送网络请求
        const res = await store.dispatch("handleLogin", toRaw(loginForm));
        console.log("res: " + res);
      } catch (error) {
        ElMessage.error("登录失败, 请稍后重试");
      } finally {
        logBtnLoading.value = false;
      }
    } else {
      ElMessage.warning("用户名或密码错误");
    }
  });
}

///////////////////////////////////////////////////////////// 注册表单
// 注册表单ref
const registerFormRef = ref(null);
// 注册表单数据
const registerForm = reactive({
  username: "",
  password: "",
  confirmPassword: "",
});

// 注册按钮的加载loading
const regBtnLoading = ref(false);

// 注册表单的校验规则
const registerRules = reactive({
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 3, message: "密码长度应大于3位", trigger: "blur" },
  ],
  confirmPassword: [
    { required: true, message: "请确认密码", trigger: "blur" },
    { validator: validateConfirmPassword, trigger: "blur" },
  ],
});

// 自定义校验器
function validateConfirmPassword(rule, value, callback) {
  if (value !== registerForm.password) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
}

// 处理注册
async function handleRegister() {
  await registerFormRef.value.validate(async (valid, fields) => {
    if (valid) {
      try {
        // 在此添加你的注册逻辑，例如通过vuex发送网络请求
        const res = await store.dispatch("handleRegister", toRaw(registerForm));
        console.log("注册成功: " + res);
      } catch (error) {
        console.log("注册失败: ", error);
      }
    } else {
      console.log("注册表单验证失败!", fields);
    }
  });
}
</script>

<style lang="scss" scoped>
//@import "@/style/mixin.scss";

.login-container {
  height: 100vh;
  width: 100%;
  background-color: #2d3a4b;
  overflow: hidden;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;

  .container {
    position: relative; /* 使子元素的绝对定位相对于 .container */
    /* @include bg_color(); */
    background-color: var(--el-bg-color);
    width: 670px;
    height: 400px;
    display: flex;
    border-radius: 6px;
    overflow: hidden;
    box-shadow: 0 5px 20px 2px rgba(255, 255, 255, 0.1);

    .banner-img {
      height: 100%;
      width: 50%;
      position: absolute;
      //top: 0;
      //left: 0;
      cursor: pointer;
      transition: transform 0.5s ease;
      z-index: 2;
      transform: translateX(0);
    }

    &.show-register .banner-img {
      transform: translateX(100%);
    }

    .left_box {
      width: 50%;
      //padding: 20px;
      //box-sizing: border-box; // 使padding包含在宽度和高度之内
      position: relative;
      z-index: 1;

      .register-form {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        padding: 20px;
        box-sizing: border-box; // 使padding包含在宽度和高度之内
        height: 100%;
        background-color: white;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        transition: opacity 0.5s ease;

        .title {
          font-size: 26px;
          /* color: #333; */
          //@include text_color();
          margin: 20px auto 40px auto;
          text-align: center;
          font-weight: 700;
        }

        .form-container {
          display: flex;
          justify-content: center;
          flex-wrap: wrap;

          .el-form {
            width: 100%;

            ::v-deep(.el-form-item__content) {
              margin-left: 0 !important;
            }
          }

          .el-button {
            width: 100%;
          }
        }
      }
    }

    .right_box {
      width: 50%;
      padding: 20px;
      box-sizing: border-box; // 使padding包含在宽度和高度之内
      position: relative;
      z-index: 1;

      .title {
        font-size: 26px;
        /* color: #333; */
        //@include text_color();
        margin: 20px auto 40px auto;
        text-align: center;
        font-weight: 700;
      }

      .form-container {
        display: flex;
        justify-content: center;
        flex-wrap: wrap;

        .el-form {
          width: 100%;

          /* vue3中的样式穿透 */
          ::v-deep(.el-form-item__content) {
            margin-left: 0 !important;
          }
        }

        .el-button {
          width: 100%;
        }
      }

      .language {
        width: 38px;
        height: 40px;
        position: absolute;
        right: 0;
        top: 0;
      }

      .theme {
        width: 38px;
        height: 40px;
        position: absolute;
        right: 38px;
        top: 0;
      }
    }
  }
}
</style>
