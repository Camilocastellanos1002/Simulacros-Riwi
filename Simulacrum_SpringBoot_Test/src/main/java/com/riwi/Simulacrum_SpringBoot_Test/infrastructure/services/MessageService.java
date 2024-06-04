package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.MessageReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.CourseBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.MessageResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.UserBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Course;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Message;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.User;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.CourseRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.MessageRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.UserRepository;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.IMessageService;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;
import com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException;
import com.riwi.Simulacrum_SpringBoot_Test.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class MessageService implements IMessageService{

    /* dependencias */
        @Autowired
        private final MessageRepository messageRepository;

        @Autowired
        private final CourseRepository courseRepository;

        @Autowired
        private final UserRepository userRepository;
    

    /* metodos CRUD */
        @Override
        public MessageResp create(MessageReq request) {
            Message message = this.MessageReqToEntity(request); //creacion de la entidad mensaje desde la info del request

            message.setCourse(findCourse(request.getId_course())); //se encuentra y captura el curso por el id ingresado en el request
            message.setSender(findUser(request.getId_sender())); //se captura el id del usuario transmisor
            message.setReceiver(findUser(request.getId_receiver())); //se captura el id del usuario receptor

            return this.messageEntityToResponse(this.messageRepository.save(message)); //se responde a partir de la entidad mensaje guardada en el repositorio
        }

        @SuppressWarnings("null")
        @Override
        public Page<MessageResp> getAll(int page, int size, SortType sortType) {
            if (page < 0) page =0; /* si es numero negativo, regresar a la pagina 0 */
            PageRequest pagination = null;
            switch (sortType) {
                case NONE -> pagination = PageRequest.of(page, size); 
                case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); //organizar de forma ascendente por titulo de lesson
                case DESC -> pagination  = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).descending()); //organizar de forma descendente por titulo de lesson
            }

            return this.messageRepository.findAll(pagination).map(this::messageEntityToResponse); //se nesesita devolver un response de la entidad 
        }

        @Override
        public MessageResp getById(Long id) {
            return this.messageEntityToResponse(this.findMessage(id)); //se busca el mensaje y se construye la entidad mensaje del response
        }

        @Override
        public MessageResp update(MessageReq request, Long id) {
            Message message = this.findMessage(id); //se encuentra mensaje por id para actualizar

            Message messageUpdate = this.MessageReqToEntity(request); //se crea el mensaje desde el request recibido
            messageUpdate.setMessage_id(message.getMessage_id()); //se actualiza id
            messageUpdate.setCourse(message.getCourse()); //se guarda info del curso
            messageUpdate.setSender(message.getSender()); //se guarda info del usuario transmisor
            messageUpdate.setReceiver(message.getReceiver()); //se guarda info del usuario receptor

            return this.messageEntityToResponse(this.messageRepository.save(messageUpdate)); //se actualiza mensaje y se devuelve un response
        }

        @Override
        public void delete(Long id) {
            this.messageRepository.delete(this.findMessage(id)); //se busca el mensaje por el id y se elimina del repositorio
        }
    
    /* metodos propios */

        /* entidad a response */
        private MessageResp messageEntityToResponse(Message entity){

            /* dependencias de tablas relacionadas */
            UserBasicResp sender = UserBasicResp.builder()
                                    //construccion del objeto usuario
                                    .user_id(entity.getSender().getUser_id())
                                    .user_name(entity.getSender().getUser_name())
                                    .password(entity.getSender().getPassword())
                                    .email(entity.getSender().getEmail())
                                    .full_name(entity.getSender().getFull_name())
                                    .role(entity.getSender().getRole())
                                    .build();
            
            UserBasicResp reciver = UserBasicResp.builder()
                                    //construccion del objeto usuario
                                    .user_id(entity.getReceiver().getUser_id())
                                    .user_name(entity.getReceiver().getUser_name())
                                    .password(entity.getReceiver().getPassword())
                                    .email(entity.getReceiver().getEmail())
                                    .full_name(entity.getReceiver().getFull_name())
                                    .role(entity.getReceiver().getRole())
                                    .build();
            
            CourseBasicResp course = CourseBasicResp.builder()
                                        //construccion del objeto curso
                                        .course_id(entity.getCourse().getCourse_id())
                                        .course_name(entity.getCourse().getCourse_name())
                                        .description(entity.getCourse().getDescription())
                                        .build();
            
            return MessageResp.builder()
                    //construccion del mensaje
                    .message_id(entity.getMessage_id())
                    .message_context(entity.getMessage_context())
                    .send_date(entity.getSent_date())
                    .user_sender(sender)
                    .user_receiver(reciver)
                    .course(course)
                    .build();
        }

        /* request a entidad */
        private Message MessageReqToEntity(MessageReq request){
            return Message.builder()
                    .message_context(request.getMessage_context())
                    .sent_date(request.getSent_date())
                    .course(findCourse(request.getId_course()))
                    .sender(findUser(request.getId_sender()))
                    .receiver(findUser(request.getId_receiver()))
                    .build();

        }

        /* buscadores */
        //buscar mensajes
        private Message findMessage(Long id){
            return this.messageRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("Message")));
        }

        //buscador de cursos
        private Course findCourse(Long id){
            return this.courseRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("Course")));
        }
        
        //buscador de usuarios
        private User findUser(Long id){ //funcion para buscar usuario
            return this.userRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("User")));
        }
}
