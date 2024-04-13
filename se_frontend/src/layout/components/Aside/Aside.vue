<script setup>
import Menu from "./Menu/Menu.vue";
</script>

<template>
  <!--  滚动条-->
  <el-scrollbar
    height="100%"
    :wrap-style="{
      background: '#2d3a4b',
    }"
  >
    <!-- logo -->
    <div class="logo">LOGO</div>
    <!-- 导航菜单 -->
    <Menu />
  </el-scrollbar>
</template>

<style scoped lang="scss"></style>
