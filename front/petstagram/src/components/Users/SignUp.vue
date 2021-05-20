<template>
  <div id="form-2">
    <v-text-field
      hide-details="auto"
      label="Email"
      :rules="[() => !!userEmail || 'This field is required']"
      v-model="userEmail"
    ></v-text-field>
    <v-text-field 
    label="NickName"
    v-model="userNickname"
    :rules="[() => !!userNickname || 'This field is required']"
    ></v-text-field>
    <v-text-field 
    label="Password"
    v-model="userPwd"
    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
    :rules="[rules.required, rules.min]"
    :type="show1 ? 'text' : 'password'"
    @click:append="show1 = !show1"
    ></v-text-field>
    <v-text-field 
    label="Password check"
    v-model="userPwd2"
    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
    :rules="[rules.required, rules.min]"
    :type="show2 ? 'text' : 'password'"
    @click:append="show2 = !show2"
    ></v-text-field>
    <v-text-field 
      label="Age"
      v-model.number="userAge" 
      append-outer-icon="add" 
      @click:append-outer="increment" 
      prepend-icon="remove" 
      @click:prepend="decrement">
      </v-text-field>
    <input ref="imageInput" type="file" hidden @change="onChangeImages">
    <v-btn type="button" @click="onClickImageUpload">이미지 업로드</v-btn>
    <!-- <v-file-input
      accept="image/*"
      label="Image"
      @click="onClickImageUpload"
    ></v-file-input> -->
    <v-img
      v-if="imageUrl" :src="imageUrl"
    ></v-img>
    <br/><br/><br/>
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
      userImg: '',
      userAge: '',
      imageUrl: null,
      show1: false,
      show2: false,
      password: 'Password',
      rules: {
        required: value => !!value || 'Required.',
        min: v => v.length >= 4 || 'Min 4 characters',
        emailMatch: () => (`The email and password you entered don't match`),
      }
    }
  },
  methods: {
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
        console.log(e.target.files)
        const file = e.target.files[0]; // Get first index in files
        this.imageUrl = URL.createObjectURL(file); // Create File URL
    },
    increment() {
      this.userAge += 1
    },
    decrement() {
      this.userAge += 1
    },
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
      }).then((res) => {
        if(res.data == true) {
          alert('회원가입이 완료되었습니다.')
          router.push("/signin")
        }
        else {
          alert('이미 있는 이메일 입니다.')
        }
      })
    }
  }
}
</script>
