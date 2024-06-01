<template>
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="专业：">
      <el-select
        v-model="formInline.major"
        placeholder="选择"
        clearable
        @change="onMajorChange"
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
      <el-select v-model="formInline.course" placeholder="选择" clearable>
        <el-option
          v-for="course in filteredCourses"
          :key="course"
          :label="course"
          :value="course"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
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
};

const onSubmit = async () => {
  console.log("Selected Major:", formInline.value.major);
  console.log("Selected Course:", formInline.value.course);
  store.commit(
    "setMajorCourses",
    formInline.value.major,
    formInline.value.course
  );
  await store.dispatch("applyFilter", store.state.forumStore.filter_info);
};
</script>

<style>
.demo-form-inline .el-select {
  --el-select-width: 170px;
}
</style>
