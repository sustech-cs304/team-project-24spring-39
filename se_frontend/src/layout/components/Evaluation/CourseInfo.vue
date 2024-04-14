<script>
export default {
  data() {
    return {
      courseName: "courseName",
      courseIntro: "courseIntro",
    };
  },
};
</script>

<template>
  <div>
    <h1>{{ courseName }}</h1>
    <p>{{ courseIntro }}</p>
  </div>
</template>
