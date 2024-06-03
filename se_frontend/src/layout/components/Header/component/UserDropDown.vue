<script setup>
import { useStore } from "vuex";
import { ArrowDown } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { submitAvatar } from "@/api/home";
import { useI18n } from "vue-i18n";

const { t } = useI18n();

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
  // console.log(avatarUrl.value);
  await submitAvatar(avatarUrl.value);
  store.commit("updateUserAvatar", avatarUrl.value);
  ElMessage.success(t("changeAvatarSuccess"));
  handleClose();
  location.reload();
};
</script>

<template>
  <div class="userDropDown">
    <el-dropdown trigger="click" @command="handleCommand">
      <!-- 用户名 -->
      <span class="el-dropdown-link">
        {{ $t("personalCenter") }}
        <el-icon>
          <arrow-down />
        </el-icon>
      </span>

      <!-- 菜单项 -->
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item icon="UserFilled" command="changeAvatar">{{
            $t("changeAvatar")
          }}</el-dropdown-item>
          <el-dropdown-item icon="SwitchButton" command="logout">{{
            $t("logout")
          }}</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>

  <el-dialog
    v-model="dialogVisible"
    :title="$t('changeAvatar')"
    :before-close="handleClose"
  >
    <el-input v-model="avatarUrl" :placeholder="$t('avatarPlaceHolder')" />
    <template #footer>
      <el-button type="primary" @click="submit">{{ $t("confirm") }}</el-button>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
@import "@/style/mixin.scss";

.userDropDown {
  cursor: pointer;
  width: 100px;
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
