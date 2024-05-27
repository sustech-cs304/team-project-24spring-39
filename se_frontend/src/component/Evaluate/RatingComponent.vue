<template>
  <div class="radio-container">
    <span>课程难度</span>
    <el-radio-group v-model="radio1">
      <el-radio :value="3">低</el-radio>
      <el-radio :value="6">中</el-radio>
      <el-radio :value="9">高</el-radio>
    </el-radio-group>
    <span>评分宽松度</span>
    <el-radio-group v-model="radio2">
      <el-radio :value="3">低</el-radio>
      <el-radio :value="6">中</el-radio>
      <el-radio :value="9">高</el-radio>
    </el-radio-group>
    <span>工作量</span>
    <el-radio-group v-model="radio3">
      <el-radio :value="3">低</el-radio>
      <el-radio :value="6">中</el-radio>
      <el-radio :value="9">高</el-radio>
    </el-radio-group>
    <span>整体收益</span>
    <el-radio-group v-model="radio4">
      <el-radio :value="3">低</el-radio>
      <el-radio :value="6">中</el-radio>
      <el-radio :value="9">高</el-radio>
    </el-radio-group>
    <span>总体评价</span>
    <el-rate v-model="value" allow-half />
    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="留言">
        <el-input
          v-model="textarea"
          style="width: 240px"
          :rows="4"
          type="textarea"
          placeholder="Please input"
        />
      </el-form-item>
    </el-form>

    <el-button
      type="success"
      round
      @click="submitData"
      :disabled="store.state.evalStore.selectedCourse.id == -1"
      >提交</el-button
    >
  </div>
</template>

<script setup>
import { ref } from "vue";
import { reactive } from "vue";
import { computed } from "vue";
import { useStore } from "vuex";
import { submitRatingInside } from "@/api/eval_api";
import { ElMessageBox } from "element-plus";
const radio1 = ref(3);
const radio2 = ref(3);
const radio3 = ref(3);
const radio4 = ref(3);
const value = ref();
const textarea = ref("");
const store = useStore();
const selectedCourseId = computed(
  () => store.state.evalStore.selectedCourse.id
);
// do not use same name with ref
const form = reactive({
  name: "",
  region: "",
  date1: "",
  date2: "",
  delivery: false,
  type: [],
  resource: "",
  desc: "",
});
const submitData = async () => {
  console.log(store.state.evalStore.selectedCourse.id);
  const payload = {
    courseID: selectedCourseId,
    difficulty: radio1.value,
    grading_policy: radio2.value,
    workload: radio3.value,
    learning_gains: radio4.value,
    overall: value.value,
    comment: textarea.value,
  };
  try {
    // console.log(payload);
    await submitRatingInside(payload);
    ElMessageBox.alert("提交成功", "成功", {
      confirmButtonText: "确定",
      type: "success",
    });
    // console.log(store.state.evalStore.courses[0]);
    store.commit("deleteCourse", store.state.evalStore.selectedCourse.id);
    // console.log(store.state.evalStore.courses[0]);
    // store.commit("setSelectedCourse", store.state.evalStore.courses[0]);
  } catch (error) {
    console.error("Error:", error);
    ElMessageBox.alert("提交失败", "错误", {
      confirmButtonText: "确定",
      type: "error",
    });
  }
};
</script>

<style scoped>
.radio-container {
  display: flex;
  flex-direction: column;
}

span {
  display: block;
  margin-bottom: 5px; /* 为文本和单选按钮组添加一些间距 */
  font-weight: bold; /* 可选：使文本加粗 */
  font-size: 12px; /* 调整字体大小 */
  line-height: 1.2; /* 调整行高 */
}

.el-input {
  height: 100px;
  width: 100%;
}
</style>
