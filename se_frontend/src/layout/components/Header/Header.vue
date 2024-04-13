<script setup>
import ExpandToggle from "@/layout/components/Header/component/ExpandToggle.vue";
import Breadcrumb from "@/layout/components/Header/component/Breadcrumb.vue";
</script>

<template>
  <div class="header">
    <!-- 左侧 -->
    <div class="header_left">
      <!--      菜单收缩按钮-->
      <ExpandToggle />
      <!--      面包屑-->
      <Breadcrumb />
    </div>
    <!-- 右侧 -->
    <div class="header_right"></div>
    <!-- tag标签 -->
    <div class="header_tag"></div>
  </div>
</template>

<style lang="scss" scoped>
/* @import "@/style/mixin.scss"; */

.header {
  display: flex;
  justify-content: space-between;
  width: 100%;
  flex-wrap: wrap;
  position: relative;
  padding: 0 20px;

  .header_left {
    display: flex;
    align-items: center;
  }

  .header_right {
    display: flex;
    align-items: center;
  }

  .header_tag {
    position: absolute;
    left: 0;
    bottom: -53px;
    width: calc(100% - 10px);
    height: 24px;
    /* @include bg_color(); */
    background-color: var(--el-bg-color);
    border-bottom: 1px solid var(--el-border-color);
    border-top: 1px solid var(--el-border-color);
    display: flex;
    align-items: center;
    padding: 5px;
    /* box-shadow: 0 1px 3px 0 rgb(0 0 0 / 12%), 0 0 3px 0 rgb(0 0 0 / 4%); */
  }
}
</style>
