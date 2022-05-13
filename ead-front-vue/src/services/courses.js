import {http} from './config'

export default {
    
    listar:() => {
        return http.get('project-course/courses');
    },
    save:(course) => {
        return http.post('project-course/courses', course)
    },
    remove:(courseId) => {
        console.log('course.courseId:'+courseId)
        return http.delete('project-course/courses/'+courseId)
    },
    
}