<script setup>
import { ref, reactive, watchEffect } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const tagList = reactive([]);
// const activeTag = ref(route.meta.title);
const activeTag = ref("首页");

watchEffect(() => {
  const hasTag = tagList.find((item) => item.path === route.fullPath);
  activeTag.value = route.fullPath;
  console.log(activeTag.value);
  if (!hasTag) {
    tagList.push({
      name: route.meta.title,
      path: route.fullPath,
    });
  }
});
</script>

<template>
  <el-tabs type="card" closable v-model="activeTag" @tab-remove="removeTab">
    <el-tab-pane
      v-for="item in tagList"
      :key="item.path"
      :label="item.name"
      :name="item.path"
    ></el-tab-pane>
  </el-tabs>
</template>

<style scoped lang="scss"></style>
