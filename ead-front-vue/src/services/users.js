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
    remove:(userId) => {
        console.log('user.userId:'+userId)
        return http.delete('project-authuser/users/'+userId)
    },
    
}