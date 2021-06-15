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
      <v-img :src="userImg"></v-img>
      <!-- <v-text-field
        hide-details="auto"
        label="Image"
        v-model="userImg"
      ></v-text-field> -->
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
      userEmail: localStorage.getItem("userEmail"),
      userNickname: '',
      userAge: '',
      userImg: '',
      imageUrl: null
    }
  },
mounted() {
  this.$nextTick(function() {
    var data = firebase.storage().ref().child(`users/${this.userEmail}`)
    data.getDownloadURL()
    .then(function(url) {
      //this.userImg를 못읽어서 이미지를 띄울 수 없음 값은 가져와짐!
      this.userImg = url
      console.log("MOUNTED: ",url)
    })
    .catch(function(err) {
      console.log("ERR", err)
    })
  })
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
      /*
      {
        jwttoken: "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJ1c2VyTml…w1scoJQfuLYQPly_vxCrFjpZiXc_rug6IA-aCUD6eOhy1fZqQ", 
        username: "cd", 
        userAge: 0, 
        userImg: null
        }
      */
        console.log("DATA: ",res.data)
        this.userNickname = res.data.userNickname;
        this.userImg = res.data.userImg;
        this.userAge = res.data.userAge;
    })
  },
  methods: {
    changeImg(file) {
      if (file) {
        this.imgURL = URL.createObjectURL(file)
        this.userImg = file
      } else {this.imgURL=null}
    },
    update() {
      const storageRef = firebase.storage().ref(`users/${this.userEmail}`)
      storageRef.put(this.userImg)
      .then(() => {
        storageRef.getDownloadURL()
        .then(url => {
          console.log("URL: ",url);
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
            // router.push("/signin")
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
