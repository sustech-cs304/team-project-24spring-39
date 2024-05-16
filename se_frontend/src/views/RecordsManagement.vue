<script setup>
import { ref, watch, computed } from "vue";
import { ElMessage, ElTree } from "element-plus";
import { Delete, Edit } from "@element-plus/icons-vue";
import { fetchBookings } from "@/api/reservation";

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
    const response = await fetchBookings({
      date: selectedDay.value || undefined,
      placeIds: checkedPlaces.value.length ? checkedPlaces.value : undefined,
    });
    displayData.value = response.data;
  } catch (error) {
    ElMessage.error("查询失败，请稍后重试");
  }
};

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

const handleClose = () => {
  dialogVisible.value = false;
};

const saveRecord = () => {
  if (isEditing.value) {
    const index = reserveData.value.findIndex(
      (record) => record.id === currentEditing.value.id
    );
    if (index !== -1) {
      reserveData.value[index] = { ...currentEditing.value };
    }
  } else {
    const newId = reserveData.value.length + 1;
    const newRecord = {
      ...currentEditing.value,
      id: newId,
    };
    reserveData.value.push(newRecord);
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
</script>

<template>
  <div class="container">
    <div class="left-part">
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
        node-key="name"
      />
    </div>
    <div class="right-part">
      <div class="right-header">
        <el-date-picker
          v-model="selectedDay"
          type="date"
          placeholder="Pick a day"
          size="default"
        />
        <el-button type="primary" plain @click="handleSearch">查询</el-button>
        <el-button type="primary" plain @click="() => openDialog()"
          >添加记录</el-button
        >
      </div>

      <div class="table-wrapper">
        <el-table
          :data="displayData"
          style="margin-bottom: 20px"
          row-key="id"
          border
        >
          <el-table-column fixed prop="id" label="预约编号" sortable />
          <el-table-column fixed prop="createTime" label="创建时间" sortable />
          <el-table-column fixed prop="library" label="图书馆" sortable />
          <el-table-column fixed prop="room" label="讨论间名称" sortable />
          <el-table-column prop="date" label="预约日期" sortable />
          <el-table-column prop="startTime" label="起始时间" sortable />
          <el-table-column prop="endTime" label="结束时间" sortable />
          <el-table-column prop="persons" label="预约人" />
          <el-table-column prop="remark" label="备注" />
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button
                type="primary"
                :icon="Edit"
                circle
                @click="() => openDialog(scope.row)"
              />
              <el-button type="danger" :icon="Delete" circle />
            </template>
          </el-table-column>
        </el-table>
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
.container {
  display: flex;
  gap: 16px;
}

.left-part {
  flex: 20%;
}

.right-part {
  flex: 80%;
}

.table-wrapper {
  max-height: 600px;
  overflow-y: auto;
  overflow-x: auto;
}
</style>
