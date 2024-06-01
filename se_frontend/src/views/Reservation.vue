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
}

.place-options {
  flex: 20%; // 占据 30% 的宽度
}

.reservation-pane {
  flex: 80%; // 占据 70% 的宽度
}
</style>
