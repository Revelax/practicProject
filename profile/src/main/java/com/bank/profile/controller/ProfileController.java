package com.bank.profile.controller;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.service.ProfileService;
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
 * Контроллер для {@link ProfileEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(
        name = "Профиль",
        description = "Контроллер для управления профилем"
)
public class ProfileController {

    private final ProfileService service;

    /**
     * @param id технический идентификатор {@link ProfileEntity}
     * @return {@link ResponseEntity<ProfileDto>}
     */
    @Operation(
            summary = "Чтение по ID",
            description = "Чтение данных профиля аккаунта по ID из БД"
    )
    @GetMapping("/read/{id}")
    public ResponseEntity<ProfileDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param profile {@link ProfileDto}
     * @return {@link ResponseEntity<ProfileDto>}
     */
    @Operation(
            summary = "Добавление записи",
            description = "Добавление нового профиля аккаунта в БД"
    )
    @PostMapping("/create")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profile) {
        return ResponseEntity.ok(service.save(profile));
    }

    /**
     * @param profile {@link ProfileDto}
     * @param id      технический идентификатор {@link ProfileEntity}
     * @return {@link ResponseEntity<ProfileDto>}
     */
    @Operation(
            summary = "Обновление записи",
            description = "Обновление данных профиля аккаунта в БД"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileDto> update(@PathVariable Long id, @RequestBody ProfileDto profile) {
        return ResponseEntity.ok(service.update(id, profile));
    }

    /**
     * @param ids лист технических идентификаторов {@link ProfileEntity}
     * @return {@link ResponseEntity} {@link List<ProfileDto>}
     */
    @Operation(
            summary = "Чтение записей по списку id",
            description = "Чтение данных профиля аккаунтов в БД, по списку id"
    )
    @GetMapping("read/all")
    public ResponseEntity<List<ProfileDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
