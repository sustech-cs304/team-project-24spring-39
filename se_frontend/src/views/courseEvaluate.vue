<script setup>
import CourseList from "../component/Evaluate/CourseList.vue";
import CourseInfo from "../component/Evaluate/CourseInfo.vue";
import RatingComponent from "../component/Evaluate/RatingComponent.vue";
</script>

<template>
  <div id="app">
    <el-container style="height: 100%">
      <el-container>
        <el-aside width="200px">
          <div style="text-align: center; margin-top: 30px; font-size: 18px">
            已选课程
          </div>
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

<style scoped>
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
  height: 60%; /* 或者根据需要调整高度 */
}
</style>

<script>
export default {
  name: "App",
  components: {},
};
</script>
