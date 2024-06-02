<script setup>
import { ref, onMounted } from "vue";
import { Delete, Edit } from "@element-plus/icons-vue";
import { useStore } from "vuex";
import {
  deleteBuilding,
  deleteRoom,
  submitBuilding,
  submitRoom,
  updateBuilding,
  updateRoom,
} from "@/api/reservation";
import { ElMessage } from "element-plus";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

const store = useStore();

const searchRoom = ref("");
const searchLibrary = ref("");
const searchMin = ref(null);
const searchMax = ref(null);

// const tableData = ref([
//   {
//     id: 1,
//     name: "一丹图书馆",
//     createTime: "2021-09-01",
//     capacity: 5,
//     children: [
//       {
//         id: 11,
//         name: "一楼",
//         createTime: "2021-09-01",
//         capacity: 5,
//       },
//       {
//         id: 12,
//         name: "二楼",
//         createTime: "2021-09-01",
//         capacity: 5,
//       },
//     ],
//   },
//   {
//     id: 2,
//     name: "琳恩图书馆",
//     createTime: "2021-09-02",
//     capacity: 5,
//     children: [
//       {
//         id: 21,
//         name: "一楼",
//         createTime: "2021-09-02",
//         capacity: 5,
//       },
//       {
//         id: 22,
//         name: "二楼",
//         createTime: "2021-09-02",
//         capacity: 5,
//       },
//     ],
//   },
//   {
//     id: 3,
//     name: "涵泳图书馆",
//     createTime: "2021-09-03",
//     capacity: 5,
//     children: [
//       {
//         id: 31,
//         name: "一楼",
//         createTime: "2021-09-03",
//         capacity: 6,
//       },
//       {
//         id: 32,
//         name: "二楼",
//         createTime: "2021-09-03",
//         capacity: 9,
//       },
//       {
//         id: 33,
//         name: "三楼",
//         createTime: "2021-09-03",
//         capacity: 1,
//       },
//     ],
//   },
// ]);

const tableData = ref([]);

const displayData = ref([...tableData.value]);

// 使用onMounted在组件挂载时获取数据
onMounted(async () => {
  await store.dispatch("reservationStore/loadLocations");
  tableData.value = store.state.reservationStore.locations;
  displayData.value = [...tableData.value];
  // 打印 displayData.value 数组中每一项的 children 字段
  // displayData.value.forEach((item) => {
  //   console.log(item.children);
  // });
});

// todo: 使用gpt生成
const handleSearch = () => {
  displayData.value = tableData.value
    .map((library) => {
      const matchesLibrary = searchLibrary.value
        ? library.id === searchLibrary.value
        : true;

      // 过滤子房间
      const filteredChildren = library.children.filter((child) => {
        const matchesRoom = searchRoom.value
          ? child.name.includes(searchRoom.value)
          : true;
        const matchesCapacity =
          (searchMin.value === null || child.capacity >= searchMin.value) &&
          (searchMax.value === null || child.capacity <= searchMax.value);
        return matchesRoom && matchesCapacity;
      });

      if (matchesLibrary && filteredChildren.length > 0) {
        return {
          ...library,
          children: filteredChildren,
        };
      } else if (
        matchesLibrary &&
        !searchRoom.value &&
        !searchMin.value &&
        !searchMax.value
      ) {
        return library;
      }
      return null;
    })
    .filter((library) => library !== null);
};

const dialogVisible = ref(false);
const currentEditting = ref({
  type: "library", // 默认为'library'
  name: "",
  state: "开放", // 默认为'开放'
  capacity: 0,
});
const isEditing = ref(false);

const openDialog = (place = {}) => {
  if (place.id) {
    currentEditting.value = {
      ...place,
      type: place.children ? "library" : "room",
      capacity: place.children
        ? place.children.reduce((sum, child) => sum + child.capacity, 0)
        : place.capacity,
    };
    isEditing.value = true;
  } else {
    currentEditting.value = {
      type: "library",
      name: "",
      state: "开放",
      capacity: 0,
    };
    isEditing.value = false;
  }
  dialogVisible.value = true;
};

const handleDelete = async (location) => {
  try {
    console.log(location);
    if (location.place) {
      await deleteRoom(location.id);
    } else {
      await deleteBuilding(location.name);
    }
    ElMessage.success(t("deleteSuccess"));
  } catch (error) {
    console.log(error);
    ElMessage.error(error);
  }
};

const handleClose = () => {
  dialogVisible.value = false;
};

const setLocationType = (type) => {
  currentEditting.value.type = type;
};

const setLocationState = (state) => {
  currentEditting.value.state = state;
};

const saveLocation = async () => {
  if (isEditing.value) {
    const index = tableData.value.findIndex(
      (library) => library.id === currentEditting.value.id
    );
    if (index !== -1) {
      tableData.value[index] = { ...currentEditting.value };
    }

    console.log(currentEditting.value);
    if (currentEditting.value.type === "library") {
      await updateBuilding({
        name: currentEditting.value.name,
        status: currentEditting.value.state,
      });
    }
    if (currentEditting.value.type === "room") {
      await updateRoom({
        room_id: currentEditting.value.id,
        status: currentEditting.value.state,
      });
    }
  } else {
    const newLocation = {
      ...currentEditting.value,
      // children: currentEditting.value.type === "library" ? [] : undefined,
    };
    if (newLocation.type === "library") {
      newLocation.capacity = 0;
    }
    try {
      if (newLocation.type === "library") {
        await submitBuilding({
          name: newLocation.name,
          status: newLocation.state,
        });
      }
      if (newLocation.type === "room") {
        await submitRoom({
          place: newLocation.name,
        });
      }
      ElMessage.success(t("submitSuccess"));
    } catch (error) {
      ElMessage.error(t("submitFail"));
    }
  }
  handleClose();
};

