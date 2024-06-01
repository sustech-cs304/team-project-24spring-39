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
  //height: 617.6px; // 固定main-page为除去header的高度
}

//.el-scrollbar__wrap el-scrollbar__wrap--hidden-default {
//  height: 100%;
//}
//
.el-scrollbar__view {
  height: 100%;
}

.el-scrollbar__thumb {
  background: deepskyblue;
}
</style>
