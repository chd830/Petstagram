<template>
  <div>
    <v-row justify="center">
      <v-col cols="12" sm="10" md="8" lg="6">
        <v-card ref="form">
          <!-- category -->
          <v-combobox
            v-model="category"
            :items="pets"
            label="Category"
            style="width:25%"
          >
            <template v-slot:selection="data">
              <v-chip
                :key="JSON.stringify(data.item)"
                v-bind="data.attrs"
                :input-value="data.selected"
                :disabled="data.disabled"
                @click:close="data.parent.selectItem(data.item)"
              >
                <v-avatar
                  class="accent white--text"
                  left
                  v-text="data.item.slice(0, 1).toUpperCase()"
                ></v-avatar>
                {{ data.item }}
              </v-chip>
            </template>
          </v-combobox>

          <!-- picture -->
          <v-file-input
            @change="changeImg"
            :rules="rules"
            accept="image/png, image/jpeg, image/bmp"
            placeholder="Select picture"
            prepend-icon="mdi-camera"
            style="width:99%"
          ></v-file-input>

          <!-- preview -->
          <v-img
            v-if="imgURL" :src="imgURL"
            style="width:250px;height:300px;object-fit:cover">
          </v-img>
          
          <!-- subject -->
          <v-card-text>
            <v-text-field
              ref="subject"
              v-model="subject"
              :rules="[() => !!subject || 'This field is required']"
              label="Subject"
              placeholder="Pet"
              required
            ></v-text-field>
            
            <!-- content -->
            <v-textarea
              v-model="content"
              filled
              no-resize
              label="Content"
              rows="4"
              row-height="50"
            ></v-textarea>

            <!-- taguser -->
            <v-combobox
              v-model="taguser"
              label="tag your friends"
              multiple
              chips
              deletable-chips
            ></v-combobox>

            <!-- hashtag -->
            <v-combobox
              v-model="hashtag"
              label="Hashtag"
              multiple
              chips
              deletable-chips
            ></v-combobox>

          </v-card-text>

          <v-divider class="mt-12"></v-divider>

          <v-card-actions>
            <v-btn text @click="resetForm">Cancel</v-btn>
            <v-spacer></v-spacer>

            <v-btn color="primary" text @click="submit">Submit</v-btn>

          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
  import router from '../../router/index'
  import firebase from "firebase"

  export default {
    data: () => ({
      rules: [
        value => !value || value.size < 2000000 || 'Avatar size should be less than 2 MB!',
      ],
      imgURL : null,
      imgFile : null,
      subject: null,
      content : null,
      pets : ["강아지", "고양이", "햄스터", "물고기", "도마뱀"],
      category : "",
      hashtag : [],
      taguser : [],
    }),

    watch: {
      name () {
        this.errorMessages = ''
      },
    },

    methods: {
      goback() {
        router.push("/posts")
      },
      changeImg(file) {
        if (file) {
          this.imgURL = URL.createObjectURL(file)
          this.imgFile = file
        } else {this.imgURL=null}
      },
      resetForm () {
        router.push("/posts")
      },
      submit () {        
        // get position
        var latitude;
        var longitude;
        navigator.geolocation.getCurrentPosition(pos => {
        latitude = pos.coords.latitude;
        longitude = pos.coords.longitude;
        }, err => {
          console.log(err.message)
        })
        // DateTime
        var createDate = new Date();
        createDate = `${createDate.getFullYear()}/${createDate.getMonth()}/${createDate.getDate()}/${createDate.getHours()}:${createDate.getMinutes()}:${createDate.getSeconds()}`
        const updateDate = createDate
        const postLike = 0;
        const commentNo = 0;

        // firebase
        const storageRef = firebase.storage().ref(`posts/test${new Date().toLocaleString()}`)
        storageRef.put(this.imgFile)
        .then(() => {
          storageRef.getDownloadURL()
          .then(url => {
            // 동기 비동기 문제 해결할 것
            this.imgURL = url

            const baseURL = "http://localhost:8000";
            this.$http.post(`${baseURL}/api/v1/posts/insert`, {
              postNo : -1,
              postSubject : this.subject,
              postContent : this.content,
              postLike : postLike,
              postImg : this.imgURL,
              postLng : longitude,
              postLat : latitude,
              postCreateDate : createDate,
              postUpdateDate : updateDate,
              commentNo : commentNo,
              categoryName : this.category,
              hashtagContent : this.hashtag,
              tagUserEmail : this.taguser,
              userEmail : "test"
            })
            .then(() => {
              router.push("/posts")
            })
            .catch(() => {alert("Fail");})
          })
        })
      },
    },
  }
</script>