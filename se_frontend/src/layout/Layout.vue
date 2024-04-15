<template>
  <el-container class="common-layout">
    <!-- 侧边导航栏 -->
    <el-aside width="200px">
      <Aside />
    </el-aside>
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <Header />
      </el-header>
      <!-- 内容区 -->
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import Header from "./components/Header/Header.vue";
import Aside from "./components/Aside/Aside.vue";
</script>

<style lang="scss">
.common-layout {
  height: 100vh;

  .el-aside {
    transition: width 0.3s ease-out;

    .logo {
      height: 60px;
      line-height: 60px;
      color: #fff;
      font-size: 26px;
      font-weight: bold;
      text-align: center;
    }

    .el-menu-vertical-demo:not(.el-menu--collapse) {
      width: 200px;
      min-height: 400px;
    }
  }

  .el-header {
    display: flex;
    align-items: center;
    margin-bottom: 0px;
    padding: 0;
  }

  .el-main {
    width: 100%;
    /* background-color: #f0f2f5; */
    padding: 0;
    align-items: baseline;
  }
}
</style>
