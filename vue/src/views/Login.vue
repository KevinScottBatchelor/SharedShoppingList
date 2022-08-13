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
        placeholder=" Email Address"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="password-label">Password </label>
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

img {
  padding-top: 50px;
  min-width: 100px;
  display: block;
  margin: 0 auto;
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
  width: 80%;
  border-radius: 24px;
  background-color: rgba(0, 0, 0, 0.03);
  box-shadow: 0px 2px 6px -1px rgba(109, 103, 103, 0.712);
}
.form-signin {

  align-items: center;
  display: grid;
  grid-template-columns: 6fr 2fr 4fr 6fr;
  grid-template-areas:  ". username-label username-input ."
                        ". password-label password-input ."
                        ". . alert ."
                        ". register-link signin-button .";
                        
                        
  gap: 25px;
  

}

label {

  font-weight: 10;
  font-size:  16px;
  color: grey;

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

  font-size:  16px; 
  padding:  20px 0px; 
  height:  56px; 
  border:  none; 
  border-radius: 12px;
  border-bottom:  solid 1px rgba(0,0,0,.1); 
  background:  #fff; 
  width:  auto; 
  box-sizing:  border-box; 
  transition:  all .3s linear; 
  color:  #000; 
  font-weight:  400;
  
}

.signin-button {
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

.signin-button:hover {
  background-color:  rgb(60, 60, 60);
  color: rgb(217, 217, 217);
  transition: 0.6s;
}

.signin-button:active {
  background-color: rgb(128, 128, 128);
  border: 4px solid rgb(53, 53, 53);
  transition: 0.1s;
  
}

</style>
