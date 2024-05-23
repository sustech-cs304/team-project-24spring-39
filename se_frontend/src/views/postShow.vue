<template>
  <el-row>
    <!-- 左侧内容 -->
    <el-col :span="16">
      <p>userInfo</p>
      <h1>标题</h1>
      <p>内容</p>
      <p>附件</p>
      <div>
        <h3>附件</h3>
        <div v-for="attachment in attachments" :key="attachment.name">
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
            v-for="comment in comments"
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
      <el-form-item label="User" required>
        <el-input v-model="newComment.user"></el-input>
      </el-form-item>
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
const attachments = ref([
  { name: "Attachment 1", url: "path/to/attachment1.pdf" },
  { name: "Attachment 2", url: "path/to/attachment2.docx" },
  // 更多附件
]);

const comments = ref([
  { id: 1, user: "User1", content: "This is the first comment." },
  { id: 2, user: "User2", content: "This is the second comment." },
  { id: 3, user: "User3", content: "This is the third comment." },
  // 更多评论
]);

const commentDialogVisible = ref(false);

const newComment = ref({
  user: "",
  content: "",
});

// const makeComment = () => {
//   console.log("making comm");
//   // eslint-disable-next-line no-const-assign
//   commentDialogVisible = true;
// };

const submitComment = () => {
  if (newComment.value.user && newComment.value.content) {
    comments.value.push({
      id: comments.value.length + 1,
      user: newComment.value.user,
      content: newComment.value.content,
    });
    newComment.value.user = "";
    newComment.value.content = "";
    commentDialogVisible.value = false;
  } else {
    console.log("User and comment content are required");
  }
};
</script>

<style scoped>
.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
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
</style>
