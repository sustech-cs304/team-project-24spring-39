<template>
  <el-card class="post-card">
    <h2>{{ post.title }}</h2>
    <p>{{ post.content }}</p>
    <el-divider></el-divider>
    <small>作者: {{ post.author }} | 发布于: {{ post.date }}</small>
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
}

.button-container {
  position: absolute;
  bottom: 10px;
  right: 10px;
}
</style>
