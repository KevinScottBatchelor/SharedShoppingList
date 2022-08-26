<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">
        <img src="./assets/white MAJiK Logo.png" />
      </router-link>
      <router-link id="logout" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>

    <router-view />
  </div>
</template>

<style scoped>
#nav {
  margin: -1% -1% 1% -1%;
  position: fixed;
  display: flex;
  flex-direction: row;
  padding: 20px;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  background: linear-gradient(45deg, rgb(119, 115, 94), rgb(255, 255, 255));
  box-shadow: 0px 2px 6px -1px rgba(109, 103, 103, 0.712);
  height: 40px;
}

html {
  background: linear-gradient(
    45deg,
    rgba(255, 255, 255, 0.712),
    rgb(234, 234, 234)
  );
}

img {
  width: 120px;
}

#logout {
  font-weight: bold;
  padding-right: 20px;
  color: rgb(54, 52, 52);
}
</style>