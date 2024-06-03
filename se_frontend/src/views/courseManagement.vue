<template>
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
    <el-button type="primary" @click="handleAdd">添加课程</el-button>
    <el-button type="info" @click="handleEnd">截止选课</el-button>
  </div>

  <!-- 默认表格 -->
  <el-table :data="defaultTableData" style="width: 100%" max-height="550">
    <el-table-column
      v-for="column in defaultTableColumns"
      :key="column.prop"
      :prop="column.prop"
      :label="column.label"
      :width="column.width"
    />
    <el-table-column fixed="right" label="Operations">
      <template #default="scope">
        <el-button type="primary" @click="handleQuery(scope.row)"
          >查询</el-button
        >
        <el-button
          type="danger"
          @click="handleDelete(scope.row.courseCid)"
          width="30px"
          >Delete
        </el-button>
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
  <div>
    <el-dialog title="添加新课程" v-model="dialogFormVisible" width="50%">
      <el-form ref="formRef" :model="formData" :rules="rules">
        <el-form-item label="课程类型" required>
          <el-select v-model="formData.type" placeholder="请选择">
            <el-option label="通识必修课" value="通识必修课"></el-option>
            <el-option label="通识选修课" value="通识选修课"></el-option>
            <!--            <el-option label="通识必修课" value="TongShiBiXiu"></el-option>-->
            <!--            <el-option label="通识选修课" value="TongShiBiXiu"></el-option>-->
          </el-select>
        </el-form-item>
        <el-form-item label="课程名称" required>
          <el-input v-model="formData.name"></el-input>
        </el-form-item>
        <el-form-item label="课程编码(CID)" required>
          <el-input v-model.number="formData.CID"></el-input>
        </el-form-item>
        <el-form-item label="学分" required>
          <el-select v-model="formData.credit" placeholder="请选择">
            <el-option
              v-for="credit in [1, 2, 3, 4]"
              :key="credit"
              :label="credit"
              :value="credit"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课时" required>
          <el-select v-model="formData.hours" placeholder="请选择">
            <el-option label="32" value="32"></el-option>
            <el-option label="64" value="64"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属系" required>
          <el-select v-model="formData.department" placeholder="请选择">
            <el-option
              label="计算机科学与技术"
              value="计算机科学与技术"
            ></el-option>
            <el-option label="艺术中心" value="艺术中心"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教授姓名" required>
          <div v-for="(item, index) in formData.professor" :key="index">
            <el-input
              v-model="item.professor_name"
              placeholder="请输入教授姓名"
              style="width: 70%"
            ></el-input>
            <el-button @click="removeTeacher(index)">删除</el-button>
          </div>
          <el-button @click="addTeacher">添加老师</el-button>
        </el-form-item>
        <el-form-item label="上课地点" required>
          <el-input v-model="formData.location"></el-input>
        </el-form-item>
        <el-form-item label="上课时间" required>
          <el-select
            v-model="formData.weekType"
            placeholder="选择单双周"
            style="width: 70%"
          >
            <el-option label="单周" value="odd"></el-option>
            <el-option label="双周" value="even"></el-option>
            <el-option label="每周" value="both"></el-option>
          </el-select>
          <!-- 添加时间按钮 -->
          <el-button @click="addTime">添加时间</el-button>
          <!-- 动态添加的时间选择 -->
          <div v-for="(time, index) in formData.times" :key="index">
            <el-select v-model="time.day" placeholder="选择星期">
              <el-option label="周一" value="Monday"></el-option>
              <el-option label="周二" value="Tuesday"></el-option>
              <el-option label="周三" value="Wednesday"></el-option>
              <el-option label="周四" value="Thursday"></el-option>
              <el-option label="周五" value="Friday"></el-option>
              <el-option label="周六" value="Saturday"></el-option>
              <el-option label="周日" value="Sunday"></el-option>
            </el-select>

            <el-select v-model="time.period" placeholder="选择时间段">
              <el-option label="1-2" value="1-2"></el-option>
              <el-option label="3-4" value="3-4"></el-option>
              <el-option label="5-6" value="5-6"></el-option>
              <el-option label="7-8" value="7-8"></el-option>
              <el-option label="9-10" value="9-10"></el-option>
            </el-select>

            <!-- 删除时间按钮 -->
            <el-button @click="removeTime(index)">删除</el-button>
          </div>
        </el-form-item>
        <el-form-item label="课程容量" required>
          <el-input v-model.number="formData.capacity" type="number"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="resetForm(true)">Reset</el-button>
          <el-button type="primary" @click="submitForm"> Confirm </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
  <div>
    <el-dialog
      :title="stuDialogCourse"
      width="80%"
      v-model="isQueried"
      :visible="isQueried"
    >
      <el-table :data="queriedTableData" style="width: 100%" max-height="550">
        <el-table-column
          v-for="column in queriedTableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
        />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import {
  endSelection,
  fetchDataByType,
  submitCourse,
  fetchDataByCourseId,
  adminDeleteCourse,
  queryCourse,
} from "@/api/course";
import { ElMessage } from "element-plus";

const formInline = ref({
  courseType: "",
  day: "",
  time: "",
  weektype: "",
  coursename: "",
});
const params = ref({
  department: "",
  time: "",
  name: "",
});

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

