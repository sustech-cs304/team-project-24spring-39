<template>
  <div>
    <span class="course-name">{{ selectedCourseName }}</span>
    <p
      class="course-intro"
      :style="{ boxShadow: shadowStyle, padding: '1rem' }"
    >
      <span class="label">简介：</span>{{ selectedCourseIntro }}
    </p>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useStore } from "vuex";
import { ref } from "vue";
const store = useStore();

const selectedCourseName = computed(
  () => store.state.userStore.selectedCourse.name
);

const selectedCourseIntro = computed(
  () => store.state.userStore.selectedCourse.intro
);

// 假设您想要应用 "Dark Shadow" 样式
const shadowType = ref("dark");

// 使用提供的方法来获取 CSS 变量名
const getCssVarName = (type) => {
  return `--el-box-shadow${type ? "-" : ""}${type}`;
};

// 定义 CSS 变量
const cssVars = {
  "--el-box-shadow-dark":
    "0 6px 6px 2px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)",
};

// 计算阴影样式
const shadowStyle = computed(() => {
  return cssVars[getCssVarName(shadowType.value)];
});
</script>

<style>
.course-intro {
  margin-left: 10px;
  margin-right: 10px;
  letter-spacing: normal; /* 调整为正常的字符间距 */
  word-spacing: normal; /* 调整为正常的单词间距 */
  line-height: 1.6; /* 调整行间距，1.6 是较常见的值 */
}

.course-name {
  margin-left: 25px; /* 与左边界的距离 */
  font-family: "Arial Black", Gadget, sans-serif; /* 醒目的字体 */
  font-weight: bold; /* 字体加粗 */
  font-size: 18px;
}

.label {
  font-weight: bold; /* 使标签文字加粗 */
}
</style>
