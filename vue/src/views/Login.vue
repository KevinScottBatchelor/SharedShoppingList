<template>
<div id="main-container">
  <div id="form">
    <img src="../assets/MAJiK Logo.png"/>
    <h1 class="login-header">Welcome Back!</h1>
    <form class="form-signin" @submit.prevent="login">
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid email address and password!
      </div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.
      </div>
      <label for="username" class="username-label">Email </label>
      <input
        type="text"
        id="username"
        class="username-input"
        placeholder="Email Address"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="password-label">Password </label>
      <input
        type="password"
        id="password"
        class="password-input"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <button class="signin-button" type="submit">Sign in</button>
      <router-link :to="{ name: 'register' }" class="register-link">Sign up!</router-link>
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

<style>

*{

    font-family: Tahoma, Verdana, Segoe, sans-serif;
    box-sizing: border-box;

}

body {
  
  background-color: white;
  height: 100vh;

}

#nav {

  display: block;
  position: fixed;
  grid-area: nav;

}

.login-header {
    
    margin: 0px;
    padding-bottom: 50px;
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

  padding-bottom: 75px;

}
.form-signin {

  align-items: center;
  display: grid;
  grid-template-columns: 2fr 2fr 3fr 2fr;
  grid-template-areas:  ". username-label username-input ."
                        ". password-label password-input ."
                        ". . signin-button ."
                        ". register-link register-link .";
                        
  gap: 25px;

}

label {

  font-weight: 10;
  font-size:  18px;

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

  grid-area: register-link;

}

input {

  font-size:  16px; 
  padding:  20px 0px; 
  height:  56px; 
  border:  none; 
  border-bottom:  solid 1px rgba(0,0,0,.1); 
  background:  #fff; 
  width:  auto; 
  box-sizing:  border-box; 
  transition:  all .3s linear; 
  color:  #000; 
  font-weight:  400;
  
}

button {
  width:  auto;
  min-width:  50px;
  border-radius:  24px; 
  text-align:  center; 
  padding:  15px 40px;  
  color:  rgb(80, 80, 80); 
  font-size:  14px; 
  box-shadow:  0px 2px 6px -1px rgba(109, 103, 103, 0.712); 
  border:  none; 
}
</style>
