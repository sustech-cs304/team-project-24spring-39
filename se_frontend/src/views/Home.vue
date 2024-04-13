<script setup>
import { ref } from "vue";
import HorizonTimeLine from "@/component/HorizonTimeLine.vue";

const circleUrl = ref(
  "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
);

// //todolist
// const generateData = () => {
//   const data = [];
//   for (let i = 1; i <= 15; i++) {
//     data.push({
//       key: i,
//       label: `Option ${i}`,
//       disabled: i % 4 === 0,
//     });
//   }
//   return data;
// };
//
// const data = ref(generateData());
// const rightValue = ref([1]);
//
// // 右侧列表元素变化时触发（目前没用）
// const handleChange = (value, direction, movedKeys) => {
//   console.log(value, direction, movedKeys);
// };

const timelineData = ref([
  {
    year: 2021,
  },
  {
    year: 2022,
  },
  {
    year: 2023,
  },
  {
    year: 2024,
  },
]);

const activeTab = ref("selection");

const tables = ref({
  selection: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
    {
      date: "2016-05-01",
      name: "王小虎",
    },
    {
      date: "2016-05-03",
      name: "王小虎",
    },
  ],
  evaluation: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
    {
      date: "2016-05-01",
      name: "王小虎",
    },
    {
      date: "2016-05-03",
      name: "王小虎",
    },
  ],
  forum: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
    {
      date: "2016-05-01",
      name: "王小虎",
    },
    {
      date: "2016-05-03",
      name: "王小虎",
    },
  ],
  reservation: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
    {
      date: "2016-05-01",
      name: "王小虎",
    },
    {
      date: "2016-05-03",
      name: "王小虎",
    },
  ],
  self: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
  ],
});
</script>

<template>
  <el-row class="upon-timeline" justify="space-evenly">
    <!--    头像和用户信息-->
    <el-col :span="6">
      <el-card class="card">
        <div class="card-body">
          <el-avatar :size="75" :src="circleUrl" />
          <div class="user-info">
            <p>用户名</p>
            <p>用户角色</p>
          </div>
        </div>
      </el-card>
    </el-col>
    <!--    todolist-->
    <!--    <el-col :span="12">-->
    <!--      <el-transfer-->
    <!--        class="transfer-pane"-->
    <!--        v-model="rightValue"-->
    <!--        style="text-align: left; display: inline-block"-->
    <!--        filterable-->
    <!--        :left-default-checked="[2, 3]"-->
    <!--        :right-default-checked="[1]"-->
    <!--        :titles="['Source', 'Target']"-->
    <!--        :button-texts="['To left', 'To right']"-->
    <!--        :format="{-->
    <!--          noChecked: '${total}',-->
    <!--          hasChecked: '${checked}/${total}',-->
    <!--        }"-->
    <!--        :data="data"-->
    <!--        @change="handleChange"-->
    <!--      >-->
    <!--        <template #default="{ option }">-->
    <!--          <span>{{ option.key }} - {{ option.label }}</span>-->
    <!--        </template>-->
    <!--        <template #left-footer>-->
    <!--          <el-button class="transfer-footer" size="small">Operation</el-button>-->
    <!--        </template>-->
    <!--        <template #right-footer>-->
    <!--          <el-button class="transfer-footer" size="small">Operation</el-button>-->
    <!--        </template>-->
    <!--      </el-transfer>-->
    <!--    </el-col>-->
  </el-row>
  <!--  时间轴-->
  <el-row>
    <HorizonTimeLine class="h-timeline" :timelineData="timelineData" />
  </el-row>
  <!--  消息提醒-->
  <el-row class="message-reminding">
    <el-tabs v-model="activeTab" tab-position="left" class="tabs">
      <el-tab-pane label="选课" name="selection"></el-tab-pane>
      <el-tab-pane label="评教" name="evaluation"></el-tab-pane>
      <el-tab-pane label="课程论坛" name="forum"></el-tab-pane>
      <el-tab-pane label="自习室预约" name="reservation"></el-tab-pane>
      <el-tab-pane label="自定义" name="self">Self Made</el-tab-pane>
    </el-tabs>
    <el-table class="table" :data="tables[activeTab]">
      <el-table-column prop="date" label="Date" />
      <el-table-column prop="name" label="Name" />
      <!-- Add more columns as needed -->
    </el-table>
  </el-row>
</template>

<style scoped lang="scss">
.upon-timeline {
  display: flex;

  .card {
    align-items: center;
    padding: 20px;

    // el-card使用flex没用，因为里面还有一个el-card__body,所以我们自己嵌套一层div，在这个div上使用flex
    .card-body {
      display: flex;
      align-items: center;
      justify-content: space-around;
    }
  }

  .transfer-pane {
    transform: scale(0.8); // 缩放为原来的 60%
    transform-origin: top; // 缩放的基点设为顶部
  }
}

.h-timeline {
  width: 100%;
}

.message-reminding {
  position: fixed;
  bottom: 40px;
  display: flex;
  margin-top: 20px;
  width: 100%; /* 确保容器占满整个父元素的宽度 */

  .tabs {
    height: 200px;
    width: 120px;
    margin-top: 40px;
  }

  .table {
    flex: 1; /* 让表格占满剩余的空间 */
    height: 240px;
  }
}
</style>
