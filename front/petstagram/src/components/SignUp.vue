<template>
  <div id="form-2">
    <v-text-field
      hide-details="auto"
      v-model="userEmail"
    ></v-text-field>
    <v-text-field v-model="userPwd"></v-text-field>
    <v-btn v-on:click="signin" depressed>
      Normal
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
      userPwd: ''
    }
  },
  methods: {
    signin: function() {
      console.log(this.userEmail+"\t"+this.userPwd)
      axios.get('http://localhost:8080/user', {
        'Content-Type': 'application/json',
          params: {
            userEmail: this.userEmail,
            userPwd : this.userPwd
          },
          
      }, {
          withCredentials: true,
      }).then((res) => {
          console.log(res)
      })
      // axios({
      //   method: 'GET',
      //   url: 'localhost:8080/user',
      //   params: {
      //     userEmail: this.userEmail,
      //     userPwd: this.userPwd
      //   },
      //   withCredentials: true
      // }).then((response) => {
      //   console.log(response.data);
      // }).catch((ex) => {
      //   console.log("ERR");
      // })
    }
  }
}
</script>
