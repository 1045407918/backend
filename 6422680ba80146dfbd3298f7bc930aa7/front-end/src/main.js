// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import router from './router'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import axios from 'axios'
import echarts from 'echarts'
Vue.use(VueRouter)
Vue.use(iView)

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$echarts = echarts

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
