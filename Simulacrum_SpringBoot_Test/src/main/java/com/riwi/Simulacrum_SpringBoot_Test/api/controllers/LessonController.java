package com.riwi.Simulacrum_SpringBoot_Test.api.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.Simulacrum_SpringBoot_Test.api.dto.response.LessonResp;
import com.riwi.Simulacrum_SpringBoot_Test.infrastructure.abstract_services.ILessonService;
import com.riwi.Simulacrum_SpringBoot_Test.util.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping(path = "/lesson")
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
}
