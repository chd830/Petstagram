<template>
  <v-app>
    <Navbar/>
    <transition>
      <router-view></router-view>
    </transition>
  </v-app>
</template>
<script>
import Navbar from './components/Nav.vue'

export default {
  components: {
    Navbar
  }
}
</script>
