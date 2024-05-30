<script setup>
import { ref, reactive, computed, watch, onMounted } from "vue";
import { format } from "date-fns";

import {
  fetchBookings,
  searchStudentBySid,
  submitReservation,
} from "@/api/reservation";
import { ElMessage, ElMessageBox } from "element-plus";
import { useStore } from "vuex";

const store = useStore();

onMounted(async () => {
  await store.dispatch("reservationStore/loadLocations");
  locations.value = store.state.reservationStore.locations;

  if (props.placeId) {
    console.log("props.placeId: ", props.placeId);
    await fetchData(props.placeId);
  }
});

const fetchData = async (placeId) => {
  const selectedLocation = locations.value.find(
    (location) => location.id === placeId
  );
  if (!selectedLocation) {
    console.error(`Location with id ${placeId} not found`);
    return;
  }
  console.log("selectedLocation: ", selectedLocation);

  childrenRooms.value = selectedLocation.children;
  const currentDate = format(new Date(), "yyyy-MM-dd"); // 获取当前日期并格式化
  for (const room of childrenRooms.value) {
    try {
      const res = await fetchBookings({
        date: selectedDay.value || currentDate,
        placeIds: [room.id],
      });
      bookings[room.id] = res.data.reserveList;
      // console.log("bookings: ", bookings); // Debugging line
    } catch (error) {
      console.error("查询失败，请稍后重试");
    }
  }
};

const locations = ref([]);

const selectedDay = ref("");

const disabledDate = (time) => {
  return (
    time.getTime() < Date.now() - 8.64e7 ||
    time.getTime() > Date.now() + 2 * 8.64e7 // 3 days
  );
};

// 定义 props 并设置默认值
// eslint-disable-next-line no-undef
const props = defineProps({
  placeId: {
    type: Number,
    default: 1, // 默认值
  },
});

const childrenRooms = ref([]);

watch(
  () => props.placeId,
  async (newPlaceId) => {
    if (newPlaceId) {
      await fetchData(newPlaceId);
    }
  }
);

// const bookings = ref([]);
const bookings = reactive({});

const hours = computed(() => {
  const startHour = 6;
  const endHour = 18;
  const times = [];
  for (let minute = startHour * 60; minute <= endHour * 60; minute += 30) {
    const hour = Math.floor(minute / 60);
    const mins = minute % 60;
    times.push(`${hour}:${mins.toString().padStart(2, "0")}`);
  }
  return times;
});

// 计算开始的网格线位置
const getGridStart = (timeslot) => {
  const [start] = timeslot.split(" - ").map((time) => time.trim().split(":"));
  // 将小时和分钟转换成网格位置，每小时两格，从6点开始，每30分钟一个格子
  const startHour = parseInt(start[0]);
  const startMinute = parseInt(start[1]);
  return (startHour - 6) * 2 + (startMinute === 30 ? 1 : 0) + 3; // 加3因为网格线从3开始
};

// 计算结束的网格线位置
const getGridEnd = (timeslot) => {
  const [, end] = timeslot.split(" - ").map((time) => time.trim().split(":"));
  // 同样的计算逻辑，但是结束时间对应的网格线是下一个网格线
  const endHour = parseInt(end[0]);
  const endMinute = parseInt(end[1]);
  return (endHour - 6) * 2 + (endMinute === 30 ? 1 : 0) + 4; // 结束时间对应的网格线需要比实际时间多1
};

const dialogVisible = ref(false);
const currentBooking = ref({});

const openDialog = (booking) => {
  currentBooking.value = booking;
  dialogVisible.value = true;
};

const handleClose = (done) => {
  const isFormEmpty = Object.values(form).every(
    (value) =>
      value === "" ||
      value === null ||
      value === undefined ||
      (Array.isArray(value) && value.length === 0)
  );
  if (isFormEmpty) {
    done();
  } else {
    ElMessageBox.confirm("可能还有未保存的数据，确定关闭吗？")
      .then(() => {
        // 用户点击确定按钮触发的分支
        done(); // el-dialog的回调函数，用于关闭对话框
        resetForm();
      })
      .catch(() => {
        // 用户点击取消按钮触发的分支
      });
  }
};

const addSelectedPerson = (person) => {
  form.addedPersons.push(person.value);
  form.searchState = "";
};

const querySearchAsync = async (studentId, cb) => {
  if (studentId.length !== 8) {
    //学号长度不符合要求，取消请求
    cb([]); // 传递空数组，无需进一步处理
    return; // 提前返回，不执行后续代码
  }

  try {
    const response = await searchStudentBySid(studentId);
    console.log(response.data.name);
    const result = response.data.sid + " " + response.data.name;
    cb([{ value: result }]);
  } catch (error) {
    console.error("搜索学生信息请求失败:", error);
    cb([]); // 在错误情况下传递空数组或适当的错误处理
  }
};

// 表单引用
const formRef = ref(null);

// 表单数据
const form = reactive({
  startTime: "",
  endTime: "",
  searchState: "",
  addedPersons: [12345678, 23456789],
});

