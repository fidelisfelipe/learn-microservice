<template>

    <ul>
      <li v-for="(erro, index) of erros" :key="index">
        <b>{{erro}}</b>
      </li>
    </ul>
    <code>localhost:8087/project-authuser/auth/signup</code>
    <form @submit.prevent="update">
      <label>username</label>
      <input type="text" placeholder="username" v-model="user.username">
      <label>email</label>
      <input type="email" placeholder="email" v-model="user.email">
      <label>userStatus</label>
      <input type="text" placeholder="userStatus" v-model="user.userStatus">
      <label>userType</label>
      <input type="text" placeholder="userType" v-model="user.userType">
      <label>fullname</label>
      <input type="text" placeholder="fullname" v-model="user.fullName">
      <label>phoneNumber</label>
      <input type="tel" placeholder="phoneNumber" v-model="user.phoneNumber">
      <label>cpf</label>
      <input type="text" placeholder="cpf" v-model="user.cpf">

      <button class="waves-effect waves-light btn-small">
        Save
        <i class="material-icons left">save</i>
      </button>
    </form>

  <div class="list">
     <h1>Users</h1>
<code>
  http://localhost:8087/project-authuser/users
</code>

    <table>
        <thead>
            <tr>
            <th>username</th>
            <th>email</th>
            <th>userStatus</th>
            <th>userType</th>
            <th>fullname</th>
            <th>phoneNumber</th>
            <th>cpf</th>
            <th>actions</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="user of userList" :key="user.userId">
            <td>{{user.username}}</td>
            <td>{{user.email}}</td>
            <td>{{user.userStatus}}</td>
            <td>{{user.userType}}</td>
            <td>{{user.fullName}}</td>
            <td>{{user.phoneNumber}}</td>
            <td>{{user.cpf}}</td>
            <td>
                <button @click="edit(user)" class="waves-effect btn-small blue light1" >
                <i class="material-icons">create</i>
                </button>
                <button @click="remove(user)" class="waves-effect btn-small blue light1" >
                <i class="material-icons">delete</i>
                </button>
            </td>
            </tr>
        </tbody>
    </table>
  </div>
</template>
<script >
import { defineComponent } from 'vue';
import User from '@/services/users'; // @ is an alias to /src

export default defineComponent({
  data(){
    return {
      userList: [],
      user: {
        userId: '',
        username: '',
        email:'',
        fullName: '',
        phoneNumber: '',
        cpf: '',
        password: ''
      },
      erros: []
    }
  },
  methods:{
    listar(){
      User.listar().then(resposta => {
      this.userList = resposta.data.content
    });
    },
    edit(user){
        this.user = user;
    },
    update(user){
      if(this.user.userId){
        User.update(this.user).then(resposta => {
          this.user = {}
          this.erros = {}
          alert('request succefully update')
          console.log('request succefully')
        }).catch(e => {
          this.erros = [e.response.data];
        });
      }
    },
    remove(user){
      User.remove(user.userId).then(resposta => {
          this.user = {}
          this.erros = {}
          alert('request succefully delete')
          console.log('request succefully')
          this.listar()
        }).catch(e => {
          this.erros = [e.response];
        });
    }
  },
  mounted(){
    this.listar();
  }

});
</script>