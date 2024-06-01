<template>
  <div class="forum">
    <el-scrollbar height="90%">
      <Post v-for="post in posts" :key="post.id" :post="post" />
    </el-scrollbar>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="post_num"
      :current-page="currentPage"
      @current-change="handlePageChange"
    />
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import Post from "./postElement.vue";
import { useStore } from "vuex";
import "element-plus/theme-chalk/el-card.css";
import "element-plus/theme-chalk/el-divider.css";
const store = useStore();
const posts = computed(() => store.state.forumStore.all_posts);
const post_num = computed(() => store.state.forumStore.post_num);
const currentPage = ref(1);
const handlePageChange = async (page) => {
  currentPage.value = page;
  store.commit(
    "set_start_end",
    (currentPage.value - 1) * 8,
    (currentPage.value - 1) * 8
  );
  await store.dispatch("applyFilter", store.state.forumStore.filter_info);
};
</script>

<style scoped>
.forum {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
</style>
