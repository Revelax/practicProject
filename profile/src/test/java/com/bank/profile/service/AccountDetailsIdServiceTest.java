package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;
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

@DisplayName(value = "Тест сервиса AccountDetailsIdService ")
@ExtendWith(MockitoExtension.class)
public class AccountDetailsIdServiceTest {
    @Mock
    AccountDetailsIdRepository repository;
    @Mock
    AccountDetailsIdMapper mapper;
    @InjectMocks
    private AccountDetailsIdServiceImp service;

    private final AccountDetailsIdEntity userRob = new AccountDetailsIdEntity();

    {
        userRob.setId(1L);
    }

    private final AccountDetailsIdEntity userAlice = new AccountDetailsIdEntity();

    {
        userAlice.setId(2L);
    }

    private final AccountDetailsIdDto userRobDto = new AccountDetailsIdDto();

    {
        userRobDto.setId(1L);
    }

    private final AccountDetailsIdDto userAliceDto = new AccountDetailsIdDto();

    {
        userAliceDto.setId(2L);
    }

    private final Long id = 1L;
    @DisplayName(value = "поиск по id, позитивный сценарий")
    @Test
    public void findById_ReturnsAccountDetailsIdDto_PositiveTest() {

        when(repository.findById(id)).thenReturn(Optional.of(userRob));
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AccountDetailsIdDto actualDto = service.findById(id);

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

        assertEquals("accountDetailsId с данным id не найден!", exception.getMessage());
    }
    @DisplayName(value = "добавление новой записи, позитивный сценарий")
    @Test
    public void save_ReturnsSavedActualAccountDto_PositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AccountDetailsIdDto savedActualAccountDto = service.save(userRobDto);

        assertEquals(userRobDto, savedActualAccountDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }
    @DisplayName(value = "добавление новой записи, негативный сценарий")
    @Test
    public void save_ReturnsNullWhenSaveFails_NegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(null);

        AccountDetailsIdDto savedActualAccountDto = service.save(userRobDto);

        assertNull(savedActualAccountDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
    }
    @DisplayName(value = "обновление записи, позитивный сценарий")
    @Test
    public void update_ReturnsUpdatedActualAccountDto_PositiveTest() {

        when(repository.findById(id)).thenReturn(Optional.of(userAlice));
        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AccountDetailsIdDto updatedActualAccountDto = service.update(id, userRobDto);

        assertEquals(userRobDto, updatedActualAccountDto);
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

        assertEquals("Обновление невозможно, accountDetailsId не найден!", exception.getMessage());
    }
    @DisplayName(value = "получение всех записей, позитивный сценарий")
    @Test
    public void findAllById_ReturnsActualDtoList_PositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<AccountDetailsIdEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<AccountDetailsIdDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(repository.findAllById(ids)).thenReturn(accountEntities);
        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<AccountDetailsIdDto> actualDtoList = service.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(accountEntities);
    }
    @DisplayName(value = "получение всех записей, негативный сценарий")
    @Test
    public void findAllById_ReturnsEmptyListWhenReadFails_NegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(Collections.emptyList());

        List<AccountDetailsIdDto> actualDtoList = service.findAllById(ids);

        assertTrue(actualDtoList.isEmpty());
        verify(repository, times(1)).findAllById(ids);

    }
}
