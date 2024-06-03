<!--<template>-->
<!--  <div class="selectedCourse">-->
<!--    <el-row gutter="20px" class="center-vertical">-->
<!--      <el-col :span="6" class="center-vertical">-->
<!--        <el-text>已选学分：{{ totalCredits }}</el-text>-->
<!--      </el-col>-->
<!--      <el-col :span="6" class="center-vertical">-->
<!--        <el-text>剩余分数：{{ remainingPoints }}</el-text>-->
<!--      </el-col>-->
<!--      <el-col :span="6" class="center-vertical">-->
<!--        <el-button plain @click="OpenDialog">查看已选课程</el-button>-->
<!--      </el-col>-->
<!--    </el-row>-->
<!--  </div>-->
<!--  <el-dialog title="已选课程" v-model="dialogVisible" width="80%" height="80%">-->
<!--    &lt;!&ndash; 对话框内容 &ndash;&gt;-->
<!--    <el-table :data="tableData" style="width: 100%" max-height="400">-->
<!--      <div v-if="state === 1">-->
<!--        <el-table-column-->
<!--          v-for="column in tableColumnsState"-->
<!--          :key="column.prop"-->
<!--          :prop="column.prop"-->
<!--          :label="column.label"-->
<!--          :width="column.width"-->
<!--        />-->
<!--      </div>-->
<!--      <div v-else>-->
<!--        <el-table-column-->
<!--          v-for="column in tableColumns"-->
<!--          :key="column.prop"-->
<!--          :prop="column.prop"-->
<!--          :label="column.label"-->
<!--          :width="column.width"-->
<!--        />-->
<!--        <el-table-column fixed="right" label="Operations">-->
<!--          <template #default="scope">-->
<!--            <el-button-->
<!--              type="danger"-->
<!--              @click="DeleteCourse(scope.row.courseCid)"-->
<!--              width="30px"-->
<!--              >Delete-->
<!--            </el-button>-->
<!--          </template>-->
<!--        </el-table-column>-->
<!--      </div>-->
<!--    </el-table>-->
<!--  </el-dialog>-->
<!--</template>-->

<!--<script setup>-->
<!--import { computed, ref, watch } from "vue";-->
<!--import {-->
<!--  DeleteSelectedCourseByCourseId,-->
<!--  ReturnSelectedCourse,-->
<!--} from "@/api/course";-->
<!--import { useStore } from "vuex";-->
<!--import { ElMessage } from "element-plus";-->
<!--import { defineProps } from "vue";-->

<!--// 使用 defineProps 定义期望从父组件接收的 props-->
<!--const props = defineProps({-->
<!--  state: Number,-->
<!--});-->
<!--const tableDataState = ref([]);-->
<!--const tableColumnsState = [-->
<!--  { prop: "courseCid", label: "课程编号", width: "120" },-->
<!--  { prop: "courseType", label: "课程类型", width: "150" },-->
<!--  { prop: "courseName", label: "课程名称", width: "120" },-->
<!--  { prop: "courseDepartment", label: "课程部门", width: "120" },-->
<!--  { prop: "courseCredit", label: "学分", width: "120" },-->
<!--  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },-->
<!--  { prop: "points", label: "投入分数", width: "120" },-->
<!--  { prop: "isSucceed", label: "是否选课成功", width: "120", fixed: "right" },-->
<!--];-->

<!--const MAX_POINTS = 100;-->
<!--const tableData = ref([]);-->
<!--const tableColumns = [-->
<!--  { prop: "courseCid", label: "课程编号", width: "120" },-->
<!--  { prop: "courseType", label: "课程类型", width: "150" },-->
<!--  { prop: "courseName", label: "课程名称", width: "120" },-->
<!--  { prop: "courseDepartment", label: "课程部门", width: "120" },-->
<!--  { prop: "courseCredit", label: "学分", width: "120" },-->
<!--  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },-->
<!--  { prop: "points", label: "投入分数", width: "120" },-->
<!--];-->

<!--const remainingPoints = computed(() => {-->
<!--  if (props.state === 1) {-->
<!--    return 100;-->
<!--  } else {-->
<!--    let totalUsedPoints = tableData.value.reduce(-->
<!--      (acc, course) => acc + course.points,-->
<!--      0-->
<!--    );-->
<!--    return MAX_POINTS - totalUsedPoints;-->
<!--  }-->
<!--});-->

<!--const totalCredits = computed(() => {-->
<!--  if (props.state === 0) {-->
<!--    return tableData.value.reduce(-->
<!--      (acc, course) => acc + course.courseCredit,-->
<!--      0-->
<!--    );-->
<!--  } else {-->
<!--    return 0;-->
<!--  }-->
<!--});-->
<!--const store = useStore();-->
<!--watch(remainingPoints, (newValue) => {-->
<!--  if (props.state === 0) {-->
<!--    store.commit("setRemainingPoints", newValue);-->
<!--  }-->
<!--});-->

<!--const dialogVisible = ref(false);-->
<!--const fetchData = async () => {-->
<!--  if (dialogVisible.value) {-->
<!--    try {-->
<!--      const response = await ReturnSelectedCourse();-->
<!--      console.log("response:", response.data);-->
<!--      if (props.state === 1) {-->
<!--        tableDataState.value = response.data.map((item) => ({-->
<!--          courseCid: item.cid,-->
<!--          courseType: item.type,-->
<!--          courseName: `${item.name}`,-->
<!--          courseCredit: item.credit,-->
<!--          courseDepartment: item.department.name,-->
<!--          capacitySelectedNumber: `${item.capacity}; ${item.selected}`,-->
<!--          points: item.score,-->
<!--          isSucceed: item.isSucceed,-->
<!--        }));-->
<!--      } else {-->
<!--        tableData.value = response.data.map((item) => ({-->
<!--          courseCid: item.cid,-->
<!--          courseType: item.type,-->
<!--          courseName: `${item.name}`,-->
<!--          courseCredit: item.credit,-->
<!--          courseDepartment: item.department.name,-->
<!--          capacitySelectedNumber: `${item.capacity}; ${item.selected}`,-->
<!--          points: item.score,-->
<!--        }));-->
<!--      }-->
<!--    } catch (error) {-->
<!--      console.error("Failed to fetch data:", error);-->
<!--    }-->
<!--  }-->
<!--};-->

<!--async function OpenDialog() {-->
<!--  dialogVisible.value = true;-->
<!--  await fetchData();-->
<!--}-->

<!--async function DeleteCourse(courseCid) {-->
<!--  try {-->
<!--    // 调用 API 删除课程-->
<!--    console.log("Deleting course:", courseCid);-->
<!--    const response = await DeleteSelectedCourseByCourseId(courseCid);-->
<!--    console.log("Deleted course response:", response);-->

<!--    // 从本地状态移除已删除的课程-->
<!--    tableData.value = tableData.value.filter(-->
<!--      (course) => course.courseCid !== courseCid-->
<!--    );-->
<!--    ElMessage.success("Course deleted successfully!");-->

<!--    // 可选：更新 Vuex state，如果需要-->
<!--    store.commit("updateCreditsAndPoints");-->
<!--  } catch (error) {-->
<!--    console.error("Failed to delete course:", error);-->
<!--    ElMessage.error("Failed to delete course!");-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped lang="scss">-->
<!--@import "@/style/mixin.scss";-->
<!--//.selectedCourse {-->
<!--//  padding: 10px;-->
<!--//  margin-top: 10px;-->
<!--//  @include block_bg_color();-->
<!--//}-->
<!--.center-vertical {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--}-->
<!--</style>-->
