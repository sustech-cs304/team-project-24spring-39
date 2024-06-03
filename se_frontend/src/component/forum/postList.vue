<template>
  <div class="forum">
    <el-scrollbar height="69vh">
      <Post v-for="post in paginatedPosts" :key="post.id" :post="post" />
    </el-scrollbar>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="totalPosts"
      :page-size="pageSize"
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

const pageSize = ref(8);
const currentPage = ref(1);

const allPosts = computed(() => store.state.forumStore.all_posts);

const totalPosts = computed(() => allPosts.value.length);

const paginatedPosts = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return allPosts.value.slice(start, end);
});

const handlePageChange = async (page) => {
  currentPage.value = page;
};
</script>

<style scoped lang="scss">
.forum {
  max-width: 700px;
  margin: 0 auto;
  padding: 16px;
}
</style>
