<template>
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
  </el-tabs>
</template>

<script setup>
import { ref, onMounted, reactive } from "vue";
import { fetchDataByType } from "@/api/course";
import { defineProps, watch } from "vue";

const props = defineProps({
  type: String,
});

const titles = [
  { id: 1, label: "全部", name: "AllCourses", type: "All" },
  { id: 2, label: "通识必修", name: "BiXiuCourses", type: "BiXiu" },
  { id: 3, label: "通识选修", name: "XuanXiuCourses", type: "XuanXiu" },
];

const activeTab = ref(titles[0].name); // 默认选中第一个标签页
const tableData = ref([]);

const tableColumns = [
  { prop: "courseType", label: "课程类型", width: "150" },
  { prop: "courseName", label: "课程名称", width: "120" },
  { prop: "courseID", label: "课程代码", width: "120" },
  { prop: "courseCredit", label: "学分", width: "120" },
  { prop: "coursePeriod", label: "学时", width: "120" },
  { prop: "courseDepartment", label: "课程部门", width: "120" },
  { prop: "courseInformation", label: "课程信息", width: "120" },
  { prop: "capacitySelectedNumber", label: "容量/已选", width: "120" },
];

const cachedData = ref({});
const totalItems = ref(0);

async function handleTabClick(tab) {
  const tabName = tab.props.name;
  const type = titles.find((title) => title.name === tabName)?.type;
  console.log("tabName:", tabName, "type:", type);
  await fetchData(type);
}

onMounted(async () => {
  await handleTabClick({ props: { name: "AllCourses" } });
});

const fetchData = async (type) => {
  if (type !== undefined && !cachedData.value[type]) {
    try {
      const response = await fetchDataByType(type);
      console.log("response:", response.data);
      cachedData.value[type] = response.data.map((item) => ({
        courseType: item.type,
        courseName: item.name,
        courseID: item.CID,
        courseCredit: item.credit,
        coursePeriod: item.hours,
        courseDepartment: item.department,
        courseInformation: `${item.professor_name}; ${item.location}; ${item.time}`,
        capacitySelectedNumber: `${item.capacity}; ${item.selected}`,
      }));
      totalItems.value = cachedData.value[type].length;
      handleCurrentChange(1); // 初始化或刷新数据后显示第一页
    } catch (error) {
      console.error("Failed to fetch data:", error);
    }
  }
  tableData.value = cachedData.value[type] || [];
};

watch(() => props.type, fetchData, { immediate: true });

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

function handleCurrentChange(newPage) {
  console.log(`current page: ${newPage}`);
  const type = titles.find((title) => title.name === activeTab.value)?.type;
  const allData = cachedData.value[type] || [];
  const startIndex = (newPage - 1) * pageSize4.value;
  tableData.value = allData.slice(startIndex, startIndex + pageSize4.value);
}
</script>

<style scoped lang="scss"></style>
