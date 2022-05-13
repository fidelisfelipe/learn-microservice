import {http} from './config'

export default {
    
    signup:(user) => {
        return http.post('project-authuser/auth/signup', user);
    },
    listar:() => {
        return http.get('project-authuser/users');
    },
    update:(user) => {
        return http.put('project-authuser/users/'+user.userId, user)
    },
    updatePassword:(user) => {
        return http.put('project-authuser/users/'+user.userId+'/updatePassword', user)
    },
    instructor:(user) => {
        return http.post('project-authuser/instructors/subscription', user)
    },
    updateImage:(user) => {
        return http.put('project-authuser/users/'+user.userId+'/image', user)
    },
    remove:(userId) => {
        console.log('user.userId:'+userId)
        return http.delete('project-authuser/users/'+userId)
    },
    
}