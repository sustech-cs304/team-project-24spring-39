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
import { useI18n } from "vue-i18n";

const { t } = useI18n();
const store = useStore();

onMounted(async () => {
  await store.dispatch("reservationStore/loadLocations");
  locations.value = store.state.reservationStore.locations;

  if (props.placeId) {
    // console.log("props.placeId: ", props.placeId);
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
  // console.log("selectedLocation: ", selectedLocation);

  childrenRooms.value = selectedLocation.children;
  const currentDate = format(new Date(), "yyyy-MM-dd"); // 获取当前日期并格式化
  for (const room of childrenRooms.value) {
    try {
      const res = await fetchBookings({
        date: selectedDay.value || currentDate,
        room_id: room.id,
      });
      bookings[room.id] = res.data;
    } catch (error) {
      console.error("查询失败，请稍后重试");
    }
  }
};

const locations = ref([]);

const selectedDay = ref("");

const disabledDate = (time) => {
  return (
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
const getGridStart = (startTime) => {
  // 将startTime按照":"分割为小时和分钟
  const [startHour, startMinute] = startTime
    .trim()
    .split(":")
    .map((time) => parseInt(time));
  // 计算网格线位置
  return (startHour - 6) * 2 + (startMinute === 30 ? 1 : 0) + 3; // 加3因为网格线从3开始
};

// 计算结束的网格线位置
const getGridEnd = (endTime) => {
  // 将endTime按照":"分割为小时和分钟
  const [endHour, endMinute] = endTime
    .trim()
    .split(":")
    .map((time) => parseInt(time));
  // 计算网格线位置
  return (endHour - 6) * 2 + (endMinute === 30 ? 1 : 0) + 3; // 现在的写法结束时间对应的网格线【不】需要比实际时间多1
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
    ElMessageBox.confirm(t("confirmClose"))
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
  addedPersons: [],
});

// 表单校验规则
const rules = reactive({
  startTime: [
    {
      required: true,
      message: t("validation.requiredStartTime"),
      trigger: "blur",
    },
  ],
  endTime: [
    {
      required: true,
      message: t("validation.requiredEndTime"),
      trigger: "blur",
    },
    {
      validator: (rule, value, callback) => {
        if (value <= form.startTime) {
          callback(new Error(t("validation.endTimeGreaterThanStartTime")));
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
      message: t("validation.requiredAddedPersons"),
      trigger: "change",
    },
  ],
});

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const currentDate = format(new Date(), "yyyy-MM-dd"); // 获取当前日期并格式化
        const selectedDate = selectedDay.value || currentDate;
        const newStartTime = new Date(`${selectedDate}T${form.startTime}:00`);
        const newEndTime = new Date(`${selectedDate}T${form.endTime}:00`);
        // console.log("newStartTime");
        // console.log(newStartTime);
        // console.log("newEndTime");
        // console.log(newEndTime);

        // 获取当前房间的所有预约记录
        const roomBookings = bookings[currentBooking.value.id] || [];

        // console.log(roomBookings);

        // 检查新预约时间是否与现有预约时间冲突
        const isConflict = roomBookings.some((booking) => {
          // console.log(booking);
          // console.log(booking.value);
          const bookingStartTime = new Date(
            `${selectedDate}T${booking.startTime}`
          );
          // console.log(selectedDate);
          // console.log(booking);
          // console.log(booking.value.startTime);
          // console.log(bookingStartTime);
          const bookingEndTime = new Date(`${selectedDate}T${booking.endTime}`);
          // console.log(bookingEndTime);
          return (
            (newStartTime >= bookingStartTime &&
              newStartTime < bookingEndTime) ||
            (newEndTime > bookingStartTime && newEndTime <= bookingEndTime) ||
            (newStartTime <= bookingStartTime && newEndTime >= bookingEndTime)
          );
        });

        if (isConflict) {
          ElMessage.error(t("messages.reservationConflict"));
          return;
        }

        await submitReservation({
          room_id: currentBooking.value.id,
          date: selectedDay.value || currentDate,
          start_time: form.startTime,
          end_time: form.endTime,
          persons: form.addedPersons.map((person) =>
            person.toString().slice(0, 8)
          ),
        });
        ElMessage.success(t("messages.submitSuccess"));
        dialogVisible.value = false;
        resetForm();
        location.reload();
      } catch (error) {
        // 错误处理
        ElMessage.error(t("messages.submitFail"));
        console.log(error);
      }
    } else {
      ElMessage.warning(t("messages.formValidationFail"));
    }
  });
};

// 重置表单
const resetForm = (showMessage = false) => {
  formRef.value.resetFields();
  if (showMessage) {
    ElMessage({
      showClose: true,
      message: t("messages.formReset"),
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
      :placeholder="$t('pickADay')"
      size="default"
      :disabledDate="disabledDate"
      value-format="YYYY-MM-DD"
    />
    <!-- 自动扩展的空白元素 -->
    <div class="spacer"></div>
    <el-button type="primary" plain @click="fetchData(props.placeId)">{{
      $t("query")
    }}</el-button>
  </div>
  <div class="body-container">
    <div class="scrollable-panel">
      <div class="time-header">
        <div>{{ $t("room") }}</div>
        <div>{{ $t("operation") }}</div>
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
            {{ $t("reserve") }}
          </el-button>
        </div>
        <div
          v-for="booking in bookings[room.id]"
          :key="booking.id"
          class="booking-slot"
          :style="{
            'grid-column-start': getGridStart(booking.startTime),
            'grid-column-end': getGridEnd(booking.endTime),
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
      <el-form-item :label="$t('appointmentTime')" required>
        <el-form-item prop="startTime">
          <el-time-select
            v-model="form.startTime"
            style="width: 240px"
            :max-time="form.endTime"
            placeholder="Start time"
            start="06:00"
            step="00:30"
            end="18:00"
          />
        </el-form-item>
        <el-form-item prop="endTime">
          <el-time-select
            v-model="form.endTime"
            style="width: 240px"
            :min-time="form.startTime"
            placeholder="End time"
            start="06:00"
            step="00:30"
            end="18:00"
          />
        </el-form-item>
      </el-form-item>
      <el-form-item :label="$t('addPersonLabel')" required>
        <el-autocomplete
          v-model="form.searchState"
          :fetch-suggestions="querySearchAsync"
          placeholder="Add people"
          @select="addSelectedPerson"
        ></el-autocomplete>
      </el-form-item>
      <el-form-item :label="$t('addedPerson')" prop="addedPersons">
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
        <el-button @click="resetForm(true)">{{ $t("reset") }}</el-button>
        <el-button type="primary" @click="submitForm">
          {{ $t("confirm") }}
        </el-button>
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
  //background-color: white;
  @include block_bg_color();
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
