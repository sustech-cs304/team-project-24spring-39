<script setup></script>

<template>
  <el-scrollbar>
    <div class="main-page">
      <router-view></router-view>
    </div>
  </el-scrollbar>
</template>

<style lang="scss">
.main-page {
  padding: 20px;
  //background-color: #f0f2f5;
}

.el-scrollbar__thumb {
  background: deepskyblue;
}
</style>
