package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.service.PassportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
 * Контроллер для {@link PassportEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/passport")
@Tag(
        name = "Паспорт",
        description = "Контроллер для управления паспортными данными"
)
public class PassportController {

    private final PassportService service;

    /**
     * @param id технический идентификатор {@link PassportEntity}
     * @return {@link ResponseEntity<PassportDto>}
     */
    @Operation(
            summary = "Чтение по ID",
            description = "Чтение данных паспорта аккаунта по ID из БД"
    )
    @GetMapping("/read/{id}")
    public ResponseEntity<PassportDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param passport {@link PassportDto}
     * @return {@link ResponseEntity<PassportDto>}
     */
    @Operation(
            summary = "Добавление записи",
            description = "Добавление нового паспорта аккаунта в БД"
    )
    @PostMapping("/create")
    public ResponseEntity<PassportDto> create( @Validated @RequestBody PassportDto passport) {
        return ResponseEntity.ok(service.save(passport));
    }

    /**
     * @param passport {@link PassportDto}
     * @param id       технический идентификатор {@link PassportEntity}
     * @return {@link ResponseEntity<PassportDto>}
     */
    @Operation(
            summary = "Обновление записи",
            description = "Обновление данных паспорта аккаунта в БД"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<PassportDto> update(@PathVariable Long id, @Validated @RequestBody PassportDto passport) {
        return ResponseEntity.ok(service.update(id, passport));
    }

    /**
     * @param ids лист технических идентификаторов {@link PassportDto}
     * @return {@link ResponseEntity} {@link List<PassportDto>}
     */
    @Operation(
            summary = "Чтение записей по списку id",
            description = "Чтение данных паспорта аккаунтов в БД, по списку id"
    )
    @GetMapping("read/all")
    public ResponseEntity<List<PassportDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
