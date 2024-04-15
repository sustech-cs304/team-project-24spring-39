<template>
  <div>
    <span>{{ selectedCourseName }}</span>
    <p>{{ "courseIntro" }}</p>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useStore } from "vuex";

const store = useStore();

const selectedCourseName = computed(
  () => store.state.userStore.selectedCourse.name
);
</script>
