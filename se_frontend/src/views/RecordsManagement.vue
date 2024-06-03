<script setup>
import { computed, onMounted, ref, watch } from "vue";
import { ElMessage, ElTree } from "element-plus";
import { Delete, Edit } from "@element-plus/icons-vue";
import {
  deleteReservation,
  fetchBookings,
  submitReservation,
} from "@/api/reservation";

const selectedDay = ref("");

const filterText = ref("");
const treeRef = ref(null);

const defaultProps = {
  children: "children",
  label: "name",
};

watch(filterText, (val) => {
  treeRef.value.filter(val);
});

const filterNode = (value, data) => {
  if (!value) return true;
  return data.label.includes(value);
};

const checkedPlaces = computed(() => {
  return treeRef.value ? treeRef.value.getCheckedKeys(true) : [];
});

const handleSearch = async () => {
  try {
    console.log(checkedPlaces.value);
    const selectedDate = selectedDay.value || undefined;
    const placeIds = checkedPlaces.value;

    let responses;

    if (placeIds.length === 0) {
      // 如果 placeIds 为空，只需根据 selectedDate 的值决定是否发送 date 参数
      const params = {};
      if (selectedDate) {
        params.date = selectedDate;
      }
      responses = [await fetchBookings(params)];
    } else {
      // 如果 placeIds 不为空，对每个 placeId 发送请求，并根据 selectedDate 的值决定是否发送 date 参数
      const fetchPromises = placeIds.map((placeId) => {
        const params = { room_id: placeId };
        if (selectedDate) {
          params.date = selectedDate;
        }
        return fetchBookings(params);
      });

      // 等待所有请求完成
      responses = await Promise.all(fetchPromises);
    }

    // 合并所有响应数据
    const allData = responses.flatMap((response) => response.data);

    // 处理数据并赋值给 displayData.value
    displayData.value = allData.map((item) => {
      return {
        ...item,
        createTime: item.createTime.replace("T", " ").substring(0, 19),
        library: item.room.place,
        room: item.room.name,
        startTime: item.startTime.substring(0, 5),
        endTime: item.endTime.substring(0, 5),
        persons: item.students
          .map((student) => `${student.sid} ${student.name}`)
          .join("\n"),
      };
    });
  } catch (error) {
    ElMessage.error("查询失败，请稍后重试");
  }
};

onMounted(() => {
  handleSearch();
});

const placeData = ref([
  {
    id: 1,
    name: "一丹图书馆",
    createTime: "2021-09-01",
    capacity: 5,
    children: [
      {
        id: 11,
        name: "一楼",
        createTime: "2021-09-01",
        capacity: 5,
      },
      {
        id: 12,
        name: "二楼",
        createTime: "2021-09-01",
        capacity: 5,
      },
    ],
  },
  {
    id: 2,
    name: "琳恩图书馆",
    createTime: "2021-09-02",
    capacity: 5,
    children: [
      {
        id: 21,
        name: "一楼",
        createTime: "2021-09-02",
        capacity: 5,
      },
      {
        id: 22,
        name: "二楼",
        createTime: "2021-09-02",
        capacity: 5,
      },
    ],
  },
  {
    id: 3,
    name: "涵泳图书馆",
    createTime: "2021-09-03",
    capacity: 5,
    children: [
      {
        id: 31,
        name: "一楼",
        createTime: "2021-09-03",
        capacity: 6,
      },
      {
        id: 32,
        name: "二楼",
        createTime: "2021-09-03",
        capacity: 9,
      },
      {
        id: 33,
        name: "三楼",
        createTime: "2021-09-03",
        capacity: 1,
      },
    ],
  },
]);

