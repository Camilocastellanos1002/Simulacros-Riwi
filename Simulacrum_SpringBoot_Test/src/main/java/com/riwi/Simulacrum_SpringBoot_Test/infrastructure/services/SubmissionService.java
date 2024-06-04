package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.SubmissionReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.AssignmentBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.LessonBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.SubmissionResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.UserBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Assignment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Submission;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.User;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.AssignmentRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.SubmissionRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.UserRepository;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.ISubmissionService;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;
import com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException;
import com.riwi.Simulacrum_SpringBoot_Test.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class SubmissionService implements ISubmissionService {

    /*indexacion de dependencias */
        @Autowired
        private final SubmissionRepository submissionRepository;

        @Autowired
        private final UserRepository userRepository;

        @Autowired
        private final AssignmentRepository assignmentRepository;
    

    /* Metodos CRUD */
        @Override
        public SubmissionResp create(SubmissionReq request) {
            Submission submission = this.SubmissionReqToEntity(request); //creacion de la entidad submision desde el request

            submission.setAssignment(findAssignment(request.getId_assignment())); //encontrar curso apartir del id ingresando del request
            submission.setUser(findUser(request.getId_user())); //encontrar usuario a partir del id ingresado del request

            return this.submissionEntityToResponse(this.submissionRepository.save(submission)); //se regresa un response a partir de la entidad submission y guardarla en el repositorio

        }

        @SuppressWarnings("null")
        @Override
        public Page<SubmissionResp> getAll(int page, int size, SortType sortType) {
            if (page < 0) page =0; /* si es numero negativo, regresar a la pagina 0 */
                
            PageRequest pagination = null;
            switch (sortType) {
                case NONE -> pagination = PageRequest.of(page, size); 
                case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); //organizar de forma ascendente por titulo de lesson
                case DESC -> pagination  = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).descending()); //organizar de forma descendente por titulo de lesson
            }

            return this.submissionRepository.findAll(pagination).map(this::submissionEntityToResponse); //se nesesita devolver un response de la entidad 
        }

        @Override
        public SubmissionResp getById(Long id) {
            return this.submissionEntityToResponse(this.findSubmission(id)); //se busca la entrega y se construye entidad del response
        }

        @Override
        public SubmissionResp update(SubmissionReq request, Long id) {
           Submission submission = this.findSubmission(id); //busca la entrega por id para actualizar

           Submission submissionUpadte = this.SubmissionReqToEntity(request); //se crea entidad entrega desde el request recibido
           submissionUpadte.setSubmission_id(id); //obtiene id
           submissionUpadte.setAssignment(submission.getAssignment());
           submissionUpadte.setUser(submission.getUser());

           return this.submissionEntityToResponse(this.submissionRepository.save(submissionUpadte));

        }

        @Override
        public void delete(Long id) {
            this.submissionRepository.delete(this.findSubmission(id)); //se busca la entrega con el id ingresado y se elimina del repositorio
        }

    /* metodos propios  */
        
        /* entidad a response */
        private SubmissionResp submissionEntityToResponse(Submission entity){    //funcion para convertir la entidad submision a tipo response

            /* se nesesita inyectar los valores de las otras tablas relacionadas */
            UserBasicResp user = UserBasicResp.builder() //construccion del objeto usuario
                                    .user_id(entity.getUser().getUser_id())
                                    .user_name(entity.getUser().getUser_name())
                                    .email(entity.getUser().getEmail())
                                    .full_name(entity.getUser().getFull_name())
                                    .role(entity.getUser().getRole())
                                    .password(entity.getUser().getPassword())
                                    .build();

            /*inyeccion indirecta de la relacion con lesson que tiene por medio del assignment */
            LessonBasicResp lesson = LessonBasicResp.builder() //construccion del objeto leccion
                                        .lesson_id(entity.getAssignment().getLesson().getLesson_id())
                                        .lesson_title(entity.getAssignment().getLesson().getLesson_title())
                                        .context(entity.getAssignment().getLesson().getContext())
                                        .build();

            
            /*inyeccion de relacion de tabla assignment */
            AssignmentBasicResp assignment = AssignmentBasicResp.builder() //contruccion del objeto tarea
                                    .assignment_id(entity.getAssignment().getAssignment_id())
                                    .assignment_title(entity.getAssignment().getAssignment_title())
                                    .description(entity.getAssignment().getDescription())
                                    .due_date(entity.getAssignment().getDue_date())
                                    .lesson(lesson)
                                    .build();
            
            return SubmissionResp.builder() //construccion de submission
                    .submission_id(entity.getSubmission_id())
                    .context(entity.getContext())
                    .submission_date(entity.getSubmission_date())
                    .grade(entity.getGrade())
                    .user(user)
                    .assignment(assignment)
                    .build();
        }
        /*Request to entity */
        private Submission SubmissionReqToEntity(SubmissionReq request){
            return Submission.builder() //construimos objeto subbmision a partir del request
                    .context(request.getContext())
                    .submission_date(request.getSubmission_date())
                    .user(findUser(request.getId_user()))
                    .assignment(findAssignment(request.getId_assignment()))
                    .build();
        }
    
        /* funcion buscar user*/
        private User findUser(Long id){
            return this.userRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("User")));
        }

        /* funcion buscar assingment*/
        private Assignment findAssignment(Long id){
            return this.assignmentRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("Assignment")));
        }

        /* funcion buscar Submission*/
        private Submission findSubmission(Long id){
            return this.submissionRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("Submission")));
        }    
}
