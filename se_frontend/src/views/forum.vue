<template>
  <div class="container">
    <div class="left-column">
      <postFilter />
      <postList class="post-list" />
    </div>
    <div class="right-column">
      <myPost />
    </div>
  </div>
</template>

<script setup>
import postFilter from "../component/forum/postFilter.vue";
import postList from "../component/forum/postList.vue";
import myPost from "../component/forum/myPost.vue";
import { onMounted } from "vue";
import { useStore } from "vuex";
const store = useStore();
onMounted(async () => {
  await store.dispatch("fetchForum");
});
</script>

<style scoped lang="scss">
@import "@/style/mixin.scss";
.container {
  display: flex;
  gap: 20px; /* 可选：添加间距 */
  //height: 617.6px; // 固定main-page为除去header的高度
}

.left-column {
  flex: 1; /* 左侧占据2份空间 */
  display: flex;
  flex-direction: column;
  //gap: 20px; /* 可选：组件之间的间距 */
}

.right-column {
  width: 300px; /* 固定右侧列宽度 */
}

.post-list {
  width: 100%; /* 确保占满父容器宽度 */
  height: 100%;
}
</style>
