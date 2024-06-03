<template>
  <div class="container">
    <div class="header">
      <el-select
        v-model="formInline.courseType"
        placeholder="请选择课程部门"
        clearable
        style="width: 180px; margin-right: 30px"
      >
        <el-option label="电子工程" value="电子工程" />
        <el-option label="化学工程" value="化学工程" />
      </el-select>
      <el-select
        v-model="formInline.day"
        placeholder="日期"
        style="width: 90px; margin-right: 5px"
        clearable
      >
        <el-option label="周一" value="Monday" />
        <el-option label="周二" value="Tuesday" />
        <el-option label="周三" value="Wednesday" />
        <el-option label="周四" value="Thursday" />
        <el-option label="周五" value="Friday" />
        <el-option label="周六" value="Saturday" />
        <el-option label="周日" value="Sunday" />
      </el-select>
      <el-select
        v-model="formInline.time"
        placeholder="时间段"
        style="width: 115px; margin-right: 5px"
        clearable
      >
        <el-option label="1-2节课" value="1-2" />
        <el-option label="3-4节课" value="3-4" />
        <el-option label="5-6节课" value="5-6" />
        <el-option label="7-8节课" value="7-8" />
        <el-option label="9-10节课" value="9-10" />
      </el-select>
      <el-select
        v-model="formInline.weektype"
        placeholder="单双周"
        style="width: 90px; margin-right: 30px"
        clearable
      >
        <el-option label="单周" value="odd" />
        <el-option label="双周" value="even" />
        <el-option label="每周" value="both" />
      </el-select>
      <el-input
        v-model="formInline.coursename"
        placeholder="请输入课程名称"
        style="width: 250px"
        clearable
      >
      </el-input>
      <!-- 自动扩展的空白元素 -->
      <div class="spacer"></div>
      <el-button type="primary" @click="HandleQuery">查询</el-button>
    </div>
    <div class="showSelectedCourse">
      <div class="selectedCourse">
        <el-row style="margin-top: 10px" gutter="20px">
          <!--      <el-col :span="6">-->
          <!--        <el-text>已选学分：{{ totalCredits }}</el-text>-->
          <!--      </el-col>-->
          <el-col :span="6">
            <el-text>剩余分数：{{ remainingPoints }}</el-text>
          </el-col>
          <el-col :span="6">
            <el-button plain @click="OpenDialog">查看已选课程</el-button>
          </el-col>
        </el-row>
      </div>
      <el-dialog
        title="已选课程"
        v-model="dialogVisible"
        width="80%"
        height="80%"
      >
        <!-- 对话框内容 -->
        <el-table :data="SelectTableData" style="width: 100%" max-height="400">
          <el-table-column
            v-for="column in SelectTableColumns"
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            :width="column.width"
          />
          <el-table-column fixed="right" label="Operations">
            <template #default="scope">
              <el-button
                type="danger"
                @click="DeleteCourse(scope.row.courseCid)"
                width="30px"
                >Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </div>
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane
        v-for="title in titles"
        :key="title.id"
        :label="title.label"
        :name="title.name"
        v-show="activeTab === title.name"
      >
        <template #label>
          <div :style="{ color: title.color }">{{ title.label }}</div>
        </template>
        <!-- 这里根据 activeTab 显示对应的数据 -->
        <el-table :data="tableData" style="width: 100%" max-height="400">
          <el-table-column
            v-for="column in tableColumns"
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            :width="column.width"
          />
          <el-table-column fixed="right" label="Operations">
            <template #default="scope">
              <el-form :inline="true" :model="scope.row" width="130px">
                <el-form-item>
                  <el-input
                    v-model="scope.row.score"
                    placeholder="投入分数"
                    width="100px"
                    clearable
                  />
                  <el-button
                    type="primary"
                    @click="HandleAdd(scope.row)"
                    width="30px"
                    >Add
                  </el-button>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
        </el-table>
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
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ElMessage } from "element-plus";
import { ref, onMounted } from "vue";
import {
  fetchDataByType,
  submitSelectedCourse,
  queryCourse,
  queryStopState,
} from "@/api/course";

const remainingPoints = ref(100);
let state = ref();
async function queryState() {
  const response = await queryStopState();
  return response.data;
}
//////////////////////////////////tab
const titles = [
  { id: 1, label: "全部", name: "AllCourses", type: "all" },
  { id: 2, label: "通识必修", name: "BiXiuCourses", type: "bixiu" },
  { id: 3, label: "通识选修", name: "XuanXiuCourses", type: "xuanxiu" },
  { id: 4, label: "课程推荐", name: "RecommendCourses", type: "high_score" },
];
const activeTab = ref(titles[0].name); // 默认选中第一个标签页

