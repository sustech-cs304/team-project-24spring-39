<script setup>
import { ref, reactive, computed } from "vue";
import { useStore } from "vuex";
import HorizonTimeLine from "@/component/Home/HorizonTimeLine.vue";
import { format } from "date-fns";
import { ElMessage, ElMessageBox } from "element-plus";

import { fetchTodoList, submitTodo } from "@/api/home";

const store = useStore();

const circleUrl = ref(
  "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
);

const userInfo = computed(() => store.state.userStore.userInfo);

// //todolist
// const generateData = () => {
//   const data = [];
//   for (let i = 1; i <= 15; i++) {
//     data.push({
//       key: i,
//       label: `Option ${i}`,
//       disabled: i % 4 === 0,
//     });
//   }
//   return data;
// };
//
// const data = ref(generateData());
// const rightValue = ref([1]);
//
// // 右侧列表元素变化时触发（目前没用）
// const handleChange = (value, direction, movedKeys) => {
//   console.log(value, direction, movedKeys);
// };

const timelineData = ref([
  {
    year: 2021,
  },
  {
    year: 2022,
  },
  {
    year: 2023,
  },
  {
    year: 2024,
  },
]);

const activeTab = ref("selection");

const tables = ref({
  selection: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
    {
      date: "2016-05-01",
      name: "王小虎",
    },
    {
      date: "2016-05-03",
      name: "王小虎",
    },
  ],
  evaluation: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
    {
      date: "2016-05-01",
      name: "王小虎",
    },
    {
      date: "2016-05-03",
      name: "王小虎",
    },
  ],
  forum: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
    {
      date: "2016-05-01",
      name: "王小虎",
    },
    {
      date: "2016-05-03",
      name: "王小虎",
    },
  ],
  reservation: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
    {
      date: "2016-05-01",
      name: "王小虎",
    },
    {
      date: "2016-05-03",
      name: "王小虎",
    },
  ],
  self: [
    {
      date: "2016-05-02",
      name: "王小虎",
    },
    {
      date: "2016-05-04",
      name: "王小虎",
    },
  ],
});

