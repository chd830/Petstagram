<template>
  <div>
    <v-card width="50%" style="margin:auto">
      <v-img
        height="50%"
        width="50%"
        :src="post.postImg"
        :alt="post.postSubject"
        style="margin:auto"
      >
        <span
          class="headline white--text pl-4 pt-4"
          v-text="post.postSubject"
        ></span>
      </v-img>
      <v-icon v-if="post.postLike && post.postLike.includes(userEmail)" @click="checkPostLike">favorite</v-icon>
      <v-icon v-else @click="checkPostLike">favorite_border</v-icon>
      <v-card-text>{{ post.postContent }}</v-card-text>
      <v-btn @click="updatepost">
        Update
      </v-btn>
      <v-spacer/>
      <hr/>

    <!-- Comment -->
      <v-text-field :rules="rules" v-model="commentContent" style="width:90%; margin:auto;"></v-text-field>
      <v-btn @click="insertComment">insert</v-btn>
      <v-container>
        <v-row>
          <v-col
            v-for="comment in comments"
            :key="comment.commentNo"
          >
            {{ comment.userEmail }}
            <v-spacer/>
            {{ comment.commentContent }}
            <v-icon v-if="comment.commentLike && comment.commentLike.includes(userEmail)" @click="checkCommentLike(comment)">favorite</v-icon>
            <v-icon v-else @click="checkCommentLike(comment)">favorite_border</v-icon>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </div>
</template>

<script>
  import router from "../../router/index"

  export default {
    data () {
      return {
        post: [],
        comments: [],
        rules: [
          value => (value || '').length <= 200 || 'Max 200 characters',
        ],
        commentContent: "",
        userEmail: "test",
      }
    },
    methods: {
      getPost() {
        const baseURL = "http://localhost:8000";
        this.$http.get(`${baseURL}/api/v1/posts/postNo?postNo=${this.$route.query.postNo}`)
        .then((response) => {
          this.post = response.data.data;
        })
        .catch(() => {alert("Fail");})
      },
      getCommnet() {
        const baseURL = "http://localhost:8000";
        this.$http.get(`${baseURL}/api/v1/comments/postNo?postNo=${this.$route.query.postNo}`)
        .then((response) => {
          this.comments = response.data.data;
        })
        .catch(() => {alert("Fail");})
      },
      insertComment() {
        // DateTime
        var createDate = new Date();
        createDate = `${createDate.getFullYear()}.${createDate.getMonth()}.${createDate.getDate()}/${createDate.getHours()}.${createDate.getMinutes()}.${createDate.getSeconds()}`

        const baseURL = "http://localhost:8000";
        this.$http.post(`${baseURL}/api/v1/comments/insert`, {
          commentNo : -1,
          commentContent : this.commentContent,
          commentCreateDate : createDate,
          commentUpdateDate : createDate,
          commentLike : [],
          userEmail : this.userEmail,
          postNo : this.post.postNo
        })
        .then(() => {
          this.getCommnet()
          this.commentContent = ""
        })
        .catch(() => {alert("Fail");})
      },
      insertposts() {
        router.push("/posts/insert")
      },
      updatepost() {
        router.push(`/posts/update?postNo=${this.$route.query.postNo}`)
      },
      checkPostLike() {
        var postLike = this.post.postLike
        if (postLike.includes(this.userEmail)){
          postLike.pop(this.userEmail)
        } else {postLike.push(this.userEmail)}

        const baseURL = "http://localhost:8000";
        this.$http.post(`${baseURL}/api/v1/posts/updatepostlike`, {
          postNo: this.post.postNo,
          postLike: postLike
        })
        .then(() => {
          this.getPost()
        })
        .catch(() => {alert("Fail");})
      },
      checkCommentLike(comment) {
        var commentLike = comment.commentLike
        if (commentLike.includes(this.userEmail)){
          commentLike.pop(this.userEmail)
        } else {commentLike.push(this.userEmail)}

        const baseURL = "http://localhost:8000";
        this.$http.post(`${baseURL}/api/v1/comments/updatelike`, {
          commentNo: comment.commentNo,
          commentLike: commentLike
        })
        .then(() => {
          this.getCommnet()
        })
        .catch(() => {alert("Fail");})
      }
    },
    mounted() {
      this.getPost();
      this.getCommnet();
    }
  }
</script>