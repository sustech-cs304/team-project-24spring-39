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

    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="留言">
        <el-input v-model="form.name" />
      </el-form-item>
    </el-form>

    <el-button type="success" round>提交</el-button>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { reactive } from "vue";

const radio1 = ref(3);
const radio2 = ref(3);
const radio3 = ref(3);
const radio4 = ref(3);

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
</style>
