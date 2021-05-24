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
      <v-text-field
        hide-details="auto"
        label="Image"
        v-model="userImg"
      ></v-text-field>
      <v-file-input
        @change="changeImg"
        :rules="rules"
        accept="image/png, image/jpeg, image/bmp"
        placeholder="Select picture"
        prepend-icon="mdi-camera"
        style="width:99%"
      ></v-file-input>
      <!-- <input ref="imageInput" type="file" hidden @change="onChangeImages">
      <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn> -->
      <br/>
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
        userEmail: '',
        userNickname: '',
        userAge: '',
        userImg: '',
        imageUrl: null
    }
  },
  created() {
    // console.log("USEREMAIL",localStorage.getItem("userEmail"))
    this.$http.post('http://localhost:8000/api/v1/user/', {
        userEmail: localStorage.getItem("userEmail")
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
        // console.log(res.data)
      })
  },
  methods: {
    // onClickImageUpload() {
    //   this.$refs.imageInput.click();
    // },
    // onChangeImages(e) {
    //     console.log(e.target.files)
    //     const file = e.target.files[0]; // Get first index in files
    //     this.imageUrl = URL.createObjectURL(file); // Create File URL
    //     this.userImg = file
    //     console.log(file)
    //     console.log(this.imageUrl)
    // },
    changeImg(file) {
      if (file) {
        this.imgURL = URL.createObjectURL(file)
        this.userImg = file
      } else {this.imgURL=null}
    },
    update() {
      const storageRef = firebase.storage().ref(this.userEmail)
      storageRef.put(this.userImg)
      .then(() => {
        storageRef.getDownloadURL()
        .then(url => {
          // 동기 비동기 문제 해결할 것
          this.imgURL = url
          this.$http.post('http://localhost:8000/api/v1/user/update', {
            userEmail: this.userEmail,
            userNickname: this.userNickname,
            userImg: this.url,
            userPwd : this.userPwd,
          }, 
          { 
            headers: { 'Content-Type': 'application/json' } 
          },
          {
              withCredentials: true,
          }).then(() => {
            alert('개인정보 변경이 완료되었습니다.')
            router.push("/signin")
          })
        })
      })
    },
    cancel() {
      router.push('/')
    }
  }
}
</script>
