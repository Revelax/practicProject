package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.service.ActualRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для {@link ActualRegistrationEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/actual/registration")
@Tag(
        name = "Актуальная регистрация",
        description = "Контроллер для управления актуальной регистрацией"
)
public class ActualRegistrationController {

    private final ActualRegistrationService service;

    /**
     * @param id технический идентификатор {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @Operation(
            summary = "Чтение по ID",
            description = "Чтение актуальных данных регистрации по ID из БД"
    )
    @GetMapping("/read/{id}")
    public ResponseEntity<ActualRegistrationDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param actualRegistration {@link ActualRegistrationDto}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @Operation(
            summary = "Добавление записи",
            description = "Добавление актуальных данных регистрации аккаунта в БД"
    )
    @PostMapping("/create")
    public ResponseEntity<ActualRegistrationDto> create(@RequestBody ActualRegistrationDto actualRegistration) {
        return ResponseEntity.ok(service.save(actualRegistration));
    }

    /**
     * @param actualRegistration {@link ActualRegistrationDto}
     * @param id                 технический идентификатор {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @Operation(
            summary = "Обновление записи",
            description = "Обновление актуальных данных регистрации аккаунта в БД"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<ActualRegistrationDto> update(@PathVariable Long id,
                                                        @RequestBody ActualRegistrationDto actualRegistration) {
        return ResponseEntity.ok(service.update(id, actualRegistration));
    }

    /**
     * @param ids лист технических идентификаторов {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity} {@link List<ActualRegistrationDto>}
     */
    @Operation(
            summary = "Чтение записей по списку id",
            description = "Чтение актуальных данных регистрации аккаунтов в БД, по списку id"
    )
    @GetMapping("read/all")
    public ResponseEntity<List<ActualRegistrationDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
