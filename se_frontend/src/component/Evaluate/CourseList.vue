<template>
  <ul class="infinite-list">
    <li
      v-for="course in courses"
      :key="course.id"
      class="infinite-list-item clickable"
      @click="selectCourse(course)"
    >
      {{ course.name }}
    </li>
  </ul>
</template>

<script setup>
// 定义一个接口来描述课程对象
// import { ref } from "vue";
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();
const courses = computed(() => store.state.evalStore.courses);

function selectCourse(course) {
  store.commit("setSelectedCourse", course);
}
</script>

<style scoped>
.infinite-list {
  height: 60%;
  padding: 5px;
  //margin: 40px;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-size: 16px;
  color: var(--el-color-primary); /* 使用 Element Plus 的主颜色 */
  font-weight: bold; /* 将字体加粗 */
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
.clickable {
  cursor: pointer; /* 这会使鼠标悬停时显示手形图标 */
}
</style>