const tableData = ref([]);
const tableColumns = [
  { prop: "courseCid", label: "课程编号", width: "150" },
  { prop: "courseType", label: "课程类型", width: "150" },
  { prop: "courseName", label: "课程名称", width: "120" },
  { prop: "courseCreditPeriod", label: "学分/学时", width: "120" },
  { prop: "courseTimeLocation", label: "时间/地点", width: "120" },
  { prop: "courseDepartment", label: "课程部门", width: "120" },
  { prop: "courseInformationRate", label: "课程信息/评分", width: "120" },
  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },
];
const cachedData = ref({});
const totalItems = ref(0);

//处理点击tab跳转
async function handleTabClick(tab) {
  const tabName = tab.props.name;
  const type = titles.find((title) => title.name === tabName)?.type;
  console.log("tabName:", tabName, "type:", type);
  await fetchData(type);
  formInline.value = {
    courseType: "",
    day: "",
    time: "",
    weektype: "",
    coursename: "",
  };
}

onMounted(async () => {
  state.value = await queryState();
  console.log("state.value:", state.value);
  console.log("remainingPoints:", remainingPoints.value);
  if (state.value === true) {
    ElMessage.error("选课已结束");
  }
  await handleTabClick({ props: { name: "AllCourses" } });
});

//获取tab页面数据
const fetchData = async (type) => {
  if (type !== undefined && !cachedData.value[type]) {
    try {
      const response = await fetchDataByType(type);
      console.log("response:", response.data);
      cachedData.value[type] = response.data.map((item) => {
        const formattedProfessor = item.professors
          .map((prof) => prof.name)
          .join(", ");
        return {
          courseCid: item.cid,
          courseType: item.type,
          courseName: `${item.name}`,
          courseCreditPeriod: `${item.credit}/${item.hours}`,
          courseTimeLocation: `${item.time}; ${item.location}`,
          courseDepartment: item.department,
          courseInformationRate: `教师：${formattedProfessor}; 评分：${item.rate}`,
          capacitySelectedNumber: `${item.capacity}; ${item.selected}`,
        };
      });
      totalItems.value = cachedData.value[type].length;
      handleCurrentChange(1); // 初始化或刷新数据后显示第一页
    } catch (error) {
      console.error("Failed to fetch data:", error);
    }
  }
  tableData.value = cachedData.value[type] || [];
};

/////////////////////////////////////筛选
const queryTableData = ref([]);
const params = ref({
  department: "",
  time: "",
  name: "",
});
const formInline = ref({
  courseType: "",
  day: "",
  time: "",
  weektype: "",
  coursename: "",
});
//筛选课程
const queryCourses = async () => {
  try {
    console.log("Querying for data:", params.value);
    const response = await queryCourse(params.value);
    console.log("response:", response.data);
    queryTableData.value = response.data.map((item) => {
      const formattedProfessor = item.professors
        .map((prof) => prof.name)
        .join(", ");
      return {
        courseCid: item.cid,
        courseType: item.type,
        courseName: `${item.name}`,
        courseCreditPeriod: `${item.credit}/${item.hours}`,
        courseTimeLocation: `${item.time}; ${item.location}`,
        courseDepartment: item.department,
        courseInformationRate: `教师：${formattedProfessor}; 评分：${item.rate}`,
        capacitySelectedNumber: `${item.capacity}; ${item.selected}`,
      };
    });
    totalItems.value = tableData.value.length;
    handleCurrentChange(1); // 初始化或刷新数据后显示第一页
  } catch (error) {
    console.error("Failed to query data:", error);
  }
  tableData.value = queryTableData.value || [];
};
//筛选课程数据
async function HandleQuery() {
  if (
    formInline.value.day !== "" &&
    formInline.value.weektype !== "" &&
    formInline.value.time === ""
  ) {
    ElMessage.error("请选择具体时间段");
  } else {
    const timeComponents = [
      formInline.value.day,
      formInline.value.time,
      formInline.value.weektype,
    ];
    const filledComponents = timeComponents.filter(Boolean); // 过滤掉空字符串

    formInline.value.courseType = formInline.value.courseType || "";
    formInline.value.day = formInline.value.day || "";
    formInline.value.time = formInline.value.time || "";
    formInline.value.weektype = formInline.value.weektype || "";
    formInline.value.coursename = formInline.value.coursename || "";

    // 使用空格连接非空组件
    const timeString = filledComponents.join(" ");

    // 设置查询参数
    params.value.department = formInline.value.courseType;
    params.value.time = timeString;
    params.value.name = formInline.value.coursename;

    // 执行查询
    await queryCourses(params);
  }
}

