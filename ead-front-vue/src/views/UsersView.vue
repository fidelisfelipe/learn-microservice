<template>

    <ul>
      <li v-for="(erro, index) of erros" :key="index">
        <b>{{erro}}</b>
      </li>
    </ul>

    <form @submit.prevent="update" id="update">
      <h1>Update</h1>

      <label>username</label>
      <input type="text" placeholder="username" v-model="user.username" disabled>
      <label>email</label>
      <input type="email" placeholder="email" v-model="user.email" disabled>
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

      <button class="waves-effect waves-light btn-small" >
        Update
        <i class="material-icons left">save</i>
      </button>
    </form>

    <form @submit.prevent="updatePassword" id="updatePassword">
        <h1>Update Password</h1>
        
        <label>old password</label>
        <input type="oldPassword" placeholder="oldPassword" v-model="user.oldPassword">
        <label>cpf</label>
        <input type="text" placeholder="cpf" v-model="user.cpf">

        <label>password</label>
        <input type="password" placeholder="password" v-model="user.password">

        <button class="waves-effect waves-light btn-small" >
          Update Password
          <i class="material-icons left">save</i>
        </button>
      </form>

      <form @submit.prevent="updateImage" id="updateImage">
      <h1>Update imageURL</h1>
        
        <label>imageURL</label>
        <input type="text" placeholder="imageURL" v-model="user.imageURL">

        <button class="waves-effect waves-light btn-small" >
          Update imageURL
          <i class="material-icons left">save</i>
        </button>
      </form>

      <form @submit.prevent="instructor" id="instructor">
      <h1>Instructor Subscription</h1>
        
        <label>userId</label>
        <input type="text" placeholder="userId" v-model="user.userId">

        <button class="waves-effect waves-light btn-small" >
          Subscription
          <i class="material-icons left">save</i>
        </button>
      </form>


  <div class="list">
     <h1>Users</h1>

    <table>
        <thead>
           
           <tr>
            <th>userId</th>
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
            <td>{{user.userId}}</td>
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

<form @submit.prevent="saveCourse" id="saveCourse">
      <h1>saveCourse</h1>

      <label>name</label>
      <input type="text" placeholder="name" v-model="course.name" >
      <label>description</label>
      <input type="text" placeholder="description" v-model="course.description" >
      <label>courseStatus</label>
      <input type="text" placeholder="courseStatus" v-model="course.courseStatus">
      <label>courseLevel</label>
      <input type="text" placeholder="courseLevel" v-model="course.courseLevel">
      <label>userInstructor</label>
      <input type="text" placeholder="userInstructor" v-model="course.userInstructor">

      <button class="waves-effect waves-light btn-small" >
        saveCourse
        <i class="material-icons left">save</i>
      </button>
</form>

  <div class="list">
     <h1>Course</h1>

    <table>
        <thead>
            <tr>
            <th>courseId</th>
            <th>name</th>
            <th>description</th>
            <th>courseStatus</th>
            <th>courseLevel</th>
            <th>userInstructor</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="course of courseList" :key="course.courseId">
            <td>{{course.courseId}}</td>
            <td>{{course.name}}</td>
            <td>{{course.description}}</td>
            <td>{{course.courseStatus}}</td>
            <td>{{course.courseLevel}}</td>
            <td>{{course.userInstructor}}</td>
            <td>
                <button @click="editCourse(course)" class="waves-effect btn-small blue light1" >
                <i class="material-icons">create</i>
                </button>
                <button @click="removeCourse(course)" class="waves-effect btn-small blue light1" >
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
import Course from '@/services/courses'; // @ is an alias to /src

export default defineComponent({
  data(){
    return {
      userList: [],
      courseList: [],
      user: {
        userId: '',
        username: '',
        email:'',
        fullName: '',
        phoneNumber: '',
        cpf: '',
        password: ''
      },
      course: {
        name: '',
        description: '',
        courseStatus: 'INPROGRESS',
        courseLevel: 'BEGINNER',
        userInstructor: ''
      },
      erros: []
    }
  },
  methods:{
    listar(){
      User.listar().then(resposta => {
        this.userList = resposta.data.content
      });
      Course.listar().then(resposta => {
        this.courseList = resposta.data.content
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
           this.listar()       
        }).catch(e => {       
          this.erros = [e.response.data];
        });
      }
    },
    updateImage(user){
      if(this.user.userId){
        User.updateImage(this.user).then(resposta => {
          this.user = {}
          this.erros = {}
          alert('request succefully updateImage')
          console.log('request succefully')
           this.listar()       
        }).catch(e => {       
          this.erros = [e.response.data];
        });
      }
    },
    updatePassword(){
      User.updatePassword(this.user).then(resposta => {
        this.user = {}
        this.erros = {}
        alert('request succefully updatePassword')
        console.log('request succefully')
         this.listar()
      }).catch(e => {
        this.erros = [e.response.data];
      });
    },
     instructor(){
      User.instructor(this.user).then(resposta => {
        this.user = {}
        this.erros = {}
        alert('request succefully instructor')
        console.log('request succefully')
         this.listar()
      }).catch(e => {
        this.erros = [e.response.data];
      });
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
    },
    saveCourse(course){
        Course.save(course).then(resposta => {
          this.course = {}
          this.erros = {}
          alert('request succefully saveCourse')
          console.log('request succefully')
           this.listar()       
        }).catch(e => {       
          this.erros = [e.response];
        });
    },
    removeCourse(course){
      Course.remove(course.courseId).then(resposta => {
          this.course = {}
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