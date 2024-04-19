<script setup>
import PlaceOptions from "@/component/Reservation/PlaceOptions.vue";
import ReservationPane from "@/component/Reservation/ReservationPane.vue";
</script>

<template>
  <el-row>
    <el-col span="6" class="place-options">
      <place-options />
    </el-col>
    <el-col span="18" class="reservation_pane">
      <ReservationPane />
    </el-col>
  </el-row>
</template>

<style scoped lang="scss"></style>
