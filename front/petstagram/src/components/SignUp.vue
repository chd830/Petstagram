<template>
  <div id="form-2">
    <v-text-field
      hide-details="auto"
      v-model="userEmail"
    ></v-text-field>
    <v-text-field v-model="userNickname"></v-text-field>
    <v-text-field v-model="userPwd"></v-text-field>
    <v-text-field v-model="userPwd2"></v-text-field>
    <v-text-field v-model="userImg"></v-text-field>
    <v-btn v-on:click="signup" depressed>
      SignUp
    </v-btn>
  </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

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
    signup: function() {
      if(this.userPwd != this.userPwd2) {
        alert('입력한 두 비밀번호가 일치하지 않습니다.')
        return
      }
      axios.get('http://localhost:8000/user', {
        userEmail: this.userEmail,
        userNickname: this.userNickname,
        userPwd : this.userPwd,
        userImg: this.userImg
      }, {
          withCredentials: true,
      }).then((res) => {
          console.log(res.data)
      })
    }
  }
}
</script>
