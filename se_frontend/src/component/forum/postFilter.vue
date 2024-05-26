<template>
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="专业：">
      <el-select v-model="formInline.region" placeholder="选择" clearable>
        <el-option label="Zone one" value="shanghai" />
        <el-option label="Zone two" value="beijing" />
      </el-select>
    </el-form-item>
    <el-form-item label="课程：">
      <el-select v-model="formInline.region" placeholder="选择" clearable>
        <el-option label="Zone one" value="shanghai" />
        <el-option label="Zone two" value="beijing" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive } from "vue";

const formInline = reactive({
  user: "",
  region: "",
  date: "",
});

const onSubmit = () => {
  console.log("submit!");
};
</script>

<style>
.demo-form-inline .el-select {
  --el-select-width: 170px;
}
</style>
