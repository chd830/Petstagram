<template>
  <v-row justify="center">
    <v-col cols="12" sm="10" md="8" lg="6">
    <v-text-field
      hide-details="auto"
      label="Email"
      v-model="userEmail"
    ></v-text-field>
    <v-text-field 
    label="Password"
    v-model="userPwd"
    ></v-text-field>
    <v-btn v-on:click="signin" color="primary" large outlined>
      SignIn
    </v-btn>
    &nbsp;&nbsp;&nbsp;
    <v-btn v-on:click="signup" color="error" large outlined>
      SignUp
    </v-btn>
    </v-col>
  </v-row>
</template>

<script>
import router from '../../router/index.js'

export default {
  name: "app",  
  data() {
    return {
      userEmail: '',
      userPwd: '',
    }
  },
  methods: {
    signup() {
      router.push('/signup')
    },
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
          localStorage.setItem("userEmail", this.userEmail)
          alert('로그인이 완료되었습니다.')
          router.push("/")
        }
        else {
          alert('다시 로그인해 주세요.')
        }
      })
    }
  }
}
</script>
