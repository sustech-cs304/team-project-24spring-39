<template>
  <!-- 只有1级 -->
  <el-menu-item v-if="!hasChildren(data)" :index="data.url || data.id + ''">
    <el-icon v-if="data.icon">
      <component :is="data.icon || 'el-icon-menu'" />
    </el-icon>
    <template #title>
      <span>{{ data.title }}</span>
    </template>
  </el-menu-item>
  <!-- 多级 -->
  <el-sub-menu v-else :index="data.url || data.id + ''">
    <template #title>
      <el-icon v-if="data.icon">
        <component :is="data.icon || 'el-icon-menu'" />
      </el-icon>
      <span>{{ data.title }}</span>
    </template>
    <!-- 递归组件 -->
    <template v-if="data.children">
      <!-- 有子节点的时候才渲染 -->
      <MenuItem
        v-for="child in data.children"
        :key="child.url"
        :data="child"
      ></MenuItem>
    </template>
  </el-sub-menu>
</template>

<script>
export default {
  name: "MenuItem",
  props: {
    data: {
      type: Object,
      required: true,
      default: () => ({}),
    },
  },
  setup() {
    function hasChildren(item) {
      return item.children && item.children.length;
    }

    return {
      hasChildren,
    };
  },
};
</script>

<style scoped lang="scss"></style>
