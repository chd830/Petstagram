<template>
  <div>
    <!-- posts -->
    <v-container style="margin:auto">
      <v-row>
        <v-spacer></v-spacer>
        <v-col
          v-for="post in posts"
          :key="post.postNo"
          cols=4
        >
          <v-card>
            <v-img
              max-height="500px"
              max-width="500px"
              :src="post.postImg"
              :alt="post.postSubject"
              style="object-fit:cover"
              @click="goDetail(post.postNo)"
            >
            </v-img>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
  import router from "../../router/index"

  export default {
    data () {
      return {
        posts : [],
      }
    },
    methods: {
      getPosts() {
        const baseURL = "http://localhost:8000";
        this.$http.get(`${baseURL}/api/v1/posts/all`)
        .then((response) => {
          this.posts = response.data.data;
        })
        .catch(() => {alert("Fail");})
      },
      insertposts() {
        router.push("/posts/insert")
      },
      goDetail(postNo) {
        router.push(`/posts/detail?postNo=${postNo}`)
      }
    },
    mounted() {
      this.getPosts();
    }
  }
</script>