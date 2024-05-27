<template>
  <el-container class="layout">
    <!-- 侧边导航栏 -->
    <el-aside :width="$store.state.layoutStore.isExpand ? '200px' : '64px'">
      <Aside />
    </el-aside>
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <Header />
      </el-header>
      <!-- 内容区 -->
      <el-main>
        <Main />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import Header from "./components/Header/Header.vue";
import Aside from "./components/Aside/Aside.vue";
import Main from "./components/Main.vue";
</script>

<style lang="scss">
@import "@/style/mixin.scss";
.layout {
  height: 100vh;
  display: flex;

  .el-aside {
    // 侧边栏收缩过渡效果
    transition: width 0.3s ease-out;

    .logo {
      height: 60px;
      line-height: 60px;
      color: #fff;
      font-size: 26px;
      font-weight: bold;
      text-align: center;
    }
  }

  .el-header {
    display: flex;
    align-items: center;
    margin-bottom: 0;
    padding: 0;
  }

  .el-main {
    width: 100%;
    //background-color: #f0f2f5;
    @include bg_color();
    @include text_color();
    padding: 0;
    margin-top: 28px;
    align-items: baseline;
    flex-grow: 1;
  }
}
</style>
