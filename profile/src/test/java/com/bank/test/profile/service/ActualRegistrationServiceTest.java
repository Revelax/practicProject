package com.bank.test.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.impl.ActualRegistrationServiceImp;
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
public class ActualRegistrationServiceTest {
    @Mock
    ActualRegistrationRepository repository;
    @Mock
    ActualRegistrationMapper mapper;
    @InjectMocks
    private ActualRegistrationServiceImp actualRegistrationService;

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

    @Test
    public void testFindById() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(userRob));
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        ActualRegistrationDto actualDto = actualRegistrationService.findById(id);

        assertEquals(userRobDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(userRob);
    }


    @Test
    public void testFindById_EntityNotFoundException() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> actualRegistrationService.findById(id));

        assertEquals("actualRegistration с данным id не найден!", exception.getMessage());
    }

    @Test
    public void testSave() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(userRob);
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        ActualRegistrationDto savedActualRegistrationDto = actualRegistrationService.save(userRobDto);

        assertEquals(userRobDto, savedActualRegistrationDto);
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

        ActualRegistrationDto updatedActualRegistrationDto = actualRegistrationService.update(id, userRobDto);

        assertEquals(userRobDto, updatedActualRegistrationDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(userRobDto, userAlice);
        verify(repository, times(1)).save(userRob);
        verify(mapper, times(1)).toDto(userRob);
    }

    @Test
    public void testUpdate_EntityNotFoundException() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> actualRegistrationService.update(id, userAliceDto));

        assertEquals("Обновление невозможно, ActualRegistration не найден!", exception.getMessage());
    }

    @Test
    public void testFindAllById() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<ActualRegistrationEntity> passportEntities = Arrays.asList(userRob, userAlice);

        List<ActualRegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(repository.findAllById(ids)).thenReturn(passportEntities);
        when(mapper.toDtoList(passportEntities)).thenReturn(expectedDtoList);

        List<ActualRegistrationDto> actualDtoList = actualRegistrationService.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(passportEntities);
    }
}
