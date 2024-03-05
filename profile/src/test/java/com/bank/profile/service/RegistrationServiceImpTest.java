package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.impl.RegistrationServiceImp;
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
@DisplayName(value = "Тест сервиса RegistrationServiceImp ")
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImpTest {
    @Mock
    private RegistrationRepository repository;

    @Mock
    private RegistrationMapper mapper;

    @InjectMocks
    private RegistrationServiceImp service;

    private final RegistrationEntity userRob = new RegistrationEntity();
    {
        userRob.setId(1L);
        userRob.setIndex(124L);
    }
    private final RegistrationEntity userAlice = new RegistrationEntity();
    {
        userAlice.setId(2L);
        userAlice.setIndex(123L);
    }
    private final RegistrationDto userRobDto = new RegistrationDto();
    {
        userRobDto.setId(1L);
        userRobDto.setIndex(124L);
    }
    private final RegistrationDto userAliceDto = new RegistrationDto();
    {
        userAliceDto.setId(2L);
        userAliceDto.setIndex(123L);
    }
    private final Long id = 1L;
    @DisplayName(value = "поиск по id, позитивный сценарий")
    @Test
    public void findById_ReturnsActualDto_PositiveTest() {

        RegistrationEntity registrationEntity = userRob;
        RegistrationDto expectedDto = userRobDto;

        when(repository.findById(id)).thenReturn(Optional.of(registrationEntity));
        when(mapper.toDto(registrationEntity)).thenReturn(expectedDto);

        RegistrationDto actualDto = service.findById(id);

        assertEquals(expectedDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(registrationEntity);
    }
    @DisplayName(value = "поиск по несуществующему id, негативный сценарий")
    @Test
    public void findById_ReturnsEntityNotFoundException_NegativeTest() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(id));

        assertEquals("registration с данным id не найден!", exception.getMessage());
    }
    @DisplayName(value = "добавление новой записи, позитивный сценарий")
    @Test
    public void save_ReturnsSavedDto_PositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        RegistrationDto savedDto = service.save(userRobDto);

        assertEquals(userRobDto, savedDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }
    @DisplayName(value = "добавление новой записи, негативный сценарий")
    @Test
    public void save_ReturnsNullWhenSaveFails_NegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(null);

        RegistrationDto savedActualAccountDto = service.save(userRobDto);

        assertNull(savedActualAccountDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
    }
    @DisplayName(value = "обновление записи, позитивный сценарий")
    @Test
    public void update_ReturnsUpdatedRegDto_PositiveTest() {

        RegistrationDto registrationDto = userRobDto;
        RegistrationEntity registrationEntityById = userAlice;
        RegistrationEntity updatedRegistrationEntity = userRob;

        when(repository.findById(id)).thenReturn(Optional.of(registrationEntityById));
        when(mapper.mergeToEntity(registrationDto, registrationEntityById)).thenReturn(updatedRegistrationEntity);
        when(repository.save(updatedRegistrationEntity)).thenReturn(updatedRegistrationEntity);
        when(mapper.toDto(updatedRegistrationEntity)).thenReturn(registrationDto);

        RegistrationDto updatedRegDto = service.update(id, registrationDto);

        assertEquals(registrationDto, updatedRegDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(registrationDto, registrationEntityById);
        verify(repository, times(1)).save(updatedRegistrationEntity);
        verify(mapper, times(1)).toDto(updatedRegistrationEntity);
    }
    @DisplayName(value = "обновление записи, негативный сценарий")
    @Test
    public void update_ReturnsEntityNotFoundException_NegativeTest() {
        RegistrationDto registrationDtoDto = userAliceDto;

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(id, registrationDtoDto));

        assertEquals("Обновление невозможно, registration не найден!", exception.getMessage());
    }
    @DisplayName(value = "получение всех записей, позитивный сценарий")
    @Test
    public void findAllById_ReturnsActualDtoList_PositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<RegistrationEntity> registrationEntities = Arrays.asList(userRob, userAlice);

        List<RegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(repository.findAllById(ids)).thenReturn(registrationEntities);
        when(mapper.toDtoList(registrationEntities)).thenReturn(expectedDtoList);

        List<RegistrationDto> actualDtoList = service.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(registrationEntities);
    }
    @DisplayName(value = "получение всех записей, негативный сценарий")
    @Test
    public void findAllById_ReturnsEmptyListWhenReadFails_NegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(Collections.emptyList());

        List<RegistrationDto> actualDtoList = service.findAllById(ids);

        assertTrue(actualDtoList.isEmpty());
        verify(repository, times(1)).findAllById(ids);

    }
}
