// import Vue from 'vue'
// import App from './App.vue'
// import router from "./router"

// Vue.config.productionTip = false

// new Vue({
//   el: "#app",
//   render: h => h(App),
//   router,
// }).$mount('#app')


import Vue from 'vue'
import App from './App.vue'
<<<<<<< HEAD
import vuetify from './plugins/vuetify'
import router from "./router/router"
import axios from "axios"
import VueGeolocationApi from 'vue-geolocation-api'
=======
import router from './router/index.js';
>>>>>>> c8be00e66df138f17f368266016bcc56af7270b7

Vue.config.productionTip = false
Vue.prototype.$http = axios

new Vue({
<<<<<<< HEAD
  router,
  vuetify,
  VueGeolocationApi,
  render: h => h(App)
}).$mount('#app')
=======
  render: h => h(App),
  router
}).$mount('#app')
>>>>>>> c8be00e66df138f17f368266016bcc56af7270b7
