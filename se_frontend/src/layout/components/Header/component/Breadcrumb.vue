<script setup>
import { ArrowRight } from "@element-plus/icons-vue";
</script>

<template>
  <el-breadcrumb :separator-icon="ArrowRight">
    <transition-group appear name="breadcrumb">
      <el-breadcrumb-item v-for="item in $route.meta.breadcrumb" :key="item">
        {{ item }}
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<style scoped lang="scss">
// 面包屑动画
.breadcrumb-enter-active,
.breadcrumb-leave-active {
  transition: all 0.4s;
}

.breadcrumb-enter-from,
.breadcrumb-leave-active {
  opacity: 0;
  transform: translateX(20px);
}

.breadcrumb-leave-active {
  position: absolute;
}
</style>
