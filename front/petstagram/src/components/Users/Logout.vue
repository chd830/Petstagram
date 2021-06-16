<template>
<div>
  Logout
</div>
</template>

<script>
export default {
  name: 'Logout',
  created() {
    localStorage.clear();
    window.location.href='/'
  }
}
</script>