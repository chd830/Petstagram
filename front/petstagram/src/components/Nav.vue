<template>
<div>
  <!-- type 글씨색 variant 배경색 -->
  <b-navbar bg-info="black" toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="/">Petstagram</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="/posts">Post</b-nav-item>
        <b-nav-item href="/posts/insert">PostInsert</b-nav-item>
        <b-nav-item href="/mypage">MyPage</b-nav-item>
        <b-nav-item href="/signup">Signup</b-nav-item>
        <b-nav-item href="/signin">SignIn</b-nav-item>
        <b-nav-item href="/Logout">Logout</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>
