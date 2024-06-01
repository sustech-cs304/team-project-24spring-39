<script setup>
import PlaceOptions from "@/component/Reservation/PlaceOptions.vue";
import ReservationPane from "@/component/Reservation/ReservationPane.vue";
import { ref } from "vue";

const placeId = ref(null);
</script>

<template>
  <div class="container">
    <div class="place-options">
      <place-options @selectRoom="placeId = $event" />
    </div>
    <div class="reservation-pane">
      <ReservationPane :place-id="placeId" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.container {
  display: flex;
  gap: 16px; // 在两个子元素之间添加 16px 的间隔
  height: 617.6px; // 固定container为除去layout.vue中header的高度
}

.place-options {
  flex: 20%; // 占据 30% 的宽度
}

.reservation-pane {
  //flex: 80%; // 占据 70% 的宽度

  flex-grow: 0; // 移除flex-grow
  width: calc(100% - 288px); // 288px 是left-container的总宽度 + gap的宽度
}
</style>
