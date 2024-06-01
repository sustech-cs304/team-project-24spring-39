<script setup>
import { useDark, useToggle } from "@vueuse/core";
import { useStore } from "vuex";

const store = useStore();

const isDark = useDark({
  selector: "html",
  attribute: "class",
  valueDark: "dark",
  valueLight: "light",
});
const toggleDark = useToggle(isDark);

function changeTheme() {
  const isDark = toggleDark();
  store.commit("setIsDark", isDark);
}
</script>

<template>
  <div class="dark-mode" @click="changeTheme">
    <el-tooltip
      effect="dark"
      :content="isDark ? '默认主题' : '暗黑主题'"
      placement="bottom"
    >
      <el-icon>
        <component :is="isDark ? 'Sunny' : 'MoonNight'"></component>
      </el-icon>
    </el-tooltip>
  </div>
</template>

<style scoped lang="scss">
@import "@/style/mixin.scss";

.dark-mode {
  cursor: pointer;
  width: 38px;
  height: 100%;
  padding: 0 10px;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    @include hover_bg_color();
  }
}

//[class="dark"] .dark-mode {
//  &:hover {
//    background-color: #242424;
//  }
//}
</style>
