<template>
  <div>
    <!-- 상위 로고 -->
    <v-card
      color="grey lighten-4"
      flat
      tile
    >
      <v-toolbar dense dark>
        <v-app-bar-nav-icon></v-app-bar-nav-icon>

        <v-toolbar-title>Petstagram</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>

        <v-btn icon @click="insertposts">
          <v-icon>note_add</v-icon>
        </v-btn>

        <v-btn icon>
          <v-icon>settings</v-icon>
        </v-btn>
      </v-toolbar>
    </v-card>
    <!-- posts -->
    <v-container>
      <v-row>
        <v-spacer></v-spacer>
        <v-col
          v-for="post in posts"
          :key="post.postNo"
          cols="12"
        >
        {{post}}
          <v-card>
            <v-img
              height="450px"
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
        </v-col>
      </v-row>
    </v-container>
    
    <!-- footer -->
    <v-footer
    absolute>
      <v-bottom-navigation
        v-model="bottomNav"
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
        bottomNav: 3,
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
      }
    },
    mounted() {
      this.getPosts();
    }
    // computed: {
    //   color () {
    //     switch (this.bottomNav) {
    //       case 0: return {'color':'blue-grey'}
    //       case 1: return 'teal'
    //       case 2: return 'brown'
    //       case 3: return 'indigo'
    //     }
    //   },
    // },
  }
</script>