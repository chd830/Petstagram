<template>
<div>
  Logout
</div>
</template>

<script>
import router from '../../router/index'

export default {
  name: 'Logout',
  created() {
    localStorage.clear();
    router.push("/")
  }
}
</script>