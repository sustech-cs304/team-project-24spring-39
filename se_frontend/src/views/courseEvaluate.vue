<script setup>
import CourseList from "../component/Evaluate/CourseList.vue";
import CourseInfo from "../component/Evaluate/CourseInfo.vue";
import RatingComponent from "../component/Evaluate/RatingComponent.vue";
</script>

<template>
  <div id="app">
    <el-container style="height: 100%">
      <el-container>
        <el-scrollbar height="100%">
          <div style="width: 220px">
            <div style="text-align: center; margin-top: 30px; font-size: 18px">
              已选课程
            </div>
            <CourseList style="height: 100%" />
          </div>
        </el-scrollbar>
        <div class="main-container">
          <CourseInfo class="courseInfo" />
          <RatingComponent class="rating-container" />
        </div>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="scss">
@import "@/style/mixin.scss";
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

.el-scrollbar {
  //background-color: #d3dce6;
  //color: #333;
  @include block_bg_color();
}

.main-container {
  //background-color: #e9eef3;
  //color: #333;
  //line-height: 160px;
  // 使子元素水平居中
  height: 100%;
  padding: 16px;
  width: calc(100% - 220px);
  display: flex;
  flex-direction: column; /* 垂直排列子元素 */
  align-items: center; /* 水平居中子元素 */
  justify-content: center;

  .courseInfo {
    width: 80%;
    @include block_bg_color();
  }

  .rating-container {
    @include block_bg_color();
    width: 80%;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 70%; /* 或者根据需要调整高度 */
  }
}
</style>

<script>
export default {
  name: "App",
  components: {},
};
</script>
