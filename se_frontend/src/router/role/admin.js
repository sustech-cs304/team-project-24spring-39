import { getTranslation } from "@/locales";

export default [
  {
    path: "/selectionManagement",
    name: "selectionManagement",
    meta: {
      title: getTranslation("selectionManagement"),
      breadcrumb: [getTranslation("selectionManagement")],
    },
    component: () => import("@/views/courseManagement.vue"),
  },
  {
    path: "/reservation-locations",
    name: "reservationLocations",
    meta: {
      title: getTranslation("breadcrumbs.locationManagement"),
      breadcrumb: [
        getTranslation("breadcrumbs.reservation"),
        getTranslation("breadcrumbs.locationManagement"),
      ],
    },
    component: () => import("@/views/LocationsManagement.vue"),
  },
  {
    path: "/reservation-records",
    name: "reservationRecords",
    meta: {
      title: getTranslation("breadcrumbs.recordManagement"),
      breadcrumb: [
        getTranslation("breadcrumbs.reservation"),
        getTranslation("breadcrumbs.recordManagement"),
      ],
    },
    component: () => import("@/views/RecordsManagement.vue"),
  },
];
