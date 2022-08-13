<template>
  <div id="main-container">
  <div id="form">
    <img src="../assets/MAJiK Logo.png"/>
    <h1 class="register-header">Create Account</h1>
    <form class="form-register" @submit.prevent="register">
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
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
      <label for="password" class="password-label">Password</label>
      <input
        type="password"
        id="password"
        class="password-input"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <label for="password" class="password-label-confirm">Password</label>
      <input
        type="password"
        id="confirmPassword"
        class="password-input-confirm"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <router-link :to="{ name: 'login' }" class="login-link">Have an account?</router-link>
      <button class="create-account-button" type="submit">
        Create Account
      </button>
    </form>
  </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
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
  padding: 0;
  margin: 0;

}

#nav {

  display: block;
  position: fixed;
  grid-area: nav;

}

.register-header {

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

.form-register {
  align-items: center;
  display: grid;
  grid-template-columns: 2fr 2fr 3fr 2fr;
  grid-template-areas:  ". username-label username-input ."
                        ". password-label password-input ."
                        ". password-label-confirm password-confirm ."
                        ". . create-account-button ."
                        ". login-link login-link .";
                        
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

.password-label-confirm {

  grid-area: password-label-confirm;

}

.password-input {

  grid-area: password-input;

}

.password-input-confirm {

  grid-area: password-confirm;

}

.create-account-button {

  grid-area: create-account-button;

}

.login-link {

  grid-area: login-link;

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
