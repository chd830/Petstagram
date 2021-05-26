<template>
  <div>
    <!-- posts -->
    <v-container>
      <v-row>
        <v-spacer></v-spacer>
        <v-col
          v-for="post in posts"
          :key="post.postNo"
          cols="12"
        >
          <v-card>
            <v-img
              height="450px"
              :src="post.postImg"
              :alt="post.postSubject"
              style="object-fit:cover"
              @click="goDetail(post.postNo)"
            >
              <span
                class="headline white--text pl-4 pt-4"
                v-text="post.postSubject"
              ></span>
            </v-img>

          </v-card>
        </v-col>
      </v-row>
    </v-container>
    
    <!-- footer -->
    <v-footer
    absolute>
      <v-bottom-navigation
        dark
        shift
      >
        <v-btn>
          <span>Home</span>
          <v-icon>mdi-home</v-icon>
        </v-btn>

        <v-btn>
          <span>Chat</span>
          <v-icon>mdi-music-note</v-icon>
        </v-btn>

        <v-btn>
          <span>Book</span>
          <v-icon>mdi-book</v-icon>
        </v-btn>

        <v-btn>
          <span>My Page</span>
          <v-icon>mdi-home</v-icon>
        </v-btn>
      </v-bottom-navigation>
    </v-footer>
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