const dialogVisible = ref(false);
const openDialog = () => {
  dialogVisible.value = true;
};
const formRef = ref(null);
const rules = reactive({
  content: [
    { required: true, message: "Content is required", trigger: "blur" },
  ],
  deadline: [
    { required: true, message: "Deadline is required", trigger: "blur" },
  ],
});
const todoForm = ref({
  content: "",
  deadline: "",
});
const shortcuts = [
  {
    text: "Tomorrow",
    value: () => {
      const date = new Date();
      date.setDate(date.getDate() + 1);
      return date;
    },
  },
  {
    text: "3 days later",
    value: () => {
      const date = new Date();
      date.setDate(date.getDate() + 3);
      return date;
    },
  },
];
const disabledDate = (time) => {
  return time.getTime() < Date.now() - 8.64e7;
};
const handleClose = (done) => {
  const isFormEmpty = Object.values(todoForm).every(
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
// 重置表单
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
const handleClick = async (tab) => {
  try {
    const response = await fetchTodoList(tab);
    tables.value[tab] = response.data;
  } catch (error) {
    ElMessage.error("获取数据失败，请稍后重试");
  }
};
const saveTodo = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await submitTodo({
          content: todoForm.value.content,
          endTime: format(todoForm.value.deadline, "yyyy-MM-dd HH:mm:ss"),
          SID: "12345678",
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
</script>

<template>
  <el-row class="upon-timeline" justify="space-evenly">
    <!--    头像和用户信息-->
    <el-col :span="6" style="margin-top: 30px">
      <el-card class="card">
        <div class="card-body">
          <el-avatar :size="75" :src="circleUrl" />
          <div class="user-info">
            <p>{{ userInfo.name }}</p>
            <p>{{ userInfo.SID }}</p>
            <p>{{ userInfo.major }}</p>
          </div>
        </div>
      </el-card>
    </el-col>
    <!--    todolist-->
    <!--    <el-col :span="12">-->
    <!--      <el-transfer-->
    <!--        class="transfer-pane"-->
    <!--        v-model="rightValue"-->
    <!--        style="text-align: left; display: inline-block"-->
    <!--        filterable-->
    <!--        :left-default-checked="[2, 3]"-->
    <!--        :right-default-checked="[1]"-->
    <!--        :titles="['Source', 'Target']"-->
    <!--        :button-texts="['To left', 'To right']"-->
    <!--        :format="{-->
    <!--          noChecked: '${total}',-->
    <!--          hasChecked: '${checked}/${total}',-->
    <!--        }"-->
    <!--        :data="data"-->
    <!--        @change="handleChange"-->
    <!--      >-->
    <!--        <template #default="{ option }">-->
    <!--          <span>{{ option.key }} - {{ option.label }}</span>-->
    <!--        </template>-->
    <!--        <template #left-footer>-->
    <!--          <el-button class="transfer-footer" size="small">Operation</el-button>-->
    <!--        </template>-->
    <!--        <template #right-footer>-->
    <!--          <el-button class="transfer-footer" size="small">Operation</el-button>-->
    <!--        </template>-->
    <!--      </el-transfer>-->
    <!--    </el-col>-->
  </el-row>
  <!--  时间轴-->
  <el-row>
    <HorizonTimeLine class="h-timeline" :timelineData="timelineData" />
  </el-row>
  <!--  消息提醒-->
  <el-row class="message-reminding">
    <el-button type="primary" plain @click="openDialog">Add Todo</el-button>
    <el-tabs
      v-model="activeTab"
      tab-position="left"
      class="tabs"
      @tab-click="handleClick"
    >
      <el-tab-pane label="选课" name="selection"></el-tab-pane>
      <el-tab-pane label="评教" name="evaluation"></el-tab-pane>
      <el-tab-pane label="课程论坛" name="forum"></el-tab-pane>
      <el-tab-pane label="自习室预约" name="reservation"></el-tab-pane>
      <el-tab-pane label="自定义" name="self">Self Made</el-tab-pane>
    </el-tabs>
    <el-table class="table" :data="tables[activeTab]">
      <el-table-column prop="endTime" label="endTime" />
      <el-table-column prop="content" label="content" />
      <el-table-column prop="createTime" label="createTime" />
      <!-- Add more columns as needed -->
    </el-table>
  </el-row>

  <el-dialog
    v-model="dialogVisible"
    title="添加自定义待办"
    :before-close="handleClose"
  >
    <el-form ref="formRef" :model="todoForm" :rules="rules" label-width="120px">
      <el-form-item label="内容" prop="content">
        <el-input v-model="todoForm.content" />
      </el-form-item>
      <el-form-item label="提醒时间" prop="deadline">
        <el-date-picker
          v-model="todoForm.deadline"
          type="datetime"
          placeholder="Select date and time"
          :shortcuts="shortcuts"
          :disabledDate="disabledDate"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="resetForm(true)">Reset</el-button>
        <el-button type="primary" @click="saveTodo">添加</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="scss">
.upon-timeline {
  display: flex;

  .card {
    align-items: center;
    //padding: 20px;
    height: 171.2px;
    box-sizing: border-box;

    display: flex;
    justify-content: center;

    // el-card使用flex没用，因为里面还有一个el-card__body,所以我们自己嵌套一层div，在这个div上使用flex
    .card-body {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 40px;
      width: 250px;
    }
  }

  .transfer-pane {
    transform: scale(0.8); // 缩放为原来的 60%
    transform-origin: top; // 缩放的基点设为顶部
  }
}

.h-timeline {
  width: 100%;
}

.message-reminding {
  position: fixed;
  bottom: 40px;
  display: flex;
  margin-top: 20px;
  width: 100%; /* 确保容器占满整个父元素的宽度 */

  .tabs {
    height: 200px;
    width: 120px;
    margin-top: 40px;
  }

  .table {
    flex: 1; /* 让表格占满剩余的空间 */
    height: 240px;
  }
}
</style>
