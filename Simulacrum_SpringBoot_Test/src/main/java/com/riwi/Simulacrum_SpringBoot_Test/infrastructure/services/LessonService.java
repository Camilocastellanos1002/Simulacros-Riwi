package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.LessonReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.AssignmentLessonResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.CourseBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.LessonResp;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Assignment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Course;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Lesson;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.CourseRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.LessonRepository;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.ILessonService;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;
import com.riwi.Simulacrum_SpringBoot_Test.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class LessonService implements ILessonService{

    /*inyeccion de dependencias */
    @Autowired
    private final LessonRepository repository; /* constante privada del repositorio de lesson */

    @Autowired
    private final CourseRepository courseRepository; /* constante privada del repositorio de course */


    /* metodos CRUD */

        @Override
        public LessonResp create(LessonReq request) {
            Lesson lesson = this.lessonReqToEntity(request); //creacion de la entidad lesson desde el request

            lesson.setAssignments(new ArrayList<>()); //crear lista vacia de tareas
            lesson.setCourse(findCourse(request.getId_course())); //encontrar curso apartir del id ingresado del request

            return this.lessonEntityToResponse(this.repository.save(lesson)); //se regresa un response apartir de la entidad lesson y guardarla en el repositorio
        }

        @SuppressWarnings("null") //???????????????????????
        @Override
        public Page<LessonResp> getAll(int page, int size, SortType sortType) {
            if (page < 0) page =0; /* si es numero negativo, regresar a la pagina 0 */
                
            PageRequest pagination = null;
            switch (sortType) {
                case NONE -> pagination = PageRequest.of(page, size); 
                case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); //organizar de forma ascendente por titulo de lesson
                case DESC -> pagination  = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).descending()); //organizar de forma descendente por titulo de lesson
            }

            return this.repository.findAll(pagination).map(this::lessonEntityToResponse); //se nesesita devolver un response de la entidad 
        }

        @Override
        public LessonResp getById(Long id) {
            return this.lessonEntityToResponse(this.findLesson(id)); //se busca la leccion y se construye entidad del response
        }

        @Override
        public LessonResp update(LessonReq request, Long id) {
            Lesson lesson = this.findLesson(id); //buscar lesson por id y obtenerlo para actualizar

            Lesson lessonUpdate = this.lessonReqToEntity(request); //se crea entidad lesson desde el request que se recibe
            lessonUpdate.setLesson_id(id); //se obtiene id
            lessonUpdate.setAssignments(lesson.getAssignments()); //se guardan tareas de la leccion encontrada
            lessonUpdate.setCourse(lesson.getCourse()); //se guarda el curso de la leccion encontrada

            return this.lessonEntityToResponse(this.repository.save(lessonUpdate)); //se guarda la leccion actualizada y se retorna la entidad leccion

        }

        @Override
        public void delete(Long id) {
            this.repository.delete(this.findLesson(id)); //se busca la leccion con el id ingresado y se elimina del repositorio
        }
    
    /*Metodos Propios */

        /* Entidad a response */
            private LessonResp lessonEntityToResponse(Lesson entity){       //funcion para convertir la entidad lesson a tipo response

                /* se nesesita inyectar los valores de las otras tablas relacionadas */
                CourseBasicResp course = CourseBasicResp.builder() 
                                            //construccion del objeto curso
                                            .course_id(entity.getCourse().getCourse_id())
                                            .course_name(entity.getCourse().getCourse_name())
                                            .description(entity.getCourse().getDescription())
                                            .build();

                /* inyecccion de valores de la lista de tareas */
                List<AssignmentLessonResp> assingmentList = 
                    entity.getAssignments() //lista de objetos tipo tareas
                    .stream() //conversion a coleccion 
                    .map(this::assignmentEntityToResponse) //se nesesita mapear la lista de tareas de la leccion
                    .collect(Collectors.toList()); //coleccion a lista

                return LessonResp.builder() //construccion de lesson
                        .lesson_id(entity.getLesson_id())
                        .lesson_title(entity.getLesson_title())
                        .context(entity.getContext())
                        .course(course)
                        .assignments(assingmentList)
                        .build();
            }

            /* Convertir entidad de tareas de leccion en response */
            private AssignmentLessonResp assignmentEntityToResponse(Assignment entity){
                return AssignmentLessonResp.builder()   //construccion de tarea
                        .assignment_id(entity.getAssignment_id())
                        .assignment_title(entity.getAssignment_title())
                        .description(entity.getDescription())
                        .due_date(entity.getDue_date())
                        .build();
            }
        
        /* Request a entity */
        private Lesson lessonReqToEntity (LessonReq request){

            return Lesson.builder() //construimos objeto lesson a partir del request
                    .lesson_title(request.getLesson_title())
                    .context(request.getContext())
                    .course(findCourse(request.getId_course())) //es nesesario buscar el curso para obtener el id
                    .build();

        }

        private Course findCourse(Long id){ //funcion para buscar curso o en caso contrario desplega una excepcion
            return this.courseRepository.findById(id).orElseThrow(()-> new com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException(ErrorMessage.idNotFound("Course")));
        }

        private Lesson findLesson(Long id){ //funcion para buscar lesson en el repositorio o desplega exception
            return this.repository.findById(id).orElseThrow(()-> new com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException(ErrorMessage.idNotFound("Lesson")));
        }
}
