package com.bank.profile.service;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.impl.PassportServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName(value = "Тест сервиса PassportServiceImpl ")
@ExtendWith(MockitoExtension.class)
public class PassportServiceImplTest {

    @Mock
    private PassportRepository repository;

    @Mock
    private PassportMapper mapper;

    @InjectMocks
    private PassportServiceImp service;

    private final PassportEntity userRob = new PassportEntity();

    {
        userRob.setId(1L);
        userRob.setFirstName("Rob");
    }

    private final PassportEntity userAlice = new PassportEntity();

    {
        userAlice.setId(2L);
        userAlice.setFirstName("Alice");
    }

    private final PassportDto userRobDto = new PassportDto();

    {
        userRobDto.setId(1L);
        userRobDto.setFirstName("Rob");
    }

    private final PassportDto userAliceDto = new PassportDto();

    {
        userAliceDto.setId(2L);
        userAliceDto.setFirstName("Alice");
    }

    private final Long id = 1L;

    @Test
    @DisplayName(value = "поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {

        when(repository.findById(id)).thenReturn(Optional.of(userRob));
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        PassportDto actualDto = service.findById(id);

        assertEquals(userRobDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(userRob);
    }

    @Test
    @DisplayName(value = "поиск по несуществующему id, негативный сценарий")
    public void findByNonExistIdNegativeTest() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(id));

        assertEquals("passport с данным id не найден!", exception.getMessage());
    }

    @Test
    @DisplayName(value = "добавление новой записи, позитивный сценарий")
    public void savePositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        PassportDto savedPassportDto = service.save(userRobDto);

        assertEquals(userRobDto, savedPassportDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }

    @Test
    @DisplayName(value = "добавление новой записи, негативный сценарий")
    public void saveFailsNegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(null);

        PassportDto savedActualAccountDto = service.save(userRobDto);

        assertNull(savedActualAccountDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
    }

    @Test
    @DisplayName(value = "обновление записи, позитивный сценарий")
    public void updatePositiveTest() {

        when(repository.findById(id)).thenReturn(Optional.of(userAlice));
        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        PassportDto updatedPassportDto = service.update(id, userRobDto);

        assertEquals(userRobDto, updatedPassportDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(userRobDto, userAlice);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }

    @Test
    @DisplayName(value = "обновление записи, негативный сценарий")
    public void updateByNonExistIdNegativeTest() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(id, userAliceDto));

        assertEquals("Обновление невозможно, passport не найден!", exception.getMessage());
    }

    @Test
    @DisplayName(value = "получение всех записей, позитивный сценарий")
    public void findAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<PassportEntity> passportEntities = Arrays.asList(userRob, userAlice);

        List<PassportDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(repository.findAllById(ids)).thenReturn(passportEntities);
        when(mapper.toDtoList(passportEntities)).thenReturn(expectedDtoList);

        List<PassportDto> actualDtoList = service.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(passportEntities);
    }

    @Test
    @DisplayName(value = "получение всех записей, негативный сценарий")
    public void findAllByIdNonExistIdNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(Collections.emptyList());

        List<PassportDto> actualDtoList = service.findAllById(ids);

        assertTrue(actualDtoList.isEmpty());
        verify(repository, times(1)).findAllById(ids);
    }
}
