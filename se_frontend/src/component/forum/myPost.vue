<template>
  <el-row class="header-row">
    <span>我的发布</span>
    <el-button type="primary" @click="dialogVisible = true">+</el-button>
  </el-row>

  <el-scrollbar height="100%">
    <el-card
      v-for="post in posts"
      :key="post.id"
      class="scrollbar-demo-item"
      @click="goToPost(post.id)"
    >
      <div class="card-header">
        <h3 class="card-title">{{ post.title }}</h3>
        <el-button
          type="danger"
          icon="Delete"
          circle
          size="small"
          @click.stop="confirmDelete(post.id)"
        ></el-button>
      </div>
      <p class="card-content">{{ post.content }}</p>
    </el-card>
  </el-scrollbar>

  <el-dialog v-model="dialogVisible" title="创建新帖子">
    <el-form ref="postForm" :model="form" label-width="80px">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="专业：">
          <el-select
            v-model="formInline.major"
            placeholder="选择"
            clearable
            @change="onMajorChange"
            @clear="onMajorClear"
          >
            <el-option
              v-for="major in majors"
              :key="major.name"
              :label="major.name"
              :value="major.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="课程：">
          <el-select
            v-model="formInline.course"
            placeholder="选择"
            clearable
            @change="onCourseChange"
          >
            <el-option
              v-for="course in filteredCourses"
              :key="course.id"
              :label="course.name"
              :value="course.name"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <el-form-item label="标题">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <el-input type="textarea" v-model="form.content"></el-input>
      </el-form-item>
      <el-form-item label="附件">
        <el-upload
          action=""
          list-type="text"
          :on-change="handleFileChange"
          :auto-upload="false"
        >
          <el-button type="primary">点击上传</el-button>
          <template #tip>
            <div class="el-upload__tip">
              只能上传不超过10M的文件，且不超过5个
            </div>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitPost">提交</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { ElMessageBox } from "element-plus";
import { createPostInside } from "@/api/forum_api";
const store = useStore();
const posts = computed(() => store.state.forumStore.my_posts);
const router = useRouter();
const goToPost = (postId) => {
  store.commit("setSelectedPost", postId);
  router.push({ name: "postShow", params: { id: postId } });
};
const majors = computed(() => store.state.forumStore.major_courses);
const formInline = ref({
  major: "",
  course: "",
});
const dialogVisible = ref(false);
const form = ref({
  title: "",
  content: "",
  file: null,
});
const filteredCourses = computed(() => {
  const selectedMajor = majors.value.find(
    (major) => major.name === formInline.value.major
  );
  return selectedMajor ? selectedMajor.courses : [];
});

const handleFileChange = (file) => {
  form.value.file = file.raw;
};

const submitPost = async () => {
  const formData = new FormData();
  formData.append("title", form.value.title);
  formData.append("content", form.value.content);
  formData.append("majorTag", "aaa");
  formData.append("courseTag", formInline.value.course);
  formData.append("file", form.value.file);

  // 打印 FormData 内容
  for (let pair of formData.entries()) {
    console.log(pair[0] + ":", pair[1]);
  }
  try {
    await createPostInside(formData);
    console.log("Upload successful:");
  } catch (error) {
    // console.error("Error:", error);
    ElMessageBox.alert("发帖失败", "错误", {
      confirmButtonText: "确定",
      type: "error",
    });
    // console.error("Upload failed:", error);
  }
  dialogVisible.value = false;
  // 清空表单数据
  form.value.title = "";
  form.value.content = "";
  form.value.file = null;
};

const confirmDelete = async (postId) => {
  try {
    await ElMessageBox.confirm("此操作将永久删除该帖子, 是否继续?", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    });
    await store.dispatch("deletePost", postId);
  } catch (error) {
    if (error !== "cancel") {
      console.error("Failed to delete post:", error);
    }
  }
};

const onMajorChange = () => {
  formInline.value.course = ""; // Reset the course selection when the major changes
  console.log("form", store.state.forumStore.filter_info);
};

const onMajorClear = () => {
  formInline.value.course = "";
};

const onCourseChange = () => {
  console.log("form", store.state.forumStore.filter_info);
};
</script>

<style scoped>
.header-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.scrollbar-demo-item {
  display: flex;
  /* align-items: center;
  justify-content: center; */
  height: 150px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
  cursor: pointer;
  position: relative;
}

.dialog-footer {
  text-align: right;
}

.card-header {
  display: flex;
  width: 250px;
  /* justify-content: space-between; */
}
.card-title {
  flex: 1;
  margin: 0;
  margin-right: 10px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: left; /* 确保标题靠左对齐 */
  width: 150px;
}
.card-content {
  text-align: left; /* 确保内容靠左对齐 */
  margin: 0; /* 移除默认的段落外边距 */
}
.delete-button {
  position: absolute;
  top: 10px;
  right: 10px;
}
</style>