const reserveData = ref([
  {
    id: 1,
    createTime: "2021-09-01 08:00:00",
    library: "一丹图书馆",
    room: "一楼",
    date: "2021-09-01",
    startTime: "08:00",
    endTime: "10:00",
    persons: "张三",
    remark: "无",
  },
  {
    id: 2,
    createTime: "2021-09-01 10:00:00",
    library: "一丹图书馆",
    room: "二楼",
    date: "2021-09-01",
    startTime: "10:00",
    endTime: "12:00",
    persons: "李四",
    remark: "无",
  },
  {
    id: 3,
    createTime: "2021-09-01 14:00:00",
    library: "琳恩图书馆",
    room: "一楼",
    date: "2021-09-01",
    startTime: "14:00",
    endTime: "16:00",
    persons: "王五",
    remark: "无",
  },
  {
    id: 4,
    createTime: "2021-09-01",
    library: "琳恩图书馆",
    room: "二楼",
    date: "2021-09-01",
    startTime: "16:00",
    endTime: "18:00",
    persons: "赵六",
    remark: "无",
  },
]);

const displayData = ref([...reserveData.value]);

const dialogVisible = ref(false);
const currentEditing = ref({
  id: null,
  createTime: "",
  library: "",
  room: "",
  date: "",
  startTime: "",
  endTime: "",
  persons: "",
  remark: "",
});
const isEditing = ref(false);

const openDialog = (record = {}) => {
  if (record.id) {
    currentEditing.value = { ...record };
    isEditing.value = true;
  } else {
    currentEditing.value = {
      id: null,
      createTime: "",
      library: "",
      room: "",
      date: "",
      startTime: "",
      endTime: "",
      persons: "",
      remark: "",
    };
    isEditing.value = false;
  }
  dialogVisible.value = true;
};

const handleDelete = async (record) => {
  try {
    await deleteReservation(record.id);
    ElMessage.success("删除成功");
    location.reload();
  } catch (error) {
    ElMessage.error("删除失败，请稍后重试");
  }
};

const handleClose = () => {
  dialogVisible.value = false;
};

const saveRecord = async () => {
  if (isEditing.value) {
    const index = reserveData.value.findIndex(
      (record) => record.id === currentEditing.value.id
    );
    if (index !== -1) {
      reserveData.value[index] = { ...currentEditing.value };
    }
  } else {
    const newRecord = {
      ...currentEditing.value,
    };
    // reserveData.value.push(newRecord);
    try {
      await submitReservation(newRecord);
      ElMessage.success("提交成功");
    } catch (error) {
      ElMessage.error("提交失败，请稍后重试");
    }
  }
  handleClose();
};

const selectedLibraryRooms = ref([]);
watch(
  () => currentEditing.value.library,
  (newLibrary) => {
    const library = placeData.value.find((lib) => lib.name === newLibrary);
    selectedLibraryRooms.value = library ? library.children : [];
  }
);

const currentPage = ref(1);
const pageSize = ref(10);

const paginatedData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return displayData.value.slice(start, end);
});

const handleCurrentChange = (page) => {
  currentPage.value = page;
};

const handleSizeChange = (size) => {
  pageSize.value = size;
};
</script>

