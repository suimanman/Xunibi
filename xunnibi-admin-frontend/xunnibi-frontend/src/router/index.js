import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../index.vue'
import LoginView from '../views/LoginView.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/toRegister',
    name: 'register',
    component: () => import('../views/RegisterView.vue')

  },

  {
    path: '/',
    name: 'Index',
    component: Index,
    children: [
      { path: 'user', name: 'UserView', component: () => import('../views/manage/UserView') },
      { path: 'crypto', name: 'XunibiView', component: () => import('../views/manage/XunibiView.vue') },
      { path: 'rent', name: "RentView", component: () => import('../views/manage/RentView.vue') },
      { path: 'return', name: "ReturnView", component: () => import('../views/manage/ReturnView.vue') },
      { path: 'team', name: "TeamView", component: () => import("../views/manage/TeamView.vue") },
      { path: 'review', name: "ReviewView", component: () => import("../views/manage/MaterialReviewView.vue") },
      { path: 'admin', name: "AdminView", component: () => import("../views/manage/AdminView.vue") },
      { path: 'notice', name: "NoticeView", component: () => import("../views/manage/NoticeView.vue") },
      { path: 'traderecords', name: "TradeView", component: () => import("../views/manage/TradeRecordsView.vue") },
      { path: 'achieverecords', name: "ReviewRecordsView", component: () => import("../views/manage/ReviewRecordsView.vue") },
      { path: 'dutyrecord', name: "DutyRecordView", component: () => import("../views/manage/DutyRecordView.vue") },
      { path: 'dutyreview', name: "DutyReviewView", component: () => import("../views/manage/DutyReviewView.vue") },


    ],
    meta: {
      requiresAuth: true
    }
  },

  ,

  {
    path: '/login',
    name: 'LoginView',
    component: LoginView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  const adminData = JSON.parse(localStorage.getItem("adminData"));
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!adminData) {
      next({ path: "/login" });
    } else {
      next();
    }
  } else {
    next();
  }
});


export default router
