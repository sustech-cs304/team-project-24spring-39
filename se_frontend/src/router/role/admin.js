export default [
  {
    path: "/reservation-locations",
    name: "reservationLocations",
    meta: {
      title: "地点管理",
      breadcrumb: ["自习室预约", "地点管理"],
    },
    component: () => import("@/views/LocationsManagement.vue"),
  },
  {
    path: "/reservation-records",
    name: "reservationRecords",
    meta: {
      title: "记录管理",
      breadcrumb: ["自习室预约", "记录管理"],
    },
    component: () => import("@/views/RecordsManagement.vue"),
  },
];
