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
          :rows="6"
          type="textarea"
          placeholder="Please input"
        />
      </el-form-item>
    </el-form>

    <el-button type="success" round @click="submitData">提交</el-button>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { reactive } from "vue";
import axios from "axios";
import { ElMessageBox } from "element-plus";
const radio1 = ref(3);
const radio2 = ref(3);
const radio3 = ref(3);
const radio4 = ref(3);
const value = ref();
const textarea = ref("");
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
  // 这里是您要发送的数据
  const payload = {
    radio1: radio1.value,
    radio2: radio2.value,
    radio3: radio3.value,
    radio4: radio4.value,
    value: value.value,
    textarea: textarea.value,
  };

  try {
    // 使用 axios 发送 POST 请求
    const response = await axios.post("您的服务器地址/api/submit", payload);
    // 处理响应
    console.log(response.data);
    ElMessageBox.alert("提交成功", "成功", {
      confirmButtonText: "确定",
      type: "success",
    });
  } catch (error) {
    // 处理错误
    console.error(error);
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
  height: 500px;
  width: 100%;
}
</style>
