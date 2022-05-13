<template>
  <div class="signup">

    <ul>
      <li v-for="(erro, index) of erros" :key="index">
        <b>{{erro}}</b>
      </li>
    </ul>

    <form @submit.prevent="signup">
    <h1>Signup</h1>
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

      <button class="waves-effect waves-light btn-small" >
        Signup
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
        username: 'user'+ Math.random().toString(5).substring(2,7),
        email:'user'+Math.random().toString(5).substring(2,7)+'@email.com',
        fullName: 'User '+Math.random().toString(5).substring(2,7)+' Full Name',
        phoneNumber: Math.random().toString(11).substring(2,12),
        cpf: Math.random().toString(11).substring(2,12),
        password: '123456'
      },
      erros: [],
      isLoading: null,
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
    },
    
  }

});
</script>
