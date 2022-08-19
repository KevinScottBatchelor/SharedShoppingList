<template>
  <h1>Logout</h1>
</template>

<script>
export default {
  created() {
    this.$store.commit("LOGOUT");    
    this.$router.push("/login");
    this.$router.go();
  }
};
</script>


<style scoped>
div #nav {

  display: none;
  position: fixed;
  grid-area: nav;

}
</style>