package com.bank.test.profile.service;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountDetailsIdServiceTest {
    @Mock
    AccountDetailsIdRepository repository;
    @Mock
    AccountDetailsIdMapper mapper;
    @InjectMocks
    private AccountDetailsIdServiceImp accountDetailsIdService;

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

    @Test
    public void testFindById() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(userRob));
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AccountDetailsIdDto actualDto = accountDetailsIdService.findById(id);

        assertEquals(userRobDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(userRob);
    }

    @Test
    public void testFindById_EntityNotFoundException() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> accountDetailsIdService.findById(id));

        assertEquals("accountDetailsId с данным id не найден!", exception.getMessage());
    }

    @Test
    public void testSave() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AccountDetailsIdDto savedActualAccountDto = accountDetailsIdService.save(userRobDto);

        assertEquals(userRobDto, savedActualAccountDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }

    @Test
    public void testUpdate() {

        when(repository.findById(id)).thenReturn(Optional.of(userAlice));
        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AccountDetailsIdDto updatedActualAccountDto = accountDetailsIdService.update(id, userRobDto);

        assertEquals(userRobDto, updatedActualAccountDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(userRobDto, userAlice);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }

    @Test
    public void testUpdate_EntityNotFoundException() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> accountDetailsIdService.update(id, userAliceDto));

        assertEquals("Обновление невозможно, accountDetailsId не найден!", exception.getMessage());
    }

    @Test
    public void testFindAllById() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<AccountDetailsIdEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<AccountDetailsIdDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(repository.findAllById(ids)).thenReturn(accountEntities);
        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<AccountDetailsIdDto> actualDtoList = accountDetailsIdService.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(accountEntities);
    }

}