const queryCourses = async () => {
  console.log("Querying for data:", params.value);
  const response = await queryCourse(params.value);
  console.log("response:", response.data);
  defaultTableData.value = response.data.map((item) => {
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
  totalItems.value = defaultTableData.value.length;
  handleCurrentChange(1); // 初始化或刷新数据后显示第一页
};

const totalItems = ref(0);
const defaultTableData = ref([]);
const queriedTableData = ref([]);
const isQueried = ref(false);

const defaultTableColumns = [
  { prop: "courseCid", label: "课程编号", width: "120" },
  { prop: "courseType", label: "课程类型", width: "150" },
  { prop: "courseName", label: "课程名称", width: "120" },
  { prop: "courseCreditPeriod", label: "学分/学时", width: "120" },
  { prop: "courseTimeLocation", label: "时间/地点", width: "120" },
  { prop: "courseDepartment", label: "课程部门", width: "120" },
  { prop: "courseInformationRate", label: "课程信息/评分", width: "120" },
  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },
];
const queriedTableColumns = [
  { prop: "courseCid", label: "课程编码", width: "120" },
  { prop: "courseName", label: "课程名称", width: "120" },
  { prop: "StudentID", label: "学生id", width: "120" },
  { prop: "StudentName", label: "学生姓名", width: "120" },
  { prop: "Department", label: "学生专业", width: "120" },
  { prop: "points", label: "投入分数", width: "120" },
  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },
];
async function fetchData() {
  console.log("fetching data");
  try {
    console.log("fetching data111");
    const response = await fetchDataByType("all");
    console.log("response:", response.data);
    defaultTableData.value = response.data.map((item) => {
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
    totalItems.value = defaultTableData.value.length;
    handleCurrentChange(1); // 初始化或刷新数据后显示第一页
  } catch (error) {
    console.error("Failed to fetch data:", error);
  }
}
const fetchQueryData = async (id) => {
  try {
    const response = await fetchDataByCourseId(id);
    console.log("response:", response.data);
    console.log("response.data.students:", response.data.students);
    queriedTableData.value = response.data.students.map((item) => ({
      Department: item.major.name,
      StudentID: item.sid,
      StudentName: item.name,
      points: item.score,
    }));
    totalItems.value = queriedTableData.value.length;
    //将表格课程部分数据
    for (let i = 0; i < totalItems.value; i++) {
      queriedTableData.value[i].courseName = response.data.courseName;
      queriedTableData.value[i].courseCid = response.data.cid;
      queriedTableData.value[
        i
      ].capacitySelectedNumber = `${response.data.capacity}; ${response.data.selected}`;
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
  if (isQueried.value === false) {
    defaultTableData.value = defaultTableData.value.slice(startIndex, endIndex);
  } else {
    queriedTableData.value = queriedTableData.value.slice(startIndex, endIndex);
  }
}

// 处理查询逻辑
onMounted(async () => {
  console.log("mounted");
  try {
    await fetchData(); // 确保 fetchData 正确处理了所有可能的错误
  } catch (error) {
    console.error("Error during fetchData:", error);
  }
});
const stuDialogCourse = ref("");
async function handleQuery(row) {
  // 这里可以加入你的查询逻辑
  isQueried.value = true;
  stuDialogCourse.value = row.courseName;
  await fetchQueryData(row.courseCid);
}

const dialogFormVisible = ref(false);

const formRef = ref(null);
const formData = reactive({
  type: "通识必修课",
  name: "小学物理",
  CID: "GH101",
  credit: "3",
  hours: "32",
  department: "计算机科学与技术",
  professor: [],
  location: "理学院101",
  weekType: "",
  times: [],
  capacity: "100",
});
const rules = reactive({
  addedPersons: [
    {
      type: "array",
      required: true,
      min: 1,
      message: "至少添加一位老师",
      trigger: "change",
    },
  ],
});
const addTime = () => {
  formData.times.push({
    day: "",
    period: "",
  });
};
const addTeacher = () => {
  formData.professor.push({
    professor_name: "",
  });
};
const removeTeacher = (index) => {
  formData.professor.splice(index, 1);
};
const removeTime = (index) => {
  formData.times.splice(index, 1);
};

async function handleAdd() {
  dialogFormVisible.value = true;
  console.log(dialogFormVisible, "add!");
}
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 处理时间数据格式
        const formattedTimes = formData.times.map((timeEntry) => {
          return `${timeEntry.day} ${timeEntry.period} ${formData.weekType}`;
        });
        await submitCourse({
          type: formData.type,
          name: formData.name,
          CID: formData.CID,
          credit: formData.credit,
          hours: formData.hours,
          department: formData.department,
          professorNames: formData.professor.map((prof) => prof.professor_name),
          location: formData.location,
          time: formattedTimes, // 使用格式化后的时间数据
          capacity: formData.capacity,
          description: "这是一门课程",
        });
        ElMessage({
          // 响应码相关已在拦截器中处理
          showClose: true,
          message: "添加成功",
          type: "success",
        });
        dialogFormVisible.value = false;
        resetForm();
      } catch (error) {
        // 错误处理
        ElMessage({
          showClose: true,
          message: error.message || "提交失败",
          type: "error",
        });
      }
    } else {
      ElMessage({
        showClose: true,
        message: "表单验证失败，请检查输入",
        type: "warning",
      });
    }
  });
};
const resetForm = (showMessage = false) => {
  formRef.value.resetFields();
  if (showMessage) {
    ElMessage({
      showClose: true,
      message: "表单已重置",
      type: "info",
      grouping: true,
    });
  }
};
async function handleDelete(courseCid) {
  console.log("delete!");
  try {
    await adminDeleteCourse({ CID: courseCid });
  } catch (error) {
    ElMessage.error("操作失败，请稍后重试");
  }
}

async function handleEnd() {
  try {
    await endSelection();
    ElMessage.success("选课已截止");
  } catch (error) {
    ElMessage.error("操作失败，请稍后重试");
  }
}
</script>

<style scoped lang="scss">
@import "@/style/mixin.scss";
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

.demo-pagination-block {
  display: flex;
  justify-content: flex-end;
  padding: 6px;
  //background-color: white;
  @include block_bg_color();
}
</style>
