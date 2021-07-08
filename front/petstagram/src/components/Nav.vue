<template>
<div>
  <!-- type 글씨색 variant 배경색 -->
  <b-navbar bg-info="black" toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="/">Petstagram</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="/posts" v-if="login">Post</b-nav-item>
        <b-nav-item href="/posts/insert" v-if="login">PostInsert</b-nav-item>
        <b-nav-item href="/mypage" v-if="login">MyPage</b-nav-item>
        <b-nav-item href="/signup">Signup</b-nav-item>
        <b-nav-item href="/signin">SignIn</b-nav-item>
        <b-nav-item href="/Logout" v-if="login">Logout</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>
<script>
export default {
  name: "app",  
  data() {
    return {
      login: false,
      userEmail: localStorage.getItem("userEmail"),
    }
  },
  created() {
      console.log("==========")
      console.log(this.userEmail)
      if(this.userEmail == null)
        this.login = false;
      else
        this.login = true;
      console.log(this.login)
      console.log("==========")
  }
}
</script>

