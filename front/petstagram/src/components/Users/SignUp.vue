<template>
  <div id="form-2">
    <v-text-field
      hide-details="auto"
      label="Email"
      v-model="userEmail"
    ></v-text-field>
    <v-text-field 
    label="NickName"
    v-model="userNickname"
    ></v-text-field>
    <v-text-field 
    label="Password"
    v-model="userPwd"
    ></v-text-field>
    <v-text-field 
    label="Password check"
    v-model="userPwd2"
    ></v-text-field>
    <v-text-field 
    label="Image"
    v-model="userImg"
    ></v-text-field>
    <v-btn v-on:click="signup" depressed>
      SignUp
    </v-btn>
    <v-btn v-on:click="cancel" depressed>
      Cancel
    </v-btn>
  </div>
</template>

<script>
import router from '../../router/index.js'

export default {
  name: "app",  
  data() {
    return {
      userEmail: '',
      userNickname: '',
      userPwd: '',
      userPwd2: '',
      userImg: ''
    }
  },
  methods: {
    cancel() {
      router.push('/signin')
    },
    signup() {
      if(this.userPwd != this.userPwd2) {
        alert('입력한 두 비밀번호가 일치하지 않습니다.')
        return
      }
      this.$http.post('http://localhost:8000/api/v1/user/signup', {
        userEmail: this.userEmail,
        userNickname: this.userNickname,
        userPwd : this.userPwd,
        userImg: this.userImg
      }, 
      { 
        headers: { 'Content-Type': 'application/json' } 
      },
      {
          withCredentials: true,
      }).then(() => {
          alert('회원가입이 완료되었습니다.')
          router.push("/signin")
      })
    }
  }
}
</script>
