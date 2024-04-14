<template>
  <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
    <li
      v-for="course in courses"
      :key="course.id"
      class="infinite-list-item clickable"
    >
      {{ course.name }}
    </li>
  </ul>
  <!-- <div class="course-display" v-if="selectedCourse">
    <h3>{{ selectedCourse.name }}</h3>
  </div> -->
</template>

<script setup>
// 定义一个接口来描述课程对象
import { ref } from "vue";
const courses = ref([
  { id: 1, name: "课程1" },
  { id: 2, name: "课程2" },
  { id: 3, name: "课程3" },
  { id: 4, name: "课程4" },
  { id: 5, name: "课程5" },
  { id: 6, name: "课程6" },
  { id: 7, name: "课程7" },
  { id: 8, name: "课程8" },
  { id: 9, name: "课程9" },
  { id: 10, name: "课程10" },
  { id: 11, name: "课程11" },
  { id: 12, name: "课程12" },
  { id: 13, name: "课程13" },
  { id: 14, name: "课程14" },
  { id: 15, name: "课程15" },
]);

// function selectCourse(course) {
//   selectedCourse.value = course;
// }
const count = ref(0);
const load = () => {
  count.value += 2;
};
</script>

<style>
.infinite-list {
  height: 60%;
  padding: 0;
  margin: 40;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
.clickable {
  cursor: pointer; /* 这会使鼠标悬停时显示手形图标 */
}
</style>
