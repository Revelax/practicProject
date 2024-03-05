package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.impl.ActualRegistrationServiceImp;
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

@DisplayName(value = "Тест сервиса ActualRegistrationService ")
@ExtendWith(MockitoExtension.class)
public class ActualRegistrationServiceTest {
    @Mock
    ActualRegistrationRepository repository;
    @Mock
    ActualRegistrationMapper mapper;
    @InjectMocks
    private ActualRegistrationServiceImp service;

    private final ActualRegistrationEntity userRob = new ActualRegistrationEntity();

    {
        userRob.setId(1L);
    }

    private final ActualRegistrationEntity userAlice = new ActualRegistrationEntity();

    {
        userAlice.setId(2L);
    }

    private final ActualRegistrationDto userRobDto = new ActualRegistrationDto();

    {
        userRobDto.setId(1L);
    }

    private final ActualRegistrationDto userAliceDto = new ActualRegistrationDto();

    {
        userAliceDto.setId(2L);
    }

    private final Long id = 1L;
    @DisplayName(value = "поиск по id, позитивный сценарий")
    @Test
    public void findById_ReturnsActualRegistrationDto_PositiveTest() {

        when(repository.findById(id)).thenReturn(Optional.of(userRob));
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        ActualRegistrationDto actualDto = service.findById(id);

        assertEquals(userRobDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(userRob);
    }

    @DisplayName(value = "поиск по несуществующему id, негативный сценарий")
    @Test
    public void findById_ReturnsEntityNotFoundException_NegativeTest() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(id));

        assertEquals("actualRegistration с данным id не найден!", exception.getMessage());
    }
    @DisplayName(value = "добавление новой записи, позитивный сценарий")
    @Test
    public void save_ReturnsSavedActualRegistrationDto_PositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        ActualRegistrationDto savedActualRegistrationDto = service.save(userRobDto);

        assertEquals(userRobDto, savedActualRegistrationDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }
    @DisplayName(value = "добавление новой записи, негативный сценарий")
    @Test
    public void save_ReturnsNullWhenSaveFails_NegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(null);

        ActualRegistrationDto savedActualAccountDto = service.save(userRobDto);

        assertNull(savedActualAccountDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
    }
    @DisplayName(value = "обновление записи, позитивный сценарий")
    @Test
    public void update_ReturnsUpdatedActualRegistrationDto_PositiveTest() {

        when(repository.findById(id)).thenReturn(Optional.of(userAlice));
        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        ActualRegistrationDto updatedActualRegistrationDto = service.update(id, userRobDto);

        assertEquals(userRobDto, updatedActualRegistrationDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(userRobDto, userAlice);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }
    @DisplayName(value = "обновление записи, негативный сценарий")
    @Test
    public void update_ReturnsEntityNotFoundException_NegativeTest() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(id, userAliceDto));

        assertEquals("Обновление невозможно, ActualRegistration не найден!", exception.getMessage());
    }
    @DisplayName(value = "получение всех записей, позитивный сценарий")
    @Test
    public void findAllById_ReturnsActualDtoList_PositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<ActualRegistrationEntity> passportEntities = Arrays.asList(userRob, userAlice);

        List<ActualRegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(repository.findAllById(ids)).thenReturn(passportEntities);
        when(mapper.toDtoList(passportEntities)).thenReturn(expectedDtoList);

        List<ActualRegistrationDto> actualDtoList = service.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(passportEntities);
    }
    @DisplayName(value = "получение всех записей, негативный сценарий")
    @Test
    public void findAllById_ReturnsEmptyListWhenReadFails_NegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(Collections.emptyList());

        List<ActualRegistrationDto> actualDtoList = service.findAllById(ids);

        assertTrue(actualDtoList.isEmpty());
        verify(repository, times(1)).findAllById(ids);

    }
}
