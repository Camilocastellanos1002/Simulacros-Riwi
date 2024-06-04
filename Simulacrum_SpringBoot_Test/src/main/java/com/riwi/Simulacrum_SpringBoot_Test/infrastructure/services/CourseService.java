package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.CourseReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.CourseResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.EnrollmentBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.LessonBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.MessageCourseResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.UserBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Course;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Enrollment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Lesson;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Message;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.User;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.CourseRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.UserRepository;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.ICourseService;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;
import com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException;
import com.riwi.Simulacrum_SpringBoot_Test.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class CourseService implements ICourseService {

    /*inyeccion de dependencias */
        @Autowired
        private final CourseRepository courseRepository;
        @Autowired
        private final UserRepository userRepository;

    /* Metodos CRUD */

        @Override
        public CourseResp create(CourseReq request) {
            Course course = this.courseReqToEntity(request); //creacion de la entidad curso desde el request

            course.setElEnrollments(new ArrayList<>()); //lista vacia de inscripciones
            course.setLessons(new ArrayList<>());   //lista vacia de lecciones 
            course.setMessages(new ArrayList<>());  //lista vacia de mensajes
            course.setUser(findUser(request.getId_user())); //encontrar usuario a partir del id ingresado en el request

            return this.courseEntityToResponse(this.courseRepository.save(course)); //se regresa un response a partir de la entidad curso y se guarda en el repositorio


        }

        @SuppressWarnings("null")
        @Override
        public Page<CourseResp> getAll(int page, int size, SortType sortType) {
            if (page < 0) page =0; /* si es numero negativo, regresar a la pagina 0 */
                
            PageRequest pagination = null;
            switch (sortType) {
                case NONE -> pagination = PageRequest.of(page, size); 
                case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); //organizar de forma ascendente por titulo de lesson
                case DESC -> pagination  = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).descending()); //organizar de forma descendente por titulo de lesson
            }

            return this.courseRepository.findAll(pagination).map(this::courseEntityToResponse); //se nesesita devolver un response de la entidad 
        }

        @Override
        public CourseResp getById(Long id) {
            return this.courseEntityToResponse(this.findCourse(id)); //se busca el curso y se construye la entidad curso del response
        }

        @Override
        public CourseResp update(CourseReq request, Long id) {
            Course course = this.findCourse(id); //se busca el curso por id para actualizar

            Course courseUpdate = this.courseReqToEntity(request); //se crea la entidad curso desde el request recibido

            courseUpdate.setCourse_id(course.getCourse_id()); //se actualiza id
            courseUpdate.setLessons(course.getLessons()); //se guardan lecciones
            courseUpdate.setElEnrollments(course.getElEnrollments()); //se guarda inscripciones
            courseUpdate.setUser(course.getUser()); //se guarda usuario
            courseUpdate.setMessages(course.getMessages()); //se guardan mensajes

            return this.courseEntityToResponse(this.courseRepository.save(courseUpdate)); //se guarda el curso actualizado
        }

        @Override
        public void delete(Long id) {
            this.courseRepository.delete(this.findCourse(id)); //se busca el curso con el id ingresado y se elimina del repositorio
        }
    
    /* Metodos propios */
        
        /* Entidad a response */
            private CourseResp courseEntityToResponse(Course entity){
                /* valor a las tablas relacionadas */

                UserBasicResp user = UserBasicResp.builder()
                                        //construccion de objeto usuario
                                        .user_id(entity.getUser().getUser_id())
                                        .user_name(entity.getUser().getUser_name())
                                        .password(entity.getUser().getPassword())
                                        .email(entity.getUser().getEmail())
                                        .full_name(entity.getUser().getFull_name())
                                        .role(entity.getUser().getRole())
                                        .build();
                

                List<LessonBasicResp> lesson = entity.getLessons() //se obtiene lista de lecciones
                                                .stream()   //conversion a coleccion
                                                .map(this::lessonEntityToResponse) //mapear la lista en entidades a response
                                                .collect(Collectors.toList()); //coleccion a lista
                                                

                List<EnrollmentBasicResp> enrollment = entity.getElEnrollments() //se obtiene lista de inscripciones
                                                        .stream()   //conversion a coleccion
                                                        .map(this::enrollmentEntityToResponse) //mapeo de lista de entidades a response
                                                        .collect(Collectors.toList());  //coleccion a lista

                List<MessageCourseResp> messages = entity.getMessages() //lista de mensajes
                                                    .stream()   //conversion
                                                    .map(this::messageCourseEntityToResponse)    //mapeo de lista entidades a response
                                                    .collect(Collectors.toList());  //coleccion a lista
                
                return CourseResp.builder()
                        //construccion del curso
                        .course_id(entity.getCourse_id())
                        .course_name(entity.getCourse_name())
                        .description(entity.getDescription())
                        .user(user)
                        .elEnrollments(enrollment)
                        .lessons(lesson)
                        .messages(messages)
                        .build();
            }

            private LessonBasicResp lessonEntityToResponse(Lesson entity){
                return LessonBasicResp.builder()
                        .lesson_id(entity.getLesson_id())
                        .lesson_title(entity.getLesson_title())
                        .context(entity.getContext())
                        .build();
            }

            private EnrollmentBasicResp enrollmentEntityToResponse(Enrollment entity){
                return EnrollmentBasicResp.builder()
                        .enrollment_id(entity.getEnrollment_id())
                        .enrollment_date(entity.getEnrollment_date())
                        .build();
            }

            private MessageCourseResp messageCourseEntityToResponse(Message entity){
                /* usuario tipo transmisor */
                UserBasicResp user_sender = new UserBasicResp();
                /* usario tipo rececptor */
                UserBasicResp user_receiver = new UserBasicResp();

                if (entity.getSender() != null) { //si existe el transmisor
                    BeanUtils.copyProperties(entity.getSender(), user_sender); //copiar las propiedades del transmisor al objeto local del sender
                }
                if (entity.getReceiver() != null) { //si existe el receotor
                    BeanUtils.copyProperties(entity.getReceiver(), user_receiver); //copiar las propiedades del transmisor al objeto local del receiver
                }

                return MessageCourseResp.builder() //construccion del objeto mensaje utilizado por curso
                        .message_id(entity.getMessage_id()) //id del mensaje
                        .message_context(entity.getMessage_context()) //contexto
                        .send_date(entity.getSent_date()) //fecha del mensaje
                        .user_sender(user_sender) //usario transmisor
                        .user_receiver(user_receiver) //usuario receptor
                        .build();
            }

        /* Request a entidad  */
        private Course courseReqToEntity(CourseReq request){
            return Course.builder()
                    .course_name(request.getCourse_name())
                    .description(request.getDescription())
                    .user(findUser(request.getId_user()))
                    .build();
        }


        /* Buscadores */
        private User findUser(Long id){ //funcion para buscar usuario
            return this.userRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("User")));
        }

        private Course findCourse(Long id){ //funcion para buscar curso
            return this.courseRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("Course")));
        }
}
