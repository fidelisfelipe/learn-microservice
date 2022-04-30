<template>
  <div class="signup">
    <h1>Signup</h1>

    <ul>
      <li v-for="(erro, index) of erros" :key="index">
        <b>{{erro}}</b>
      </li>
    </ul>

    <form @submit.prevent="signup">
    //http://localhost:8087/project-authuser/auth/signup
      <label>username</label>
      <input type="text" placeholder="username" v-model="user.username">
      <label>email</label>
      <input type="email" placeholder="email" v-model="user.email">
      <label>fullname</label>
      <input type="text" placeholder="fullname" v-model="user.fullName">
      <label>phoneNumber</label>
      <input type="tel" placeholder="phoneNumber" v-model="user.phoneNumber">
      <label>cpf</label>
      <input type="text" placeholder="cpf" v-model="user.cpf">

       <label>password</label>
      <input type="password" placeholder="password" v-model="user.password">

      <button class="waves-effect waves-light btn-small">
        Save
        <i class="material-icons left">save</i>
      </button>
    </form>

  </div>
</template>
<script >
import { defineComponent } from 'vue';
import User from '@/services/users'; // @ is an alias to /src

export default defineComponent({
  data(){
    return {
      user: {
        username: 'usertwo',
        email:'usertwo@email.com',
        fullName: 'User Two Full Name',
        phoneNumber: '+111111111111',
        cpf: '11111111111',
        password: '123456'
      },
      erros: []
    }
  },
  
  methods:{
    signup(){
      User.signup(this.user).then(resposta => {
        this.user = {}
        this.erros = {}
        alert('request succefully signup')
        console.log('request succefully')
      }).catch(e => {
        this.erros = [e.response.data];
      });
    }
  }

});
</script>
