<template>
  <div class="intermediate"></div>
</template>

<script>
// import api from '@/api/index.js'
// import EventBus from '../eventBus.js'
export default {
    created() {
        const url = window.location.href
        var afterDomain = url.slice(url.indexOf('?') + 1)
        // 파라미터 분리
        var parameters = afterDomain.split('&')
        var useremail = 'useremail'
        var token;
        parameters.forEach(parameter => {
            // 토큰
            if (parameter.includes('token')) {
                token = parameter.split('=')[1]
                this.$cookies.set('token', token, '1d')
            }
            // 유저 이메일
            else if (parameter.includes('useremail')) {
                useremail = parameter.split('=')[1]
            }
            console.log('useremail', useremail)
        })

        // api.get(`/api/v1//user/`, {
        //     useremail: useremail
        // })
        // .then(res => {
        //     localStorage.usernickname = res.data.usernickname
        //     localStorage.userData = JSON.stringify(res.data)
        //     EventBus.$emit('isLogedIn');
        //     this.$router.push({ name: 'Home'})
        // })
        // .catch(err => console.log(err))
    }
}
</script>

<style>

</style>