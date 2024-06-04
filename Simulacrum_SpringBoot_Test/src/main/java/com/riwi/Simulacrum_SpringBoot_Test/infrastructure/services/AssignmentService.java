package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.services;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.AssignmentReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.AssignmentResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.LessonBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.SubmissionAssingmentResp;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Assignment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Lesson;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Submission;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.AssignmentRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.LessonRepository;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.IAssignmentService;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;
import com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException;
import com.riwi.Simulacrum_SpringBoot_Test.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class AssignmentService implements IAssignmentService{

    /*inyeccion de dependencias */
        @Autowired
        private final AssignmentRepository assingmentRepository; /* constante privada del repositorio de assignment */

        @Autowired
        private final LessonRepository lessonRepository;

    /* metodos CRUD */
        @Override
        public AssignmentResp create(AssignmentReq request) {
            Assignment assignment = this.AssignmenteReqToEntity(request); //se crea la entidad tarea desde el request

            assignment.setSubmissions(new ArrayList<>()); //se crea lista vacia de entregas
            assignment.setLesson(findLesson(request.getId_lesson())); //se ingresa leccion a partir del id ingresado en el request

            return this.AssingmentEntityToResponse(this.assingmentRepository.save(assignment)); //se regresa un response a partir de la entidad tarea y es guardada en el repositorio
        }

        @SuppressWarnings("null")
        @Override
        public Page<AssignmentResp> getAll(int page, int size, SortType sortType) {
            if (page < 0) page =0; /* si es numero negativo, regresar a la pagina 0 */
                
            PageRequest pagination = null;
            switch (sortType) {
                case NONE -> pagination = PageRequest.of(page, size); 
                case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); //organizar de forma ascendente por titulo de lesson
                case DESC -> pagination  = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).descending()); //organizar de forma descendente por titulo de lesson
            }

            return this.assingmentRepository.findAll(pagination).map(this::AssingmentEntityToResponse);  //se nesesita devolver un response de la entidad 
        }

        @Override
        public AssignmentResp getById(Long id) {
            return this.AssingmentEntityToResponse(this.findAssignment(id)); //se busca la entrega por id y se construye la entidad del response
        }

        @Override
        public AssignmentResp update(AssignmentReq request, Long id) {
            Assignment assignment = this.findAssignment(id); //se busca la tarea por id y se obtiene para actualizar

            Assignment assignmentUpdate = this.AssignmenteReqToEntity(request); //se crea la entidad assignment desde el request recibido
            assignmentUpdate.setAssignment_id(id); //se envia id
            assignmentUpdate.setLesson(assignment.getLesson()); //se guarda las leccion de la tarea encontrada
            assignmentUpdate.setSubmissions(assignment.getSubmissions()); //se guarda las entregas de la tarea encontrada

            return this.AssingmentEntityToResponse(this.assingmentRepository.save(assignmentUpdate)); //se guarda la tarea actualizada y se retorna entidad tareas
        }

        @Override
        public void delete(Long id) {
            this.assingmentRepository.delete(this.findAssignment(id)); //se busca la tarea con el id ingresado y se elimina del repositorio
        }
    
    /* Metodos propios */

        /* Entidad a response */
        private AssignmentResp AssingmentEntityToResponse(Assignment entity){       //funcion para convertir la entidad assignment a tipo response

            /* se nesesita inyectar los valores de las otras tablas relacionadas */
            LessonBasicResp lesson = LessonBasicResp.builder() 
                                        //construccion del objeto lesson basico
                                        .lesson_id(entity.getLesson().getLesson_id())
                                        .lesson_title(entity.getLesson().getLesson_title())
                                        .context(entity.getLesson().getContext())
                                        .build();

            /* inyecccion de valores de la lista de entregas */
            List<SubmissionAssingmentResp> submissionList =
                entity.getSubmissions() //lista de objetos tipo entregas
                .stream() //conversion a coleccion
                .map(this::submissionEntityToResponse) //se necesita mapear la lista de entregas de la tarea
                .collect(Collectors.toList());//conversion de la coleccion a lista
            

            return  AssignmentResp.builder()//construccion de lesson
                        .assignment_id(entity.getAssignment_id())
                        .assignment_title(entity.getAssignment_title())
                        .description(entity.getDescription())
                        .due_date(entity.getDue_date())
                        .lesson(lesson)
                        .submissions(submissionList)
                        .build();
        }

        /*Convertir entidad de entregas en response */
        private SubmissionAssingmentResp submissionEntityToResponse(Submission entity){
            return SubmissionAssingmentResp.builder() //creamos la entrega
                    .submission_id(entity.getSubmission_id())
                    .context(entity.getContext())
                    .submission_date(entity.getSubmission_date())
                    .grade(entity.getGrade())
                    .build();
        }

        /* Request a entity */
        private Assignment AssignmenteReqToEntity(AssignmentReq request){
            return Assignment.builder() //construimos objeto assignment a partir del request
                    .assignment_id(request.getId_lesson())
                    .assignment_title(request.getAssignment_title())
                    .description(request.getDescription())
                    .due_date(request.getDue_date())
                    .lesson(findLesson(request.getId_lesson()))
                    .build();
        }

        /*funcion para buscar lesson o generar exception*/
        private Lesson findLesson(Long id){
            return this.lessonRepository.findById(id).orElseThrow(()-> new com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException(ErrorMessage.idNotFound("Lesson")));
        }

        /*funcion para buscar el assigment o generar exception */
        private Assignment findAssignment(Long id){
            return this.assingmentRepository.findById(id).orElseThrow(()->new BadRequestException(ErrorMessage.idNotFound("Assignment")));
        }







}
