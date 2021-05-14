<template>
  <div id="form-2">
    <v-text-field
      hide-details="auto"
      label="Email"
      v-model="userEmail"
    ></v-text-field>
    <v-text-field 
    label="Password"
    v-model="userPwd"
    ></v-text-field>
    <v-btn v-on:click="signin" depressed>
      SignIn
    </v-btn>
  </div>
</template>

<script>
// import router from '../../router/index.js'

export default {
  name: "app",  
  data() {
    return {
      userEmail: '',
      userPwd: '',
    }
  },
  methods: {
    signin() {
      this.$http.post('http://localhost:8000/api/v1/user/signin', {
        userEmail: this.userEmail,
        userPwd : this.userPwd,
      }, 
      { 
        headers: { 'Content-Type': 'application/json' } 
      },
      {
          withCredentials: true,
      }).then((res) => {
        console.log(res)
        if(res.data) {
          alert('로그인이 완료되었습니다.')
          // router.push("/")
        }
        else {
          alert('다시 로그인해 주세요.')
        }
      })
    }
  }
}
</script>
