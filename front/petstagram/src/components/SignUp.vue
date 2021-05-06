<template>
  <div id="form-2">
    <v-text-field
      label="USER EMAIL"
      hide-details="auto"
      v-model="useremail"
    ></v-text-field>
    <v-text-field label="PASSWORD" v-model="userpassword"></v-text-field>
    <v-btn v-on:click="signin" depressed>
      Normal
    </v-btn>
  </div>
</template>

<script>
// import axios from 'axios';

export default {
  name: "app",
  data() {
    return {
      useremail: '',
      userpassword: ''
    }
  },
  methods: {
    signin: function() {
      console.log(this.useremail+"\t"+this.userpassword)
    }
  }
}
</script>
