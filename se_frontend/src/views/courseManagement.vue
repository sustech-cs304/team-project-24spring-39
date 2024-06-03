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
      <el-form ref="formRef" :model="formData" :rules="rules">
        <el-form-item label="课程类型" required>
          <el-select v-model="formData.type" placeholder="请选择">
            <el-option label="必修" value="必修"></el-option>
            <el-option label="选修" value="选修"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程名称" required>
          <el-input v-model="formData.name"></el-input>
        </el-form-item>
        <el-form-item label="课程编码(CID)" required>
          <el-input v-model.number="formData.CID" type="number"></el-input>
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
              label="计算机科学与工程系"
              value="计算机科学与工程系"
            ></el-option>
            <el-option label="艺术中心" value="艺术中心"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教授姓名" required>
          <el-autocomplete
            v-model="formData.searchState"
            :fetch-suggestions="querySearchAsync"
            placeholder="添加老师"
            @select="addSelectedPerson"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="已添加的老师：" prop="addedPersons">
          <div v-if="formData.addedPersons.length > 0">
            <ul>
              <li v-for="person in formData.addedPersons" :key="person.id">
                {{ person }}
              </li>
            </ul>
          </div>
        </el-form-item>
        <el-form-item label="上课地点" required>
          <el-input v-model="formData.location"></el-input>
        </el-form-item>
        <el-form-item label="上课时间" required>
          <el-select v-model="formData.weekType" placeholder="选择单双周">
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
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import {
  fetchDataByType,
  searchTeacherBySid,
  submitCourse,
} from "@/api/course";
import { fetchDataByCourseId } from "@/api/course";
import { ElMessage } from "element-plus";

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
  { prop: "Department", label: "专业", width: "120" },
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
        courseDepartment: item.department.name,
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
  console.log("mounted");
  try {
    await fetchData(); // 确保 fetchData 正确处理了所有可能的错误
  } catch (error) {
    console.error("Error during fetchData:", error);
  }
});
async function handleQuery() {
  // 这里可以加入你的查询逻辑
  isQueried.value = true;
  console.log("Querying for courseID:", formInline.courseID);
  await fetchQueryData(formInline.courseID);
}

const dialogVisible = ref(false);

const formRef = ref(null);
const formData = reactive({
  type: "",
  name: "",
  CID: "",
  credit: "",
  hours: "",
  department: "",
  professor_name: [],
  location: "",
  weekType: "",
  times: [],
  capacity: "",
  addedPersons: [],
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

const removeTime = (index) => {
  formData.times.splice(index, 1);
};
const addSelectedPerson = (person) => {
  formData.addedPersons.push(person.value);
  formData.searchState = "";
};

const querySearchAsync = async (id, cb) => {
  if (id.length < 3) {
    cb([]);
    return;
  }
  try {
    const response = await searchTeacherBySid(id);
    console.log(response.data.name);
    const result = response.data.sid + " " + response.data.name;
    cb([{ value: result }]);
  } catch (error) {
    console.error("搜索老师信息请求失败:", error);
    cb([]);
  }
};
async function handleAdd() {
  dialogVisible.value = true;
  console.log(dialogVisible, "add!");
}
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await submitCourse({
          type: formData.type,
          name: formData.name,
          CID: formData.CID,
          credit: formData.credit,
          hours: formData.hours,
          department: formData.department,
          persons: formData.addedPersons.map((teacher) => teacher.slice(0, 8)),
          location: formData.location,
          weekType: formData.weekType,
          times: formData.times,
          capacity: formData.capacity,
        });
        ElMessage({
          // 响应码相关已在拦截器中处理
          showClose: true,
          message: "添加成功",
          type: "success",
        });
        dialogVisible.value = false;
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
async function handleDelete() {
  console.log("delete!");
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
</style>
