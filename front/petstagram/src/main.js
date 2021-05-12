import Vue from 'vue'
import App from './App.vue'
import router from './router/index.js';
import vuetify from './plugins/vuetify'
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
