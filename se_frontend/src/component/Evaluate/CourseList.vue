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
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();
const courses = computed(() => store.state.userStore.courses);

// function selectCourse(course) {
//   selectedCourse.value = course;
// }
const count = ref(0);
const load = () => {
  count.value += 2;
};
</script>

<style scoped>
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
