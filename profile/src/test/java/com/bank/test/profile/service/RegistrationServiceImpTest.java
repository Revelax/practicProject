package com.bank.test.profile.service;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.impl.RegistrationServiceImp;
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
public class RegistrationServiceImpTest {
    @Mock
    private RegistrationRepository repository;

    @Mock
    private RegistrationMapper mapper;

    @InjectMocks
    private RegistrationServiceImp registrationService;

    private final RegistrationEntity userRobReg = new RegistrationEntity();
    {
        userRobReg.setId(1L);
        userRobReg.setIndex(124L);
    }
    private final RegistrationEntity userAliceReg = new RegistrationEntity();
    {
        userAliceReg.setId(2L);
        userAliceReg.setIndex(123L);
    }
    private final RegistrationDto userRobRegDto = new RegistrationDto();
    {
        userRobReg.setId(1L);
        userRobReg.setIndex(124L);
    }
    private final RegistrationDto userAliceRegDto = new RegistrationDto();
    {
        userAliceReg.setId(2L);
        userAliceReg.setIndex(123L);
    }
    private final Long id = 1L;

    @Test
    public void testFindById() {

        RegistrationEntity registrationEntity = userRobReg;
        RegistrationDto expectedDto = userRobRegDto;

        when(repository.findById(id)).thenReturn(Optional.of(registrationEntity));
        when(mapper.toDto(registrationEntity)).thenReturn(expectedDto);

        RegistrationDto actualDto = registrationService.findById(id);

        assertEquals(expectedDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(registrationEntity);
    }

    @Test
    public void testFindById_EntityNotFoundException() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.findById(id));

        assertEquals("registration с данным id не найден!", exception.getMessage());
    }

    @Test
    public void testSave() {

        when(mapper.toEntity(userRobRegDto)).thenReturn(userRobReg);
        when(repository.save(userRobReg)).thenReturn(userRobReg);
        when(mapper.toDto(userRobReg)).thenReturn(userRobRegDto);

        RegistrationDto savedDto = registrationService.save(userRobRegDto);

        assertEquals(userRobRegDto, savedDto);
        verify(mapper, times(1)).toEntity(userRobRegDto);
        verify(repository, times(1)).save(userRobReg);
        verify(mapper, times(1)).toDto(userRobReg);
    }

    @Test
    public void testUpdate() {

        RegistrationDto registrationDto = userRobRegDto;
        RegistrationEntity registrationEntityById = userAliceReg;
        RegistrationEntity updatedRegistrationEntity = userRobReg;

        when(repository.findById(id)).thenReturn(Optional.of(registrationEntityById));
        when(mapper.mergeToEntity(registrationDto, registrationEntityById)).thenReturn(updatedRegistrationEntity);
        when(repository.save(updatedRegistrationEntity)).thenReturn(updatedRegistrationEntity);
        when(mapper.toDto(updatedRegistrationEntity)).thenReturn(registrationDto);

        RegistrationDto updatedRegDto = registrationService.update(id, registrationDto);

        assertEquals(registrationDto, updatedRegDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(registrationDto, registrationEntityById);
        verify(repository, times(1)).save(updatedRegistrationEntity);
        verify(mapper, times(1)).toDto(updatedRegistrationEntity);
    }

    @Test
    public void testUpdate_EntityNotFoundException() {
        RegistrationDto registrationDtoDto = userAliceRegDto;

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.update(id, registrationDtoDto));

        assertEquals("Обновление невозможно, registration не найден!", exception.getMessage());
    }

    @Test
    public void testFindAllById() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<RegistrationEntity> registrationEntities = Arrays.asList(userRobReg, userAliceReg);

        List<RegistrationDto> expectedDtoList = Arrays.asList(userRobRegDto, userAliceRegDto);

        when(repository.findAllById(ids)).thenReturn(registrationEntities);
        when(mapper.toDtoList(registrationEntities)).thenReturn(expectedDtoList);

        List<RegistrationDto> actualDtoList = registrationService.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(registrationEntities);
    }
}
