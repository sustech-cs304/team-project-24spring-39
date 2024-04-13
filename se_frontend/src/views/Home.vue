<script setup>
import { ref } from "vue";
import HorizonTimeLine from "@/component/HorizonTimeLine.vue";

const circleUrl = ref(
  "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
);

const timelineData = ref([
  {
    year: 2022,
  },
  {
    year: 2021,
  },
  {
    year: 2020,
  },
  {
    year: 2019,
  },
  {
    year: 2018,
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
  <el-row>
    <!--    头像和用户信息-->
    <el-col :span="8">
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
    <!--    今日课程-->
    <el-col :span="16">
      <div class="today-classes" />
    </el-col>
  </el-row>
  <!--  时间轴-->
  <el-row>
    <HorizonTimeLine :timelineData="timelineData" />
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
