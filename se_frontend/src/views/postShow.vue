<template>
  <el-row>
    <!-- 左侧内容 -->
    <el-col :span="16">
      <div class="background">
        <p>{{ selectedPostAuthor }}</p>
        <p>{{ selectedPostDate }}</p>
        <h1>{{ selectedPostTitle }}</h1>
        <p>{{ selectedPostContent }}</p>
        <h3>附件</h3>
        <div
          v-for="attachment in selectedPostAttachments"
          :key="attachment.name"
        >
          <el-link :href="attachment.url" target="_blank" download>
            {{ attachment.name }}
          </el-link>
        </div>
      </div>
    </el-col>

    <!-- 右侧评论 -->
    <el-col :span="8">
      <div class="comment-section">
        <div class="comment-header">
          <span>评论</span>
          <el-button type="primary" @click="commentDialogVisible = true"
            >+</el-button
          >
        </div>
        <el-scrollbar height="400px">
          <div
            v-for="comment in selectedPostComments"
            :key="comment.id"
            class="scrollbar-demo-item"
          >
            <strong>{{ comment.user }}:</strong> {{ comment.content }}
          </div>
        </el-scrollbar>
      </div>
    </el-col>
  </el-row>

  <!-- 评论弹窗 -->
  <el-dialog v-model="commentDialogVisible" title="Add Comment">
    <el-form :model="newComment">
      <el-form-item label="Comment" required>
        <el-input v-model="newComment.content" type="textarea"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="commentDialogVisible = false">Cancel</el-button>
      <el-button type="primary" @click="submitComment">Submit</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import { useStore } from "vuex";
import { computed } from "vue";
import { ElMessageBox } from "element-plus";
const store = useStore();

const selectedPostTitle = computed(
  () => store.state.forumStore.selectedPost.title
);
const selectedPostContent = computed(
  () => store.state.forumStore.selectedPost.content
);
const selectedPostAuthor = computed(
  () => store.state.forumStore.selectedPost.author
);
const selectedPostDate = computed(
  () => store.state.forumStore.selectedPost.date
);
const selectedPostAttachments = computed(
  () => store.state.forumStore.selectedPost.attachments
);
const selectedPostComments = computed(
  () => store.state.forumStore.selectedPost.comments
);
// const attachments = ref([
//   { name: "Attachment 1", url: "path/to/attachment1.pdf" },
//   { name: "Attachment 2", url: "path/to/attachment2.docx" },
//   // 更多附件
// ]);

// const comments = ref([
//   { id: 1, user: "User1", content: "This is the first comment." },
//   { id: 2, user: "User2", content: "This is the second comment." },
//   { id: 3, user: "User3", content: "This is the third comment." },
//   // 更多评论
// ]);

const commentDialogVisible = ref(false);

const newComment = ref({
  postId: store.state.forumStore.selectedPost.id,
  user: store.state.userStore.userInfo.name,
  content: "",
});

const submitComment = async () => {
  try {
    await store.dispatch("makeComment", newComment.value);
    console.log(newComment.value);
    commentDialogVisible.value = false;
  } catch (error) {
    console.error("Error:", error);
    ElMessageBox.alert("评论失败？", "错误", {
      confirmButtonText: "确定",
      type: "error",
    });
  }
};
</script>

<style scoped>
.scrollbar-demo-item {
  padding: 10px;
  margin: 5px 0;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
  border-radius: 4px;
  word-wrap: break-word; /* 确保长单词自动换行 */
}

.comment-section {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.comment-header span {
  font-size: 18px;
  font-weight: bold;
}

.background {
  background: white;
  margin: 20px;
}
</style>
