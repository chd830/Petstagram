<template>
  <div class="intermediate"></div>
</template>

<script>
// import api from '@/api/index.js'
// import EventBus from '../eventBus.js'
import router from '../router/index.js'

export default {
    created() {
        const url = window.location.href
        var afterDomain = url.slice(url.indexOf('?') + 1)
        // 파라미터 분리
        var parameters = afterDomain.split('&')
        var useremail = parameters[1].split('=')[1].split('%40')[0]+"@"+parameters[1].split('=')[1].split('%40')[1];

        console.log('success')
        localStorage.setItem("userEmail", useremail)
        router.push("/")
        // console.log("user")
        // api.post(`/user`, {
        //     useremail: useremail
        // })
        // .then(res => {
        //     localStorage.usernickname = res.data.usernickname
        //     localStorage.userData = JSON.stringify(res.data)
        //     this.$router.push({ name: 'Home'})
        // })
        // .catch(err => console.log(err))
    }
}
</script>

<style>

</style>