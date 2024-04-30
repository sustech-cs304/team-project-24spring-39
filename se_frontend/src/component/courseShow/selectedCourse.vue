<template>
  <el-row style="margin-top: 10px" gutter="30px">
    <el-col :span="6">
      <el-text>已选学分：{{ totalCredits }}</el-text>
    </el-col>
    <el-col :span="6">
      <el-text>剩余分数：{{ remainingPoints }}</el-text>
    </el-col>
    <el-col :span="6">
      <el-button plain @click="OpenDialog">查看已选课程</el-button>
    </el-col>
  </el-row>

  <el-dialog title="已选课程" v-model="dialogVisible" width="80%" height="80%">
    <!-- 对话框内容 -->
    <el-table :data="tableData" style="width: 100%" max-height="400">
      <el-table-column
        v-for="column in tableColumns"
        :key="column.prop"
        :prop="column.prop"
        :label="column.label"
        :width="column.width"
      />
      <el-table-column fixed="right" label="Operations">
        <el-button type="danger" @click="DeleteCourse" width="30px"
          >Delete
        </el-button>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup>
import { ref, computed } from "vue";
import { ReturnSelectedCourse } from "@/api/course";

const MAX_POINTS = 100;
const remainingPoints = computed(() => {
  let totalUsedPoints = tableData.value.reduce(
    (acc, course) => acc + course.points,
    0
  );
  return MAX_POINTS - totalUsedPoints;
});

const totalCredits = computed(() => {
  let totalCredit = tableData.value.reduce(
    (acc, course) => acc + course.courseCredit,
    0
  );
  return totalCredit;
});

const tableData = ref([]);
const tableColumns = [
  { prop: "courseType", label: "课程类型", width: "150" },
  { prop: "courseName", label: "课程名称", width: "120" },
  { prop: "courseID", label: "课程代码", width: "120" },
  { prop: "courseCredit", label: "学分", width: "120" },
  { prop: "courseInformation", label: "课程信息", width: "120" },
  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },
  { prop: "points", label: "投入分数", width: "120" },
];

const dialogVisible = ref(false);
const fetchData = async () => {
  if (dialogVisible.value) {
    try {
      const response = await ReturnSelectedCourse();
      console.log("response:", response.data);
      tableData.value = response.data.map((item) => ({
        courseType: item.type,
        courseName: item.name,
        courseID: item.CID,
        courseCredit: item.credit,
        courseInformation: `${item.professor_name}; ${item.location}; ${item.time}`,
        capacitySelectedNumber: `${item.capacity}; ${item.selected}`,
        points: item.points,
      }));
    } catch (error) {
      console.error("Failed to fetch data:", error);
    }
  }
};

async function OpenDialog() {
  dialogVisible.value = true;
  await fetchData();
}

function DeleteCourse() {
  console.log("Delete course");
}
</script>

<style scoped>
/* 对话框样式 */
</style>
