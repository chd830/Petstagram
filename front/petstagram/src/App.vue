<template>
    <div id="app">
      <Navbar/>
      <v-app>
        <transition name="slide-fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </v-app>
      <div v-if="loading" style="position:absolute; width: 100%; height:100%; top:0; left:0; z-index:10000; background-color:white">
        <div style="margin-left: auto; margin-right: auto">
          <v-img :src="require(`@/assets/loading.gif`)"></v-img>
        </div>
      </div>
    </div>
</template>
<script>
import Navbar from './components/Nav.vue'

export default {
  components: {
    Navbar,
  },
  name: "app",
  data: () => ({
    loading: true,
    image: '@/assets/loading.gif'
  }),
  mounted() {
    setTimeout(() => {
      this.loading = false
    }, 800)
  }
}
</script>
