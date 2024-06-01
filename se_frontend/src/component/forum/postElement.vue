<template>
  <el-card class="post-card">
    <h2>{{ post.title }}</h2>
    <p>{{ post.content }}</p>
    <el-divider></el-divider>
    <small>作者: {{ post.author }} | 发布于: {{ post.date }}</small>
    <div class="button-container">
      <router-link :to="{ name: 'postShow', params: { id: post.id } }">
        <el-button type="primary">查看详情</el-button>
      </router-link>
    </div>
  </el-card>
</template>

<script setup>
import { defineProps } from "vue";

// eslint-disable-next-line no-unused-vars
const props = defineProps({
  post: {
    type: Object,
    required: true,
  },
});
</script>

<style scoped>
.post-card {
  margin-bottom: 20px;
  position: relative;
  padding-bottom: 40px; /* Ensure there's space for the button */
}

.button-container {
  position: absolute;
  bottom: 10px;
  right: 10px;
}
</style>
