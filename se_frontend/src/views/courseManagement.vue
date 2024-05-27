<template>
  <div>
    <!-- 查询表单 -->
    <el-form :inline="true" :model="formInline">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="课程ID" style="width: 250px">
            <el-input
              v-model="formInline.courseID"
              placeholder="请输入课程编码"
              clearable
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button type="primary" @click="handleQuery">查询</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item class="align-right">
            <el-button type="primary" @click="handleAdd">添加课程</el-button>
            <el-button type="danger" @click="handleDelete">删除课程</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>

  <!-- 默认表格 -->
  <div v-if="!isQueried">
    <el-table :data="activeData" style="width: 100%" max-height="550">
      <el-table-column
        v-for="column in defaultTableColumns"
        :key="column.prop"
        :prop="column.prop"
        :label="column.label"
        :width="column.width"
      />
    </el-table>
  </div>
  <!-- 查询后的表格 -->
  <div v-else>
    <el-table :data="activeData" style="width: 100%" max-height="550">
      <el-table-column
        v-for="column in queriedTableColumns"
        :key="column.prop"
        :prop="column.prop"
        :label="column.label"
        :width="column.width"
      />
    </el-table>
  </div>
  <div class="demo-pagination-block">
    <el-pagination
      v-model:current-page="currentPage4"
      v-model:page-size="pageSize4"
      :page-sizes="[25, 50, 75, 100]"
      :small="small"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalItems"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
  <div>
    <el-dialog title="添加新课程" v-model="dialogVisible" width="50%">
      <el-form :model="formData">
        <el-form-item label="课程类型">
          <el-select v-model="formData.type" placeholder="请选择">
            <el-option label="必修" value="必修"></el-option>
            <el-option label="选修" value="选修"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="formData.name"></el-input>
        </el-form-item>
        <el-form-item label="课程编码(CID)">
          <el-input v-model.number="formData.CID" type="number"></el-input>
        </el-form-item>
        <el-form-item label="学分">
          <el-select v-model="formData.credit" placeholder="请选择">
            <el-option
              v-for="credit in [1, 2, 3, 4]"
              :key="credit"
              :label="credit"
              :value="credit"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课时">
          <el-select v-model="formData.hours" placeholder="请选择">
            <el-option label="32" value="32"></el-option>
            <el-option label="64" value="64"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属系">
          <el-select v-model="formData.department" placeholder="请选择">
            <el-option
              label="计算机科学与工程系"
              value="计算机科学与工程系"
            ></el-option>
            <el-option label="艺术中心" value="艺术中心"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教授姓名">
          <el-input v-model="formData.professor_name"></el-input>
        </el-form-item>
        <el-form-item label="上课地点">
          <el-input v-model="formData.location"></el-input>
        </el-form-item>
        <el-form-item label="上课时间">
          <el-input
            v-model="formData.time"
            placeholder="格式如：周一 10:00-12:00"
          ></el-input>
        </el-form-item>
        <el-form-item label="课程容量">
          <el-input v-model.number="formData.capacity" type="number"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { fetchDataByType } from "@/api/course";
import { fetchDataByCourseId } from "@/api/course";

// 表单数据
const formInline = reactive({
  courseID: "",
});
const activeData = ref([]);

const totalItems = ref(0);
const defaultTableData = ref([]);
const queriedTableData = ref([]);
const isQueried = ref(false);

const defaultTableColumns = [
  { prop: "courseID", label: "课程编码", width: "150" },
  { prop: "courseType", label: "课程类型", width: "150" },
  { prop: "courseName", label: "课程名称", width: "120" },
  { prop: "courseCID", label: "课程代码", width: "120" },
  { prop: "courseCredit", label: "学分", width: "120" },
  { prop: "coursePeriod", label: "学时", width: "120" },
  { prop: "courseDepartment", label: "课程部门", width: "120" },
  { prop: "courseInformation", label: "课程信息", width: "120" },
  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },
];
const queriedTableColumns = [
  { prop: "courseName", label: "课程名称", width: "150" },
  { prop: "courseID", label: "课程编码", width: "120" },
  { prop: "StudentID", label: "学生id", width: "120" },
  { prop: "StudentName", label: "学生姓名", width: "120" },
  { prop: "Department", label: "专业", width: "120" },
  { prop: "points", label: "投入分数", width: "120" },
  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },
];
const fetchData = async () => {
  try {
    const response = await fetchDataByType("All");
    console.log("response:", response.data);
    defaultTableData.value = response.data.map((item) => ({
      courseType: item.type,
      courseName: item.name,
      courseID: item.id,
      courseCID: item.CID,
      courseCredit: item.credit,
      coursePeriod: item.hours,
      courseDepartment: item.department,
      courseInformation: `${item.professor_name}; ${item.location}; ${item.time}`,
      capacitySelectedNumber: `${item.capacity}; ${item.selected}`,
    }));
    totalItems.value = defaultTableData.value.length;
    handleCurrentChange(1); // 初始化或刷新数据后显示第一页
  } catch (error) {
    console.error("Failed to fetch data:", error);
  }
};
const fetchQueryData = async (id) => {
  try {
    const response = await fetchDataByCourseId(id);
    console.log("response:", response.data);
    const courseData = response.data.course;
    console.log("response.data.course:", courseData);
    console.log("response.data.students:", response.data.students);
    queriedTableData.value = response.data.students.map((item) => ({
      Department: item.department,
      StudentID: item.sid,
      StudentName: item.name,
      points: item.points,
    }));
    totalItems.value = queriedTableData.value.length;
    //将表格课程部分数据
    for (let i = 0; i < totalItems.value; i++) {
      queriedTableData.value[i].courseName = courseData.name;
      queriedTableData.value[i].courseID = courseData.id;
      queriedTableData.value[
        i
      ].capacitySelectedNumber = `${courseData.capacity}; ${courseData.selected}`;
    }
    handleCurrentChange(1); // 初始化或刷新数据后显示第一页
  } catch (error) {
    console.error("Failed to fetch data:", error);
  }
};
// 分页
const currentPage4 = ref(1);
const pageSize4 = ref(25);
const small = ref(false);
const background = ref(false);
const disabled = ref(false);
function handleSizeChange(val) {
  console.log(`${val} items per page`);
}
function handleCurrentChange(newPage) {
  currentPage4.value = newPage; // 更新当前页码
  console.log(`current page: ${newPage}`);
  const startIndex = (newPage - 1) * pageSize4.value;
  const endIndex = startIndex + pageSize4.value;
  const fullData = isQueried.value ? queriedTableData : defaultTableData;
  activeData.value = fullData.value.slice(startIndex, endIndex);
}

// 处理查询逻辑
onMounted(async () => {
  await fetchData(); // 组件加载时获取默认数据
});
async function handleQuery() {
  // 这里可以加入你的查询逻辑
  isQueried.value = true;
  console.log("Querying for courseID:", formInline.courseID);
  await fetchQueryData(formInline.courseID);
}

const dialogVisible = ref(false);

const formData = reactive({
  type: "",
  name: "",
  CID: "",
  credit: "",
  hours: "",
  department: "",
  professor_name: "",
  location: "",
  time: "",
  capacity: "",
});

async function handleAdd() {
  dialogVisible.value = true;
  console.log(dialogVisible, "add!");
}
async function handleSubmit() {
  console.log("submit!");
  dialogVisible.value = false;
}
async function handleDelete() {
  console.log("delete!");
}
</script>

<style scoped>
/* 样式可以根据需要进行调整 */
</style>
