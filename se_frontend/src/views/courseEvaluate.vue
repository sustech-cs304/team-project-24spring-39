<script setup>
import CourseList from "./components/CourseList.vue";
import CourseInfo from "./components/CourseInfo.vue";
import RatingComponent from "./components/RatingComponent.vue";
</script>

<template>
  <div id="app">
    <el-container style="height: 100%">
      <el-header>评教系统</el-header>
      <el-container>
        <el-aside width="200px">
          <div style="text-align: center">已选课程</div>
          <CourseList />
        </el-aside>
        <el-main>
          <CourseInfo />
          <div class="rating-container">
            <RatingComponent />
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
body,
html {
  height: 100%;
}

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100%;
}

.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 80px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  line-height: 160px;
}

.rating-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 40%; /* 或者根据需要调整高度 */
}
</style>

<script>
export default {
  name: "App",
  components: {},
};
</script>
