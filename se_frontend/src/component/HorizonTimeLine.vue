<template>
  <div class="m-timeline-wrap">
    <div class="m-time-dot">
      <div
        :class="['m-dot-box', { active: active === item.year }]"
        @click="onClickYear(item.year)"
        v-for="(item, index) in timelineData"
        :key="index"
      >
        <p class="u-year">{{ item.year }}</p>
        <div class="m-dot">
          <div class="u-dot"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "HorizonTimeLine",
  props: {
    timelineData: {
      // 时间轴数据
      type: Array,
      required: true,
      default: () => {
        return [];
      },
    },
    activeYear: {
      // 初始选中年份
      type: Number,
      default: 2023,
    },
  },
  data() {
    return {
      active: this.activeYear,
    };
  },
  methods: {
    onClickYear(year) {
      if (this.active !== year) {
        this.active = year;
        console.log("hello", year);
      }
    },
  },
};
</script>
<style lang="less" scoped>
@themeColor: #1890ff;
.m-timeline-wrap {
  margin: 150px auto;
  height: 3px;
  background: #8dc6f5;
  .m-time-dot {
    display: flex;
    justify-content: space-around;
    .m-dot-box {
      cursor: pointer;
      text-align: center;
      transform: translateY(-100%+14px);
      .u-year {
        margin: 5px;
        font-size: 20px;
        font-weight: 500;
        color: #333;
        transform: translateY(-8px);
        transition: all 0.3s ease;
      }
      .m-dot {
        margin: 0 auto;
        width: 14px;
        height: 14px;
        background: #8dc6f5;
        border-radius: 50%;
        transition: all 0.3s ease;
        .u-dot {
          width: 14px;
          height: 14px;
          background: #8dc6f5;
          border-radius: 50%;
          transition: all 0.3s ease;
        }
      }
    }
    .m-dot-box:hover {
      .u-year {
        color: @themeColor;
      }
      .m-dot {
        .u-dot {
          background: @themeColor;
        }
      }
    }
    .active {
      .u-year {
        transform: scale(2) translateY(-18px); // 同时设置多个transform属性只需用空格间隔，执行时从后往前执行！
        color: @themeColor;
      }
      .m-dot {
        transform: scale(3);
        .u-dot {
          transform: scale(0.67);
          background: @themeColor;
        }
      }
    }
  }
}
</style>