// 表单校验规则
const rules = reactive({
  startTime: [{ required: true, message: "请选择开始时间", trigger: "blur" }],
  endTime: [
    { required: true, message: "请选择结束时间", trigger: "blur" },
    {
      validator: (rule, value, callback) => {
        if (value <= form.startTime) {
          callback(new Error("结束时间必须大于开始时间"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
  addedPersons: [
    {
      type: "array",
      required: true,
      min: 1,
      message: "至少添加一位人员",
      trigger: "change",
    },
  ],
});

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        console.log("currentBooking.value: ", currentBooking.value);
        const currentDate = format(new Date(), "yyyy-MM-dd"); // 获取当前日期并格式化
        await submitReservation({
          room_id: currentBooking.value.id,
          date: selectedDay.value || currentDate,
          start_time: form.startTime,
          end_time: form.endTime,
          persons: form.addedPersons.map((person) =>
            person.toString().slice(0, 8)
          ),
        });
        ElMessage.success("提交成功");
        dialogVisible.value = false;
        resetForm();
      } catch (error) {
        // 错误处理
        ElMessage.error("提交失败，请稍后重试");
        console.log(error);
      }
    } else {
      ElMessage.warning("表单验证失败，请检查输入");
    }
  });
};

// 函数：重置表单
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
</script>

<template>
  <div class="filter-bar">
    <el-date-picker
      v-model="selectedDay"
      type="date"
      placeholder="Pick a day"
      size="default"
      :disabledDate="disabledDate"
    />
    <!-- 自动扩展的空白元素 -->
    <div class="spacer"></div>
    <el-button type="primary" plain @click="fetchBookings">查询</el-button>
  </div>
  <div class="body-container">
    <div class="scrollable-panel">
      <div class="time-header">
        <div>场地</div>
        <div>操作</div>
        <div v-for="hour in hours" :key="hour">{{ hour }}</div>
      </div>
      <div class="booking-row" v-for="room in childrenRooms" :key="room.id">
        <div
          :style="{
            'grid-column-start': 1,
            'grid-column-end': 2,
          }"
        >
          {{ room.name }}
        </div>
        <div>
          <el-button type="primary" plain @click="openDialog(room)">
            预约
          </el-button>
        </div>
        <div
          v-for="booking in bookings[room.id]"
          :key="booking.id"
          class="booking-slot"
          :style="{
            'grid-column-start': getGridStart(booking.timeslot),
            'grid-column-end': getGridEnd(booking.timeslot),
          }"
        >
          {{ booking.status }}
        </div>
      </div>
    </div>
  </div>

  <el-dialog
    v-model="dialogVisible"
    :title="currentBooking.place + '-' + currentBooking.name || '预约'"
    :before-close="handleClose"
  >
    <el-form ref="formRef" :model="form" :rules="rules">
      <el-form-item label="预约时间" required>
        <el-form-item prop="startTime">
          <el-time-select
            v-model="form.startTime"
            style="width: 240px"
            :max-time="form.endTime"
            placeholder="Start time"
            start="08:00"
            step="00:30"
            end="22:00"
          />
        </el-form-item>
        <el-form-item prop="endTime">
          <el-time-select
            v-model="form.endTime"
            style="width: 240px"
            :min-time="form.startTime"
            placeholder="End time"
            start="08:00"
            step="00:30"
            end="22:00"
          />
        </el-form-item>
      </el-form-item>
      <el-form-item label="输入学号添加人员" required>
        <el-autocomplete
          v-model="form.searchState"
          :fetch-suggestions="querySearchAsync"
          placeholder="Add people"
          @select="addSelectedPerson"
        ></el-autocomplete>
      </el-form-item>
      <el-form-item label="已添加的人员：" prop="addedPersons">
        <div v-if="form.addedPersons.length > 0">
          <ul>
            <li v-for="person in form.addedPersons" :key="person.id">
              {{ person }}
            </li>
          </ul>
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="resetForm(true)">Reset</el-button>
        <el-button type="primary" @click="submitForm"> Confirm </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
@import "@/style/mixin.scss";
.filter-bar {
  display: flex;
  align-items: center;
  padding: 10px;
  //background-color: #f5f5f5;
  border-bottom: 1px solid #ccc;
  @include block_bg_color();

  .spacer {
    flex: 1;
  }
}

.body-container {
  width: 100%;
  height: calc(100% - 52.8px); // 52.8px 是 filter-bar 的高度
  background-color: white;
  overflow: auto;
  margin-top: 10px; // 为了让筛选框和预约展示表格有一定的间距
}

.scrollable-panel {
  border-collapse: collapse; // 合并边框，目前好像没用
  display: grid;
  grid-template-columns: 150px 100px repeat(25, 60px);
  font-size: 14px;

  // 设置字体颜色为半透明黑色
  color: rgba(0, 0, 0, 0.8);

  // 为 scrollable-panel 添加阴影
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); // 调整这个值以获得不同的阴影效果

  .time-header div {
    border: 1px solid #ccc;
    padding: 0.5rem;
    text-align: center;
    //background-color: #f5f5f5;
    @include block_bg_color();
    @include text_color();
  }

  .booking-row div {
    border: 1px solid #ccc;
    padding: 0.5rem;

    // 为了让文字垂直居中
    display: flex;
    align-items: center; // 垂直居中
    justify-content: center; // 水平居中
  }

  .booking-row {
    display: contents;
  }

  .time-header {
    display: contents;
  }

  .booking-slot {
    background-color: lightskyblue;
    border-radius: 4px;
    color: whitesmoke;
  }
}
</style>
