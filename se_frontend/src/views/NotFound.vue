<template>
  <div class="errPage">
    <div style="margin-top: 100px">
      <Lottie
        width="300px"
        height="300px"
        src="https://lottie.host/c6d4c13d-1e5e-426a-976f-be5b447eb1f6/133r7nkgAl.json"
      ></Lottie>
      <div class="info">抱歉，您访问的页面不存在。</div>
      <div class="goback">
        <el-button @click="goBack">返回上一页</el-button>
        <el-button type="primary" @click="goHome"> 返回首页 </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import Lottie from "../component/Lottie";

const router = useRouter();

function goHome() {
  router.push("/");
}

function goBack() {
  router.go(-1);
}
</script>

<style lang="scss" scoped>
.errPage {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;

  .info {
    text-align: center;
    font-size: 16px;
    padding: 20px 0;
    color: #999;
  }

  .goback {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
