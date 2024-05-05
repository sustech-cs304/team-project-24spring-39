<script setup>
import { ref, onMounted } from "vue";

const rooms = ref([
  {
    id: 1,
    name: "会议室",
    description: "MEETING ROOM",
    icon: "icon-meeting",
    color: "#F7BA2A",
  },
  {
    id: 2,
    name: "活动室",
    description: "ACTIVITY ROOM",
    icon: "icon-activity",
    color: "#409EFF",
  },
  {
    id: 3,
    name: "自习室",
    description: "STUDY ROOM",
    icon: "icon-study",
    color: "#67C23A",
  },
  {
    id: 4,
    name: "健身房",
    description: "GYM",
    icon: "icon-gym",
    color: "#E6A23C",
  },
]);

// eslint-disable-next-line no-undef
const emit = defineEmits(["selectRoom"]);

function selectRoom(roomId) {
  emit("selectRoom", roomId);
}

// 在组件挂载后立即选择第一个房间
onMounted(() => {
  if (rooms.value.length > 0) {
    selectRoom(rooms.value[0].id);
  }
});
</script>

<template>
  <div class="room-list">
    <div
      v-for="room in rooms"
      :key="room.id"
      class="room-card"
      :style="{ backgroundColor: room.color }"
      @click="selectRoom(room.id)"
    >
      <i :class="room.icon"></i>
      <div class="room-name">{{ room.name }}</div>
      <div class="room-description">{{ room.description }}</div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.room-list {
  display: flex;
  flex-direction: column;
  gap: 1rem; // 间隔
}

.room-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1rem;
  border-radius: 0.5rem;
  color: white;
  cursor: pointer;
  box-shadow: 0 4px 6px 0 rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s, transform 0.3s;

  &:hover {
    box-shadow: 0 6px 12px 0 rgba(0, 0, 0, 0.2);
    transform: translateY(-5px);
  }

  i {
    // 假设使用的是 Font Awesome 或类似的图标库
    font-size: 2rem; // 设置图标的大小
    //margin-right: 1rem;
    width: 3rem; // 确保图标的宽度，如果图标是方形的
    height: 3rem; // 确保图标的高度，如果图标是方形的
    background-color: rgba(255, 255, 255, 0.3); // 图标背景色半透明
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%; // 图标为圆形
  }

  .room-name {
    font-size: 1.25rem; // 房间名称的字体相对较大
    font-weight: bold; // 房间名称加粗
    margin-bottom: 0.25rem; // 与房间描述之间的间隔
  }

  .room-description {
    font-size: 0.85rem; // 房间描述的字体相对较小
    opacity: 0.7; // 房间描述的透明度减少，看起来更轻
  }
}

// 预先定义好的图标类
.icon-meeting {
  /* 使用相应的图标库类名 */
}
.icon-activity {
  /* 使用相应的图标库类名 */
}
.icon-study {
  /* 使用相应的图标库类名 */
}
.icon-gym {
  /* 使用相应的图标库类名 */
}
</style>
