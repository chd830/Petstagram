import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import axios from "axios"
import router from './router/index.js'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import '@mdi/font/css/materialdesignicons.css'
import VueGeolocationApi from 'vue-geolocation-api'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
// npm install --save bootstrap-vue

Vue.config.productionTip = false
Vue.prototype.$http = axios

new Vue({
  router,
  vuetify,
  VueGeolocationApi,
  icons: {
    iconfont: 'mdi',
  },
  render: h => h(App)
}).$mount('#app')
