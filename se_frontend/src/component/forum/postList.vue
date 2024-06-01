<template>
  <div class="forum">
    <el-scrollbar height="90%">
      <Post v-for="post in posts" :key="post.id" :post="post" />
    </el-scrollbar>
    <el-pagination background layout="prev, pager, next" :total="1000" />
  </div>
</template>

<script setup>
import { ref } from "vue";
import Post from "./postElement.vue";
import "element-plus/theme-chalk/el-card.css";
import "element-plus/theme-chalk/el-divider.css";

const posts = ref([
  {
    id: 1,
    title: "帖子标题1",
    content: "这是第一个帖子内容。",
    author: "用户1",
    date: "2024-05-22",
  },
  {
    id: 2,
    title: "帖子标题2",
    content: "这是第二个帖子内容。",
    author: "用户2",
    date: "2024-05-21",
  },
  {
    id: 3,
    title: "帖子标题2",
    content: "这是第二个帖子内容。",
    author: "用户2",
    date: "2024-05-21",
  },
  {
    id: 4,
    title: "帖子标题2",
    content: "这是第二个帖子内容。",
    author: "用户2",
    date: "2024-05-21",
  },
  // 更多帖子...
]);
</script>

<style scoped>
.forum {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
</style>
