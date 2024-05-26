<template>
  <ul class="infinite-list" style="overflow: auto">
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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  color: rgb(0, 0, 103);
  border: 1px solid #cfcfcf; /* 添加边框 */
  border-radius: 10px; /* 设置边框为圆角 */
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
.clickable {
  cursor: pointer; /* 这会使鼠标悬停时显示手形图标 */
}
</style>
