<script setup>
import { useStore } from "vuex";
import { ArrowDown } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { submitAvatar } from "@/api/home";

const store = useStore();

function handleCommand(val) {
  if (val === "logout") {
    store.dispatch("logout");
  } else if (val === "changeAvatar") {
    dialogVisible.value = true;
  }
}

const dialogVisible = ref(false);
const avatarUrl = ref("");
const handleClose = () => {
  avatarUrl.value = "";
  dialogVisible.value = false;
};
const submit = async () => {
  console.log(avatarUrl.value);
  await submitAvatar(avatarUrl.value);
  store.commit("updateUserAvatar", avatarUrl.value);
  ElMessage.success("修改成功");
  handleClose();
};
</script>

<template>
  <div class="userDropDown">
    <el-dropdown trigger="click" @command="handleCommand">
      <!-- 用户名 -->
      <span class="el-dropdown-link">
        admin
        <el-icon>
          <arrow-down />
        </el-icon>
      </span>

      <!-- 菜单项 -->
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item icon="UserFilled" command="changeAvatar"
            >修改头像</el-dropdown-item
          >
          <el-dropdown-item icon="SwitchButton" command="logout"
            >退出登录</el-dropdown-item
          >
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>

  <el-dialog
    v-model="dialogVisible"
    title="修改头像"
    :before-close="handleClose"
  >
    <el-input v-model="avatarUrl" />
    <template #footer>
      <el-button type="primary" @click="submit">确定</el-button>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
@import "@/style/mixin.scss";

.userDropDown {
  cursor: pointer;
  width: 80px;
  height: 100%;
  padding: 0 10px;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;

  &:hover {
    @include hover_bg_color();
  }

  .el-dropdown-link {
    display: flex;
    align-items: center;
  }
}
</style>
