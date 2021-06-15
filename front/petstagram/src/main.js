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
import firebase from "firebase"
// npm install --save firebase
import datetime from "vue-datetime"

Vue.config.productionTip = false
Vue.prototype.$http = axios

// firebase
var firebaseConfig = {
  apiKey: process.env.VUE_APP_APIKEY,
  authDomain: process.env.VUE_APP_AUTHDOMAIN,
  projectId: process.env.VUE_APP_PROJECTID,
  storageBucket: process.env.VUE_APP_STORAGEBUCKET,
  messagingSenderId: process.env.VUE_APP_MESSAGINGSENDERID,
  appId: process.env.VUE_APP_APPID,
  measurementId: process.env.VUE_APP_MEASUREMENTID,
  // apiKey: "AIzaSyDOZddqqpsVfIzJSE1EWt8WEpqYVmdTY2M",
  // authDomain: "petstagram-6dd07.firebaseapp.com",
  // projectId: "petstagram-6dd07",
  // storageBucket: "petstagram-6dd07.appspot.com",
  // messagingSenderId: "208979939701",
  // appId: "1:208979939701:web:2f63e48887ae1ac70cc33c",
  // measurementId: "G-Y6PMK7XJKL"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
firebase.analytics();

Vue.component("datetime", datetime)

new Vue({
  router,
  vuetify,
  VueGeolocationApi,
  icons: {
    iconfont: 'mdi',
  },
  render: h => h(App)
}).$mount('#app')
