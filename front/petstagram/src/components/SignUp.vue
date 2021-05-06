<template>
  <div id="form-2">
    <v-text-field
      label="USER EMAIL"
      hide-details="auto"
      v-model="userEmail"
    ></v-text-field>
    <v-text-field label="PASSWORD" v-model="userPwd"></v-text-field>
    <v-btn v-on:click="signin" depressed>
      Normal
    </v-btn>
  </div>
</template>

<script>
import axios from 'axios';

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
      axios({
        method: 'GET',
        url: '/user',
        params: {
          userEmail: this.userEmail,
          userPwd: this.userPwd
        }
      }).then((response) => {
        console.log(response.data);
      // }).catch((ex) => {
      //   console.log("ERR");
      })
    }
  }
}
</script>
