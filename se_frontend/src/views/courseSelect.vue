<template>
  <el-col class="courseSelect">
    <div>
      <el-card shadow="hover" style="margin-top: 20px">
        <el-form :inline="true" :model="formInline">
          <el-form-item label="课程类别" style="width: 250px">
            <el-select
              v-model="formInline.courseType"
              placeholder="请输入课程类别"
              clearable
            >
              <el-option label="必修" value="BiXiu" />
              <el-option label="选修" value="XuanXiu" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="HandleQuery">查询</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
    <selected-course />
    <course-tabs :type="selectedType" />
  </el-col>
</template>

<script setup>
import { reactive, ref } from "vue";
import CourseTabs from "@/component/courseShow/tabtable.vue";
import SelectedCourse from "@/component/courseShow/selectedCourse.vue";

const selectedType = ref("All");
const formInline = reactive({
  courseType: "",
});
const HandleQuery = () => {
  selectedType.value = formInline.courseType;
  console.log("Querying for type:", selectedType.value);
};
</script>
<style scoped></style>