// 添加已选课程
async function HandleAdd(row) {
  console.log("Adding course:", state);
  if (state.value === true) {
    ElMessage.error("选课已结束");
  } else {
    console.log("remainingPoints:", remainingPoints.value);
    if (remainingPoints.value < row.score) {
      console.log("Not enough points!");
      ElMessage.error("Not enough points!");
      return;
    }
    try {
      // 调用 API 函数
      const response = await submitSelectedCourse(row.courseCid, row.score);
      remainingPoints.value = response.data;
      console.log("Submitted successfully", remainingPoints.value);
      console.log("Submitted successfully", response.data);
      ElMessage.success("Course added successfully!");
      row.score = ""; // 清空输入框
    } catch (error) {
      console.error("Failed to submit course", error);
      ElMessage.error("Failed to add course!");
    }
  }
}

/////////////////////////////////////分页
const currentPage4 = ref(1);
const pageSize4 = ref(25);
const small = ref(false);
const background = ref(false);
const disabled = ref(false);

function handleSizeChange(val) {
  console.log(`${val} items per page`);
}

function handleCurrentChange(newPage) {
  console.log(`current page: ${newPage}`);
  const allData = tableData.value;
  const startIndex = (newPage - 1) * pageSize4.value;
  tableData.value = allData.slice(startIndex, startIndex + pageSize4.value);
}

/////////////////////////////////////////////////////////
import {
  DeleteSelectedCourseByCourseId,
  QuerySelectedCourseFinal,
  ReturnSelectedCourse,
} from "@/api/course";

const SelectTableData = ref([]);
const SelectTableColumns = [
  { prop: "courseCid", label: "课程编号", width: "120" },
  { prop: "courseType", label: "课程类型", width: "150" },
  { prop: "courseName", label: "课程名称", width: "120" },
  { prop: "courseDepartment", label: "课程部门", width: "120" },
  { prop: "courseCredit", label: "学分", width: "120" },
  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },
  { prop: "points", label: "投入分数", width: "120" },
];
// const totalCredits = computed(() => {
//   if (props.state === false) {
//     return tableData.value.reduce(
//       (acc, course) => acc + course.courseCredit,
//       0
//     );
//   } else {
//     return 0;
//   }
// });

const dialogVisible = ref(false);

//获取已选课程数据
const fetchSelectData = async () => {
  if (dialogVisible.value) {
    try {
      if (state.value === true) {
        const response = await QuerySelectedCourseFinal();
        console.log("response:", response.data);
      }
      const response = await ReturnSelectedCourse();
      console.log("response:", response.data);
      SelectTableData.value = response.data.map((item) => ({
        courseCid: item.cid,
        courseType: item.type,
        courseName: `${item.name}`,
        courseCredit: item.credit,
        courseDepartment: item.department,
        capacitySelectedNumber: `${item.capacity}; ${item.selected}`,
        points: item.score,
      }));
    } catch (error) {
      console.error("Failed to fetch data:", error);
    }
  }
};

async function OpenDialog() {
  dialogVisible.value = true;
  await fetchSelectData();
}

//删除选课
async function DeleteCourse(courseCid) {
  try {
    if (state.value === true) {
      ElMessage.error("选课已截至，无法操作");
    } else {
      console.log("Deleting course:", courseCid);
      const response = await DeleteSelectedCourseByCourseId(courseCid);
      SelectTableData.value = SelectTableData.value.filter(
        (course) => course.courseCid !== courseCid
      );
      ElMessage.success("Course deleted successfully!");
      remainingPoints.value = response.data;
      console.log("remainingPoints:", remainingPoints.value);
    }
  } catch (error) {
    console.error("Failed to delete course:", error);
    ElMessage.error("Failed to delete course!");
  }
}
</script>

<style scoped lang="scss">
@import "@/style/mixin.scss";
.demo-pagination-block {
  display: flex;
  justify-content: flex-end;
  padding: 6px;
  //background-color: white;
  @include block_bg_color();
}
.container {
  flex-direction: column;
  height: 617.6px; // 固定container为除去layout.vue中header的高度
}

.header {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  //gap: 16px; /* 控制各个元素之间的间距 */
  padding: 16px;
  //background-color: white;
  @include block_bg_color();

  .spacer {
    flex-grow: 1;
  }
}
.showSelectedCourse {
  margin-top: 16px;
  padding: 8px;
}
</style>