// onMounted(async () => {
//   try {
//     const res = await fetchLocations();
//     tableData.value = res.data;
//   } catch (error) {
//     ElMessage({
//       showClose: true,
//       message: error.message || "获取地点信息失败",
//       type: "error",
//     });
//   }
// });
</script>

<template>
  <div class="container">
    <div class="header">
      <el-input
        v-model="searchRoom"
        :placeholder="$t('inputPlaceholder')"
        class="location-search"
      >
        <template #prepend>
          <el-select
            v-model="searchLibrary"
            :placeholder="$t('selectPlaceholder')"
            style="width: 115px"
          >
            <el-option
              v-for="library in tableData"
              :key="library.id"
              :label="library.name"
              :value="library.id"
            />
          </el-select>
        </template>
      </el-input>

      <el-input-number
        v-model="searchMin"
        :min="1"
        :max="10"
        :label="$t('number')"
        :placeholder="$t('minCapacity')"
        controls-position="right"
      />
      <el-input-number
        v-model="searchMax"
        :min="1"
        :max="10"
        :label="$t('number')"
        :placeholder="$t('maxCapacity')"
        controls-position="right"
      />
      <!-- 自动扩展的空白元素 -->
      <div class="spacer"></div>
      <el-button type="primary" plain @click="handleSearch">{{
        t("search")
      }}</el-button>
      <el-button type="primary" plain @click="openDialog">{{
        t("addLocation")
      }}</el-button>
    </div>

    <div class="table">
      <!--      <el-scrollbar class="custom-scrollbar">-->
      <el-table
        :data="displayData"
        style="width: 100%"
        stripe
        row-key="id"
        border
        default-expand-all
      >
        <el-table-column fixed prop="name" :label="t('name')" sortable />
        <el-table-column prop="status" :label="t('status')" sortable />
        <el-table-column prop="capacity" :label="t('capacity')" sortable />
        <el-table-column prop="remark" :label="t('remark')" />
        <el-table-column fixed="right" :label="t('action')">
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
      <!--      </el-scrollbar>-->
    </div>

    <div class="footer">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 25, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="400"
      />
    </div>
  </div>

  <el-dialog
    v-model="dialogVisible"
    :title="isEditing ? $t('dialog.editLocation') : $t('dialog.addLocation')"
    :before-close="handleClose"
  >
    <el-form :model="currentEditting" label-width="120px">
      <el-form-item :label="$t('dialog.locationType')">
        <el-button-group>
          <el-button
            :type="currentEditting.type === 'library' ? 'primary' : ''"
            @click="() => setLocationType('library')"
            :disabled="isEditing.value"
            >{{ $t("dialog.library") }}
          </el-button>
          <el-button
            :type="currentEditting.type === 'room' ? 'primary' : ''"
            @click="() => setLocationType('room')"
            :disabled="isEditing.value"
            >{{ $t("dialog.room") }}
          </el-button>
        </el-button-group>
      </el-form-item>
      <el-form-item :label="$t('dialog.locationName')">
        <el-input v-model="currentEditting.name"></el-input>
      </el-form-item>
      <el-form-item :label="$t('dialog.status')">
        <el-button-group>
          <el-button
            :type="currentEditting.state === '开放' ? 'primary' : ''"
            @click="() => setLocationState('开放')"
            >{{ $t("dialog.open") }}
          </el-button>
          <el-button
            :type="currentEditting.state === '关闭' ? 'primary' : ''"
            @click="() => setLocationState('关闭')"
            >{{ $t("dialog.closed") }}
          </el-button>
        </el-button-group>
      </el-form-item>
      <el-form-item :label="$t('dialog.capacity')">
        <el-input-number
          v-model="currentEditting.capacity"
          controls-position="right"
          :disabled="currentEditting.type === 'library'"
        ></el-input-number>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">{{ $t("dialog.cancel") }}</el-button>
        <el-button type="primary" @click="saveLocation">{{
          $t("dialog.save")
        }}</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
@import "@/style/mixin.scss";
.container {
  display: flex;
  flex-direction: column;
  height: 617.6px; // 固定container为除去layout.vue中header的高度
}

.header {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  gap: 16px; /* 控制各个元素之间的间距 */
  padding: 16px;
  //background-color: white;
  @include block_bg_color();

  .location-search {
    width: 300px;
  }
  .spacer {
    flex-grow: 1;
  }
}

.table {
  margin-top: 12px;
  //height: 477px; /* 最大高度为 500px */
  flex-grow: 1;
  overflow: auto; /* 超出部分显示滚动条 */
  //overflow: hidden; /* 隐藏默认滚动条 */
  //background-color: white;
  @include block_bg_color();

  //.custom-scrollbar {
  //  height: 100%;
  //}
  //
  //.el-scrollbar__wrap {
  //  max-height: 100%; /* 使滚动条高度占满父容器 */
  //  overflow: hidden !important; /* 确保隐藏默认滚动条 */
  //}
  //
  //.el-scrollbar__thumb {
  //  background: deepskyblue;
  //}
  //
  ///* 自定义滚动条样式 */
  //.el-scrollbar__bar {
  //  z-index: 1;
  //  width: 8px;
  //  background-color: transparent;
  //}
}

.footer {
  display: flex;
  justify-content: flex-end;
  padding: 6px;
  //background-color: white;
  @include block_bg_color();
}
</style>
