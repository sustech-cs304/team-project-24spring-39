<template>
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="专业：" class="center-item">
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
    <el-form-item label="课程：" class="center-item">
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
    <!-- 自动扩展的空白元素 -->
    <div class="spacer"></div>
    <el-form-item class="center-item">
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();
const majors = computed(() => store.state.forumStore.major_courses);
const formInline = ref({
  major: "",
  course: "",
});

const filteredCourses = computed(() => {
  const selectedMajor = majors.value.find(
    (major) => major.name === formInline.value.major
  );
  return selectedMajor ? selectedMajor.courses : [];
});

const onMajorChange = () => {
  formInline.value.course = ""; // Reset the course selection when the major changes
  console.log("form", store.state.forumStore.filter_info);
};

// const onMajorClear = () => {
//   formInline.value.course = "";
// };
const onCourseChange = () => {
  console.log("form", store.state.forumStore.filter_info);
};

const onSubmit = async () => {
  console.log("Selected Major:", formInline.value.major);
  console.log("Selected Course:", formInline.value.course);
  console.log("form", store.state.forumStore.filter_info);
  store.commit("setMajorCourses", formInline.value);
  console.log("form", store.state.forumStore.filter_info);
  console.log("Selected Major:", formInline.value.major);
  console.log("Selected Course:", formInline.value.course);
  await store.dispatch("applyFilter", store.state.forumStore.filter_info);
};
</script>

<style scoped lang="scss">
@import "@/style/mixin.scss";
.demo-form-inline .el-select {
  --el-select-width: 170px;
}

.demo-form-inline {
  @include block_bg_color();
  display: flex;
  align-items: center;
  gap: 20px; /* 可选：添加间距 */
  padding: 12px;

  .spacer {
    flex: 1;
  }
}

.center-item {
  margin: 0;
  display: flex;
  align-items: center;
}
</style>
