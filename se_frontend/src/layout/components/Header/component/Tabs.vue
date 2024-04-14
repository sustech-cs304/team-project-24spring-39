<script setup>
import { ref, reactive, watch } from "vue";
import { useRoute } from "vue-router";
import router from "@/router";

const route = useRoute();

const tagList = reactive([]);
const activeTag = ref(route.fullPath);

// 这里只能用 watch, 不能用 watchEffect（试过了）
watch(
  () => route,
  (to) => {
    const hasTag = tagList.find((item) => item.path === to.fullPath);
    activeTag.value = to.fullPath;
    if (!hasTag) {
      tagList.push({
        name: to.meta.title,
        path: to.fullPath,
      });
    }
  },
  {
    deep: true,
    immediate: true,
  }
);

function handleTabClick(tabContext) {
  router.push(tabContext.props.name);
}

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 4.0
 * usage: 问如何实现标签的删除，采用经过调整的代码
 */
function removeTab(targetName) {
  if (tagList.length === 1 && activeTag.value === "/") {
    // 如果只有一个标签, 且当前激活标签是首页, 则不允许删除
    return;
  }

  const tabs = tagList.filter((tab) => tab.path !== targetName);
  tagList.splice(0, tagList.length, ...tabs);
  if (activeTag.value === targetName) {
    if (tabs.length > 0) {
      router.push(tabs[tabs.length - 1].path); // 当删除的是当前激活标签时, 跳转至列表中的最后一个
    } else {
      router.push("/"); // 如果没有标签, 重定向到首页
    }
  }
}
</script>

<template>
  <el-tabs
    type="card"
    closable
    v-model="activeTag"
    @tab-click="handleTabClick"
    @tab-remove="removeTab"
  >
    <el-tab-pane
      v-for="item in tagList"
      :key="item.path"
      :label="item.name"
      :name="item.path"
    ></el-tab-pane>
  </el-tabs>
</template>

<style scoped lang="scss"></style>
