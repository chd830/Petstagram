<template>
<div>
        <v-text-field
      hide-details="auto"
      label="Email"
      v-model="userEmail"
    ></v-text-field>
    <v-text-field
      hide-details="auto"
      label="NickName"
      v-model="userNickname"
    ></v-text-field>
    <v-text-field
      hide-details="auto"
      label="Age"
      v-model="userAge"
    ></v-text-field>
    <v-text-field
      hide-details="auto"
      label="Image"
      v-model="userImg"
    ></v-text-field>
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
        userAge: '',
        userImg: ''
    }
  },
  created() {
    console.log('test')
    this.$http.post('http://localhost:8000/api/v1/user/', {
        userEmail: 'ab'
      }, 
      { 
        headers: { 'Content-Type': 'application/json' } 
      },
      {
          withCredentials: true,
      }).then((res) => {
          this.userEmail = res.data.userEmail;
          this.userNickname = res.data.userNickname;
          this.userAge = res.data.userAge;
          this.userImg = res.data.userImg;
        console.log(res.data)
      })
  },
  methods: {
      cancel() {
          router.push('/')
      }
  }
}
</script>
