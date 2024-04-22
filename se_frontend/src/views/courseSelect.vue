<template>
  <el-col class="courseSelect">
    <div>
      <el-card shadow="hover" style="margin-top: 20px">
        <el-form :inline="true" :model="formInline">
          <el-form-item label="课程名称">
            <el-input
              v-model="formInline.user"
              placeholder="请输入课程名称"
              clearable
            />
          </el-form-item>
          <el-form-item label="开设部门" style="width: 250px">
            <el-select
              v-model="formInline.region"
              placeholder="请输入开设部门"
              clearable
            >
              <el-option label="体育中心" value="shanghai" />
              <el-option label="艺术中心" value="beijing" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="HandleQuery">查询</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
    <div class="creditInformation">
      <el-row style="margin-top: 10px" gutter="30px">
        <el-col :span="6">
          <el-text>已选学分：0</el-text>
        </el-col>
        <el-col :span="6">
          <el-text>剩余分数：100</el-text>
        </el-col>
      </el-row>
    </div>
    <el-tabs
      v-model="activeName"
      type="card"
      class="demo-tabs"
      @tab-click="handleClick"
      style="margin-top: 20px"
    >
      <el-tab-pane label="全部" name="first">
        <el-table :data="tableData" style="width: 100%" max-height="400">
          <el-table-column prop="courseType" label="课程类型" width="150" />
          <el-table-column prop="courseName" label="课程名称" width="120" />
          <el-table-column prop="courseID" label="课程代码" width="120" />
          <el-table-column prop="courseCredit" label="学分" width="120" />
          <el-table-column prop="coursePeriod" label="学时" width="120" />
          <el-table-column
            prop="courseDepartment"
            label="课程部门"
            width="120"
          />
          <el-table-column
            prop="courseInformation"
            label="课程信息"
            width="120"
          />
          <el-table-column
            prop="capacitySelectedNumber"
            label="容量/已选"
            width="120"
          />
          <el-table-column fixed="right" label="Operations">
            <el-form :inline="true" :model="AddCourse" width="130px">
              <el-form-item>
                <el-input
                  v-model="AddCourse.credit"
                  placeholder="投入分数"
                  width="100px"
                  clearable
                />
                <el-button type="primary" @click="HandleAdd" width="30px"
                  >Add
                </el-button>
              </el-form-item>
            </el-form>
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
      <el-tab-pane label="通识必修" name="second">Config</el-tab-pane>
      <el-tab-pane label="通识选修" name="third">Role</el-tab-pane>
      <el-tab-pane label="培养方案内课程" name="fourth">Task</el-tab-pane>
    </el-tabs>
  </el-col>
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import { useStore } from "vuex";

// 获取到vuex的store
const store = useStore();

const formInline = reactive({
  user: " ",
  region: " ",
  date: "",
});
const HandleQuery = () => {
  console.log("submit!");
};

const activeName = ref("first");

// 分页
const totalItems = ref(0);

function handleClick(tab, event) {
  console.log(tab, event);
}

let tableData = ref([]); // 使用 ref 为表格数据定义一个响应式引用
onMounted(() => {
  getTableData();
});
const getTableData = async () => {
  try {
    const res = await store.dispatch("getTableALLDataList");
    console.log("resS:", res);
    tableData.value = res.map((item) => ({
      courseType: item.type,
      courseName: item.name,
      courseID: item.CID,
      courseCredit: item.credit,
      coursePeriod: item.hours,
      courseDepartment: item.department,
      courseInformation: `${item.professor_name}; ${item.location}; ${item.time}`,
      capacitySelectedNumber: `${item.capacity}; ${item.selected}`,
    }));
    totalItems.value = tableData.value.length;
  } catch (error) {
    console.log("error:", error);
  }
};

const AddCourse = reactive({
  credit: " ",
});

function HandleAdd() {
  console.log("add!");
}

// 分页
const currentPage4 = ref(1);
const pageSize4 = ref(25);
const small = ref(false);
const background = ref(false);
const disabled = ref(false);

function handleSizeChange(val) {
  console.log(`${val} items per page`);
}

function handleCurrentChange(val) {
  console.log(`current page: ${val}`);
}
</script>
<style scoped></style>
