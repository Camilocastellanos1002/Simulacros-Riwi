package com.riwi.Simulacrum_SpringBoot_Test.api.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.request.LessonReq;
import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.LessonResp;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.ILessonService;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

/*http://localhost:8080/api/v1/swagger-ui/index.html/ */
@RestController
@RequestMapping(path = "/lessons")
@Data
@AllArgsConstructor
public class LessonController {
    
    /*Inyeccion de dependencias */
        @Autowired
        private final ILessonService lessonService;


    /*Peticiones HTTP */
        @GetMapping
        @Operation(summary = "Obtiene las lecciones de forma paginada y organizada por el titulo")
        public ResponseEntity<Page<LessonResp>>getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestHeader(required = false)SortType sortType
        ){
            if (Objects.isNull(sortType)) {
                sortType = SortType.NONE;
            }
            return ResponseEntity.ok(this.lessonService.getAll(page-1, size, sortType));
        }

        @PostMapping
        @Operation(summary = "Crea la leccion")
        @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        public ResponseEntity<LessonResp> create(
                @Validated LessonReq request) {
            return ResponseEntity.ok(this.lessonService.create(request));
        }

        @PutMapping(path = "/{id}")
        @Operation(summary = "Actualiza la leccion por id")
        @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        public ResponseEntity<LessonResp> update(
                @PathVariable Long id, @Validated @RequestBody LessonReq request) {
            return ResponseEntity.ok(this.lessonService.update(request, id));
        }

        @DeleteMapping(path = "/{id}")
        @Operation(summary = "Elimina la leccion por id")
        @ApiResponse(
            responseCode = "400", description = "Cuando el id no es valido", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)) })
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            this.lessonService.delete(id);

            return ResponseEntity.noContent().build();
        }

        @GetMapping(path = "/{id}")
        @Operation(summary = "Obtiene un leccion por id")
        @ApiResponse(responseCode = "400", description = "Cuando el id no es valido", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        public ResponseEntity<LessonResp> getById(@PathVariable Long id) {
            return ResponseEntity.ok(this.lessonService.getById(id));
        }
}
