<template>
  <div>
      <v-card>
        <v-img
          height="700px"
          width="700px"
          :src="post.postImg"
          :alt="post.postSubject"
          style="object-fit:cover"
        >
          <span
            class="headline white--text pl-4 pt-4"
            v-text="post.postSubject"
          ></span>
        </v-img>
      </v-card>
      <v-btn @click="updatepost">
        Update
      </v-btn>
  </div>
</template>

<script>
  import router from "../../router/index"

  export default {
    data () {
      return {
        post: [],
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
      insertposts() {
        router.push("/posts/insert")
      },
      updatepost() {
        router.push(`/posts/update?postNo=${this.$route.query.postNo}`)
      }
    },
    mounted() {
      this.getPost();
    }
  }
</script>