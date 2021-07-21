<template>
  <v-row justify="center">
    <v-col cols="12" sm="10" md="8" lg="6">
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
      <v-img :src="imageUrl"></v-img>
      <v-file-input
        @change="changeImg"
        :rules="rules"
        accept="image/png, image/jpeg, image/bmp"
        placeholder="Select picture"
        prepend-icon="mdi-camera"
        style="width:99%"
      ></v-file-input>
      <br/>
      <div class="spinner-div" v-if="isLoading">
        <b-spinner></b-spinner>
      </div>
      <div class="spinner-div" v-if="!isLoading">
        <img
          hide-details="auto"
          label="Image"
          :src="userImg"
        />
      </div>
      <v-btn v-on:click="update" color="primary" large outlined>
        Update
      </v-btn>
      &nbsp;&nbsp;
      <v-btn v-on:click="cancel" color="secondary" large outlined>
        Cancel
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
import router from '../../router/index.js'
import firebase from "firebase"

export default {
  name: "app",  
  data() {
    return {
      rules: [
        value => !value || value.size < 2000000 || 'Avatar size should be less than 2 MB!',
      ],
      userEmail: localStorage.getItem("userEmail"),
      userNickname: '',
      userAge: '',
      userImg: '',
      imageUrl: null,
      isLoading: false
    }
  },
mounted() {

},
created() {
  this.$http.post('http://localhost:8000/api/v1/user/getuser', {
      userEmail: this.userEmail,
    }, 
    { 
      headers: { 'Content-Type': 'application/json' } 
    },
    {
        withCredentials: true,
    }).then((res) => {
        console.log("DATA: ",res.data)
        this.userNickname = res.data.userNickname;
        this.userImg = res.data.userImg;
        this.userAge = res.data.userAge;
    })
    // console.log('userEmail userImg')
    // console.log(this.userEmail, this.userImg)
    // var data = firebase.storage().ref().child(`users/${this.userEmail}`)
    // console.log("download data")
    // data.getDownloadURL()
    // .then(function(url) {
    //   console.log('===========================================\n',url,'================================================')
    //   this.userEmail = url
    //   // this.userImg = url
    // })
    // .catch(function(err) {
    //   console.log("ERR", err)
    // })
  },
  methods: {
    changeImg(file) {
      if (file) {
        this.imgURL = URL.createObjectURL(file)
        this.userImg = file
      } else {this.imgURL=null}
    },
    update() {
      this.isLoading = true
      const storageRef = firebase.storage().ref(`users/${this.userEmail}`)
      storageRef.put(this.userImg)
      .then(() => {
        storageRef.getDownloadURL()
        .then(url => {
          // 동기 비동기 문제 해결할 것
          this.imgURL = url
          console.log(url)
          this.$http.post('http://localhost:8000/api/v1/user/update', {
            userEmail: this.userEmail,
            userNickname: this.userNickname,
            userImg: url,
            userPwd : this.userPwd,
          }, 
          { 
            headers: { 'Content-Type': 'application/json' } 
          },
          {
              withCredentials: true,
          }).then((res) => {
            console.log(res.data)
            alert('개인정보 변경이 완료되었습니다.')
            // router.push("/signin")
          })
        })
      })
      this.isLoading = false;
    },
    cancel() {
      router.push('/')
    }
  }
}
</script>
