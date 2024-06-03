<template>
  <el-card class="post-card">
    <h2 class="title">{{ post.title }}</h2>
    <p class="content">{{ post.content }}</p>
    <el-divider></el-divider>
    <small>作者: {{ post.name }} | 发布于: {{ post.date }}</small>
    <div class="button-container">
      <el-button type="primary" @click="goToPost(post.id)">查看详情</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const store = useStore();
const router = useRouter();
// eslint-disable-next-line no-unused-vars
const props = defineProps({
  post: {
    type: Object,
    required: true,
  },
});

const goToPost = (postId) => {
  store.commit("setSelectedPost", postId);
  router.push({ name: "postShow", params: { id: postId } });
};
</script>

<style scoped>
.post-card {
  margin-bottom: 20px;
  position: relative;
  padding-bottom: 40px; /* Ensure there's space for the button */
  height: 150px; /* 设置固定高度 */
  overflow: hidden; /* 防止内容溢出 */
}

.button-container {
  position: absolute;
  bottom: 10px;
  right: 10px;
}

.title {
  flex-shrink: 0; /* 确保标题不会缩小 */
  margin-bottom: 10px;
  white-space: nowrap; /* 防止标题换行 */
  overflow: hidden; /* 隐藏溢出的内容 */
  text-overflow: ellipsis; /* 使用省略号表示被截断的文本 */
}

.content {
  flex: 1; /* 使内容区域占据剩余空间 */
  overflow: hidden; /* 隐藏溢出的内容 */
  text-overflow: ellipsis; /* 使用省略号表示被截断的文本 */
  white-space: nowrap; /* 防止内容换行 */
}
</style>
