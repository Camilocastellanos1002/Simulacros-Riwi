package com.riwi.Simulacrum_SpringBoot_Test.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.EnrollmentReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.CourseBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.EnrollmentResp;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.UserBasicResp;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Course;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.Enrollment;
import com.riwi.Simulacrum_SpringBoot_Test.domain.entities.User;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.CourseRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.EnrollmentRepository;
import com.riwi.Simulacrum_SpringBoot_Test.domain.repositories.UserRepository;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.IEnrollmentService;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;
import com.riwi.Simulacrum_SpringBoot_Test.util.exceptions.BadRequestException;
import com.riwi.Simulacrum_SpringBoot_Test.util.messages.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class EnrollmentService implements IEnrollmentService{

    /* dependencias */
        @Autowired
        private final EnrollmentRepository enrollmentRepository;
        @Autowired
        private final CourseRepository courseRepository;
        @Autowired
        private final UserRepository userRepository;

    /* Metodos CRUD */
        @Override
        public EnrollmentResp create(EnrollmentReq request) {
            Enrollment enrollment = this.enrollmentReqToEntity(request); //creacion de la entidad registro desde el request

            enrollment.setUser(findUser(request.getId_user())); //encontrar usuario a partir del request
            enrollment.setCourse(findCourse(request.getId_course())); //encontrar curso por el id a partir del request

            return this.enrollmentEntityToResponse(this.enrollmentRepository.save(enrollment)); //se regresa un response a partir de la entidad registro y se guarda en el repositorio
        }

        @SuppressWarnings("null")
        @Override
        public Page<EnrollmentResp> getAll(int page, int size, SortType sortType) {
              if (page < 0) page =0; /* si es numero negativo, regresar a la pagina 0 */
                
            PageRequest pagination = null;
            switch (sortType) {
                case NONE -> pagination = PageRequest.of(page, size); 
                case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending()); //organizar de forma ascendente por titulo de lesson
                case DESC -> pagination  = PageRequest.of(page, size,Sort.by(FIELD_BY_SORT).descending()); //organizar de forma descendente por titulo de lessonenrollmentEntityToResponse
            }

            return this.enrollmentRepository.findAll(pagination).map(this::enrollmentEntityToResponse); //se nesesita devolver un response de la entidad 
        }

        @Override
        public EnrollmentResp getById(Long id) {
            return this.enrollmentEntityToResponse(this.findEnrollment(id)); //busqueda de la inscripcion por id y se construye entidad registro a partir del response generado
        }

        @Override
        public EnrollmentResp update(EnrollmentReq request, Long id) {
           Enrollment enrollment = this.findEnrollment(id); //se busca registro por id
           Enrollment enrollmentUpdate = this.enrollmentReqToEntity(request); //se crea entidad del request
           enrollmentUpdate.setEnrollment_id(id);
           enrollmentUpdate.setUser(enrollment.getUser());
           enrollmentUpdate.setCourse(enrollment.getCourse());

           return this.enrollmentEntityToResponse(this.enrollmentRepository.save(enrollmentUpdate)); //registro ACTUALIZADO y guardado
        }

        @Override
        public void delete(Long id) {
            this.enrollmentRepository.delete(this.findEnrollment(id)); //busqueda de la inscripcion por id y eliminacion del repositorio
        }
    
    /*Metodos propios */

        /* Entidad a response */
        private EnrollmentResp enrollmentEntityToResponse(Enrollment entity){
            /* tablas relacionadas */
            UserBasicResp user = UserBasicResp.builder()
                                        //construccion de objeto usuario
                                        .user_id(entity.getUser().getUser_id())
                                        .user_name(entity.getUser().getUser_name())
                                        .password(entity.getUser().getPassword())
                                        .email(entity.getUser().getEmail())
                                        .full_name(entity.getUser().getFull_name())
                                        .role(entity.getUser().getRole())
                                        .build();

            CourseBasicResp course =  CourseBasicResp.builder()
                                        //construccion del curso
                                        .course_id(entity.getCourse().getCourse_id())
                                        .course_name(entity.getCourse().getCourse_name())
                                        .description(entity.getCourse().getDescription())
                                        .build();
            
            return EnrollmentResp.builder()
                //construccion de la inscripcion
                .enrollment_id(entity.getEnrollment_id())
                .enrollment_date(entity.getEnrollment_date())
                .user(user)
                .course(course)
                .build();
        }

        /* Request a entidad */
        private Enrollment enrollmentReqToEntity(EnrollmentReq request){
            return Enrollment.builder()
                    .enrollment_date(request.getEnrollment_date())
                    .user(findUser(request.getId_user()))
                    .course(findCourse(request.getId_course()))
                    .build();
        }

        /* buscadores */
            private User findUser(Long id){ //funcion para buscar usuario
                return this.userRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("User")));
            }

            private Course findCourse(Long id){ //funcion para buscar curso
                return this.courseRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("Course")));
            }
            private Enrollment findEnrollment(Long id){ //funcion para buscar usuario
                return this.enrollmentRepository.findById(id).orElseThrow(()-> new BadRequestException(ErrorMessage.idNotFound("Enrollment")));
            }
}
