import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  },
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/index',
      name: 'index',
      component: () => import('@/views/user/index'),
      children: [
        {
          path: '/main',
          component: () => import('@/views/user/main')
        },
        {
          path: '/movie',
          component: () => import('@/views/user/movie')
        },
        {
          path: '/order',
          component: () => import('@/views/user/ticketOrder')
        },
        {
          path: '/movielist',
          component: () => import('@/views/user/movielist')
        },
        {
          path: '/search',
          component: () => import('@/views/user/search')
        },
        {
          path: '/movie',
          component: () => import('@/views/user/movie')
        },
        {
          path: '/my/tickets',
          name: 'myTickets',
          component: () => import('@/views/user/my/tickets')
        },
        {
          path: '/my/orders',
          name: 'myOrders',
          component: () => import('@/views/user/my/orders')
        },
        {
          path: '/my/vip',
          name: 'myVIP',
          component: () => import('@/views/user/my/vip')
        },
        {
          path: '/my/coupons',
          name: 'myCoupons',
          component: () => import('@/views/user/my/coupons')
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/register')
    },
    {
      path: '/admin',
      name: 'admin',
      redirect: '/admin/movie',
      component: () => import('@/views/admin/admin'),
      children: [
        {
          path: '/admin/movie',
          name: 'adminMovieManage',
          component: () => import('@/views/admin/movieManage')
        },
        {
          path: '/admin/schedule',
          name: 'adminScheduleManage',
          component: () => import('@/views/admin/scheduleManage')
        },
        {
          path: '/admin/promotion',
          name: 'adminPromotionManage',
          component: () => import('@/views/admin/promotionManage')
        },
        {
          path: '/admin/cinema',
          name: 'adminCinemaManage',
          component: () => import('@/views/admin/cinemaManage')
        },
        {
          path: '/admin/statistic',
          name: 'adminStatistic',
          component: () => import('@/views/admin/statistic')
        },
        {
          path: '/admin/staff',
          name: 'adminStaffManage',
          component: () => import('@/views/admin/staffManage')
        },
        {
          path: '/admin/movie/details',
          name: 'adminMovieDetails',
          component: () => import('@/views/admin/movieDetails')
        },
        {
          path: '/admin/coupon',
          name: 'adminCouponManage',
          component: () => import('@/views/admin/couponManage')
        },
        {
          path: '/admin/refund',
          name: 'adminRefundManage',
          component: () => import('@/views/admin/refundManage')
        },
        {
          path: '/admin/vip',
          name: 'adminVipManage',
          component: () => import('@/views/admin/vipManage')
        }
      ]
    }
  ]
})