<template>
  <div class="container">
    <div class="left-container">
      <el-input
        v-model="filterText"
        style="width: 240px"
        placeholder="Filter keyword"
      />

      <el-tree
        ref="treeRef"
        style="max-width: 600px"
        class="filter-tree"
        :data="placeData"
        :props="defaultProps"
        default-expand-all
        :filter-node-method="filterNode"
        show-checkbox
        node-key="id"
      />
    </div>

    <div class="right-container">
      <div class="right-header">
        <el-date-picker
          v-model="selectedDay"
          type="date"
          placeholder="Pick a day"
          size="default"
        />
        <!-- 自动扩展的空白元素 -->
        <div class="spacer"></div>
        <el-button type="primary" plain @click="handleSearch">查询</el-button>
        <el-button type="primary" plain @click="() => openDialog()"
          >添加记录</el-button
        >
      </div>

      <div class="table-wrapper">
        <el-table
          :data="paginatedData"
          style="margin-bottom: 20px; width: 100%"
          stripe
          row-key="id"
          border
        >
          <el-table-column fixed prop="id" label="预约编号" sortable />
          <el-table-column
            fixed
            prop="createTime"
            label="创建时间"
            width="180"
            sortable
          />
          <el-table-column prop="library" label="图书馆" width="120" sortable />
          <el-table-column prop="room" label="讨论间" sortable />
          <el-table-column prop="date" label="预约日期" width="120" sortable />
          <el-table-column prop="startTime" label="起始时间" sortable />
          <el-table-column prop="endTime" label="结束时间" sortable />
          <el-table-column prop="persons" label="预约人" width="180" />
          <el-table-column prop="remark" label="备注" />
          <el-table-column fixed="right" label="操作" width="120">
            <template #default="scope">
              <el-button
                type="primary"
                :icon="Edit"
                circle
                @click="() => openDialog(scope.row)"
              />
              <el-button
                type="danger"
                :icon="Delete"
                circle
                @click="() => handleDelete(scope.row)"
              />
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="footer">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 25, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="displayData.value.length"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        />
      </div>
    </div>
  </div>

  <el-dialog
    v-model="dialogVisible"
    :title="isEditing.valueOf() ? '编辑预约' : '添加预约'"
    :before-close="handleClose"
  >
    <el-form :model="currentEditing" label-width="120px">
      <el-form-item label="预约编号">
        <el-input v-model="currentEditing.id" disabled></el-input>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          type="datetime"
          v-model="currentEditing.createTime"
          disabled
        />
      </el-form-item>
      <el-form-item label="图书馆">
        <el-select v-model="currentEditing.library" placeholder="选择图书馆">
          <el-option
            v-for="library in placeData"
            :key="library.id"
            :label="library.name"
            :value="library.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="讨论间名称">
        <el-select v-model="currentEditing.room" placeholder="选择讨论间">
          <el-option
            v-for="room in selectedLibraryRooms"
            :key="room.id"
            :label="room.name"
            :value="room.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预约日期">
        <el-date-picker
          v-model="currentEditing.date"
          type="date"
          placeholder="选择日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="起始时间">
        <el-time-select
          v-model="currentEditing.startTime"
          style="width: 240px"
          :max-time="currentEditing.endTime"
          placeholder="选择起始时间"
          start="08:00"
          step="00:30"
          end="22:00"
        ></el-time-select>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-time-select
          v-model="currentEditing.endTime"
          style="width: 240px"
          :min-time="currentEditing.startTime"
          placeholder="选择结束时间"
          start="08:00"
          step="00:30"
          end="22:00"
        ></el-time-select>
      </el-form-item>
      <el-form-item label="预约人">
        <el-input v-model="currentEditing.persons"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="currentEditing.remark"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="saveRecord">保存</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
@import "@/style/mixin.scss";
//* {
//  box-sizing: border-box;
//}

.container {
  display: flex;
  gap: 16px;
  height: 617.6px; // 固定container为除去layout.vue中header的高度
}

.left-container {
  width: 240px;
  //background-color: white;
  @include block_bg_color();
  padding: 16px;
}

.right-container {
  flex-grow: 0; // 移除flex-grow
  width: calc(100% - 288px); // 288px 是left-container的总宽度 + gap的宽度
  display: flex;
  flex-direction: column;

  .right-header {
    display: flex;
    align-items: center; /* 垂直居中对齐 */
    gap: 16px; /* 控制各个元素之间的间距 */
    padding: 16px;
    //background-color: white;
    @include block_bg_color();

    .spacer {
      flex-grow: 1;
    }
  }

  .table-wrapper {
    margin-top: 12px;
    //background-color: white;
    @include block_bg_color();
    flex-grow: 1;
    //max-height: 600px;
    //overflow-y: auto;
    //overflow-x: auto;
  }

  .footer {
    display: flex;
    justify-content: flex-end;
    padding: 6px;
    //background-color: white;
    @include block_bg_color();
  }
}
</style>
