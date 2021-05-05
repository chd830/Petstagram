<template>
  <div id="form-2">
    <v-text-field
      label="USER EMAIL"
      hide-details="auto"
    ></v-text-field>
    <v-text-field label="PASSWORD"></v-text-field>
    <v-btn v-on:click="signin" depressed>
      Normal
    </v-btn>
  </div>
</template>

<script>
export default {
  methods: {
    signin: function() {
      console.log("clicked")
    }
  }
}
</script>
