import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from "./router/router"
import axios from "axios"
import VueGeolocationApi from 'vue-geolocation-api'

Vue.config.productionTip = false
Vue.prototype.$http = axios

new Vue({
  router,
  vuetify,
  VueGeolocationApi,
  render: h => h(App)
}).$mount('#app')
