<template>
  <div id="main-container">
    <div id="form">
      <div class="pyramid">
        <div class="base"></div>
        <div class="side one"></div>
        <div class="side two"></div>
        <div class="side three"></div>
        <div class="side four"></div>
      </div>

      <img src="../assets/white MAJiK Logo.png" />
      <h1 class="login-header">Welcome Back!</h1>
      <div
        class="alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <div
        class="alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid email address and password!</div>
      <form class="form-signin" @submit.prevent="login">
        <label for="username" class="username-label">Email</label>
        <input
          type="text"
          id="username"
          class="username-input"
          placeholder=" Email Address"
          v-model="user.username"
          required
          autofocus
        />
        <label for="password" class="password-label">Password</label>
        <input
          type="password"
          id="password"
          class="password-input"
          placeholder=" Password"
          v-model="user.password"
          required
        />

        <button class="signin-button" type="submit">Sign in</button>
        <router-link :to="{ name: 'register' }" class="register-link">Sign Up!</router-link>
      </form>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
            this.$forceUpdate();
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
* {
  font-family: Tahoma, Verdana, Segoe, sans-serif;
  box-sizing: border-box;
}

img {
  width: 40%;
  min-width: 360px;
  display: block;
  margin: 0 auto;
}

.alert-success {
  text-align: center;
  color: rgb(54, 52, 52);
}

.alert-danger {
  text-align: center;
  color: rgb(227, 108, 108);
}

body {
  background-color: white;
  height: 100vh;
}

div #nav {
  display: none;
}

.login-header {
  margin: 0px;
  padding-bottom: 30px;
  text-align: center;
  color: rgb(54, 52, 52);
}

#main-container {
  height: 100vh;
  display: grid;
  grid-template-columns: 1fr;
  place-items: center;
}

#form {
  width: 60%;
  border-radius: 24px;
  background-color: rgba(135, 122, 88, 0.45);
  box-shadow: 0px 2px 6px -1px rgba(109, 103, 103, 0.712);
  margin-top: 75px;
  padding-top: 20px;
  padding-bottom: 20px;
}
.form-signin {
  align-items: center;
  display: grid;
  grid-template-columns: 6fr 2fr 4fr 6fr;
  grid-template-areas:
    ". username-label username-input ."
    ". password-label password-input ."
    ". . alert ."
    ". register-link signin-button .";

  gap: 25px;
}

label {
  font-weight: 10;
  font-size: 16px;
  color: rgb(47, 47, 47);
}

.username-label {
  grid-area: username-label;
}

.username-input {
  grid-area: username-input;
}

.password-label {
  grid-area: password-label;
}

.password-input {
  grid-area: password-input;
}

.signin-button {
  grid-area: signin-button;
}

.register-link {
  color: rgb(69, 69, 69);
  grid-area: register-link;
}

input {
  font-size: 16px;
  padding: 10px;
  height: 56px;
  border: none;
  border-radius: 12px;
  border-bottom: solid 1px rgba(0, 0, 0, 0.1);
  background: #fff;
  width: auto;
  box-sizing: border-box;
  transition: all 0.3s linear;
  color: #000;
  font-weight: 400;
}

.signin-button {
  width: auto;
  min-width: 50px;
  border-radius: 12px;
  text-align: center;
  padding: 15px 40px;
  color: rgb(80, 80, 80);
  font-size: 14px;
  box-shadow: 0px 2px 6px -1px rgba(109, 103, 103, 0.712);
  border: none;
  background-color: rgba(135, 122, 88, 0.45);
}

.signin-button:hover {
  background-color: rgb(60, 60, 60);
  color: rgb(217, 217, 217);
  transition: 0.6s;
}

.signin-button:active {
  background-color: rgb(128, 128, 128);
  border: 4px solid rgb(53, 53, 53);
  transition: 0.1s;
}

.pyramid {
  margin: 0 auto;
  margin-bottom: 20px;
  position: relative;
  width: 200px;
  height: 200px;
  transform-style: preserve-3d;
  transform: rotateY(326deg) rotateX(2deg) rotateZ(359deg);
  animation: rotate 5s linear infinite;
}
.side {
  width: 0;
  height: 0;
  position: absolute;
  opacity: 0.7;
  border: 100px solid transparent;
  border-bottom: 200px solid rgba(109, 103, 103, 0.712);
  border-top: 0px;
}
.one {
  transform: rotateX(30deg);
  transform-origin: 0 0;
}
.two {
  transform-origin: 100px 0;
  transform: rotateY(90deg) rotateX(-30deg);
  border-bottom-color: rgba(109, 103, 103, 0.712);
}
.three {
  transform-origin: 100px 0;
  transform: rotateY(270deg) rotateX(-30deg);
  border-bottom-color: rgba(109, 103, 103, 0.712);
}
.four {
  transform-origin: 100px 0;
  transform: rotateY(0) rotateX(-30deg);
  border-bottom-color: rgba(109, 103, 103, 0.712);
}
.base {
  position: absolute;
  width: 100%;
  height: 100%;
  transform: translateY(73px) rotateX(90deg);
  border: 0;
  background: rgba(109, 103, 103, 0.712);
}
@keyframes rotate {
  from {
    transform: rotateY(0) rotateX(30deg) rotateZ(30deg);
  }
  to {
    transform: rotateY(360deg) rotateX(30deg) rotateZ(30deg);
  }
}
</style>
