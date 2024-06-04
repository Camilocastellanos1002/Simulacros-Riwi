package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.UserReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.AssignmentBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.CourseBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.EnrollmentBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.MessageReceiverResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.MessageSenderResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.SubmissionBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.UserBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.UserResp;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Course;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Enrollment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Message;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Submission;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.User;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.UserRepository;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.IUserService;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.helpers.EmailHelper;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;
import com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException;
import com.riwi.Simulacrum_SpringBoot_Test.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class UserService implements IUserService {

    /* dependencias */
        @Autowired
        private final UserRepository userRepository;

        /* inyeccion del helper*/
        @Autowired
        private final EmailHelper emailHelper;

    /*metodos CRUD */

        @Override
        public UserResp create(UserReq request) {
            User user = this.UserReqToEntity(request); //creacion de la entidad usuario desdel el request

            user.setCourses(new ArrayList<>()); //lista vacia de cursos
            user.setEnrollments(new ArrayList<>()); //lista vacia de registros
            user.setSubmisions(new ArrayList<>()); //lista vacia de entregas
            user.setSender_Messages(new ArrayList<>()); //lista vacia de mensajes como transmisor
            user.setReceiver_Messages(new ArrayList<>()); //lista vacia de mensajes como receptor

            /* valida si se creo el usuario correctamente, por lo que se envia un mensaje */
            if (Objects.nonNull(user.getEmail())) {
                this.emailHelper.sendMail(user.getEmail(), user.getUser_name(), user.getFull_name(),"fecha yyyy-MM-dd XXXXXXXXXXXXXX");
            }

            return this.userEntityToResponse(this.userRepository.save(user)); //se regresa un response a partir de la entidad usuario y se guarda en el repositorio
        }

        @SuppressWarnings("null")
        @Override
        public Page<UserResp> getAll(int page, int size, SortType sortType) {
            if (page < 0) page =0; /* si es numero negativo, regresar a la pagina 0 */
                
            PageRequest pagination = null;
            switch (sortType) {
                case NONE -> pagination = PageRequest.of(page, size); 
                case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); //organizar de forma ascendente por titulo de lesson
                case DESC -> pagination  = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).descending()); //organizar de forma descendente por titulo de lesson
            }

            return this.userRepository.findAll(pagination).map(this::userEntityToResponse); //se nesesita devolver un response de la entidad 
        }

        @Override
        public UserResp getById(Long id) {
            return this.userEntityToResponse(this.findUser(id)); //se busca el usuario por id y se construye la endidad del response
        }

        @Override
        public UserResp update(UserReq request, Long id) {
            User user = this.findUser(id); //se busca el usuario por id para actualizar

            User userUpdate = this.UserReqToEntity(request); //se crea la entidad usuario desde el request

            userUpdate.setCourses(user.getCourses()); //se guarda los cursos
            userUpdate.setEnrollments(user.getEnrollments()); //se guarda las inscripciones
            userUpdate.setSubmisions(user.getSubmisions()); //se guardan las entregas
            userUpdate.setSender_Messages(user.getSender_Messages()); //se guardan los mensajes como transmisor
            userUpdate.setReceiver_Messages(user.getReceiver_Messages()); //se guardan los mensajes como receptor

            return this.userEntityToResponse(this.userRepository.save(userUpdate)); // se guarda el usuario actualizado


        }

        @Override
        public void delete(Long id) {
            this.userRepository.delete(this.findUser(id)); //se busca el usuario con el id ingresado y se elmina del repositorio
        }
    
    /* Metodos propios */

        /* Entidad a response */
        private UserResp userEntityToResponse(User entity){
            /* valor a las tablas relacionadas */
            List<EnrollmentBasicResp> enrollment = entity.getEnrollments() //se obtiene lista de inscripciones
                                                        .stream()   //conversion a coleccion
                                                        .map(this::enrollmentEntityToResponse) //mapeo de lista de entidades a response
                                                        .collect(Collectors.toList());  //coleccion a lista
            
            List<SubmissionBasicResp> submission = entity.getSubmisions() //se obtiene lista de entregas
                                                        .stream()   //conversion a coleccion
                                                        .map(this::submissionEntityToResponse) //mapeo de lista de entidades a response
                                                        .collect(Collectors.toList());  //coleccion a lista
            
            List<CourseBasicResp> course = entity.getCourses() //se obtiene lista de entregas
                                                        .stream()   //conversion a coleccion
                                                        .map(this::courseEntityToResponse) //mapeo de lista de entidades a response
                                                        .collect(Collectors.toList());  //coleccion a lista
            
            List<MessageSenderResp> message_send = entity.getSender_Messages() //se obtiene lista de entregas
                                                        .stream()   //conversion a coleccion
                                                        .map(this::messageSenderEntityToResponse) //mapeo de lista de entidades a response
                                                        .collect(Collectors.toList());  //coleccion a lista
        
            List<MessageReceiverResp> message_recei = entity.getReceiver_Messages() //se obtiene lista de entregas
                                                        .stream()   //conversion a coleccion
                                                        .map(this::messageReceiverEntityToResponse) //mapeo de lista de entidades a response
                                                        .collect(Collectors.toList());  //coleccion a lista

            return UserResp.builder()
                //construccion de objeto usuario
                .user_id(entity.getUser_id())
                .user_name(entity.getUser_name())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .full_name(entity.getFull_name())
                .role(entity.getRole())
                .messages_sender(message_send)
                .messages_receiver(message_recei)
                .courses(course)
                .submissions(submission)
                .enrollments(enrollment)
                .build();                                     
        }
        private EnrollmentBasicResp enrollmentEntityToResponse(Enrollment entity){
            return EnrollmentBasicResp.builder()
                    .enrollment_id(entity.getEnrollment_id())
                    .enrollment_date(entity.getEnrollment_date())
                    .build();
        }
        private SubmissionBasicResp submissionEntityToResponse(Submission entity){
            
            /* Se copian las propiedades assignment */
            AssignmentBasicResp assignment = new AssignmentBasicResp(); 

            if (entity.getAssignment() != null) {//si existe tareas
                BeanUtils.copyProperties(entity.getAssignment(), assignment); //copiar las propiedades de la tarea al objeto local
            }

            return SubmissionBasicResp.builder()
                    .submission_id(entity.getSubmission_id())
                    .submission_date(entity.getSubmission_date())
                    .context(entity.getContext())
                    .grade(entity.getGrade())
                    .assignmentBasicResp(assignment)
                    .build();
        }
        private CourseBasicResp courseEntityToResponse(Course entity){
            return CourseBasicResp.builder()
                    .course_id(entity.getCourse_id())
                    .course_name(entity.getCourse_name())
                    .description(entity.getDescription())
                    .build();
        }
        private MessageSenderResp messageSenderEntityToResponse(Message entity){
            /* usuario tipo transmisor */
            UserBasicResp user_sender = new UserBasicResp();
            if (entity.getSender() != null) { //si existe el transmisor
                BeanUtils.copyProperties(entity.getSender(), user_sender); //copiar las propiedades del transmisor al objeto local del sender
            }
            return MessageSenderResp.builder() //construccion del objeto mensaje del transmisor
                        .message_id(entity.getMessage_id()) //id del mensaje
                        .message_context(entity.getMessage_context()) //contexto
                        .send_date(entity.getSent_date()) //fecha del mensaje
                        .user_sender(user_sender) //usario transmisor
                        .build();
        }
        private MessageReceiverResp messageReceiverEntityToResponse(Message entity){
            /* usario tipo rececptor */
            UserBasicResp user_receiver = new UserBasicResp();
            if (entity.getReceiver() != null) { //si existe el receotor
                BeanUtils.copyProperties(entity.getReceiver(), user_receiver); //copiar las propiedades del transmisor al objeto local del receiver
            }
            return MessageReceiverResp.builder() //construccion del objeto mensaje utilizado por curso
                        .message_id(entity.getMessage_id()) //id del mensaje
                        .message_context(entity.getMessage_context()) //contexto
                        .send_date(entity.getSent_date()) //fecha del mensaje
                        .user_receiver(user_receiver) //usuario receptor
                        .build();
        }



        /* request a entidad */
        private User UserReqToEntity(UserReq request){
            return User.builder()
                .user_name(request.getUser_name())
                .password(request.getPassword())
                .email(request.getEmail())
                .full_name(request.getFull_name())
                .role(request.getRole())
                .build();
        }

        /* buscadores */
        private User findUser(Long id){ //funcion para buscar usuario
            return this.userRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("User")));
        }

        
}
