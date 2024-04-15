<script setup>
import { computed } from "vue";
import { useStore } from "vuex";

const store = useStore();

const content = computed(() =>
  store.state.layoutStore.isExpand ? "点击以收缩" : "点击以展开"
);

function handleClick() {
  store.commit("setIsExpand");
}
</script>

<template>
  <el-tooltip
    class="box-item"
    effect="dark"
    :content="content"
    placement="right"
  >
    <el-icon class="toggle-icon" size="26px" @click="handleClick">
      <component
        :is="store.state.layoutStore.isExpand ? 'Expand' : 'Fold'"
      ></component>
    </el-icon>
  </el-tooltip>
</template>

<style scoped lang="scss">
.toggle-icon {
  cursor: pointer;
}
</style>
