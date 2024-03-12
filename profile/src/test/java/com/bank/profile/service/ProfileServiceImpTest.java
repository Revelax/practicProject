package com.bank.profile.service;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.impl.ProfileServiceImp;
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

@DisplayName(value = "Тест сервиса ProfileServiceImp ")
@ExtendWith(MockitoExtension.class)
public class ProfileServiceImpTest {

    @Mock
    private ProfileRepository repository;

    @Mock
    private ProfileMapper mapper;

    @InjectMocks
    private ProfileServiceImp service;

    private final ProfileEntity userRob = new ProfileEntity();

    {
        userRob.setId(1L);
        userRob.setInn(123L);
    }

    private final ProfileEntity userAlice = new ProfileEntity();

    {
        userAlice.setId(2L);
        userAlice.setInn(125L);
    }

    private final ProfileDto userRobDto = new ProfileDto();

    {
        userRobDto.setId(1L);
        userRobDto.setInn(123L);
    }

    private final ProfileDto userAliceDto = new ProfileDto();

    {
        userAliceDto.setId(2L);
        userAliceDto.setInn(125L);
    }

    private final Long id = 1L;

    @Test
    @DisplayName(value = "поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {

        ProfileEntity profileEntity = userRob;
        ProfileDto expectedDto = userRobDto;

        when(repository.findById(id)).thenReturn(Optional.of(profileEntity));
        when(mapper.toDto(profileEntity)).thenReturn(expectedDto);

        ProfileDto actualDto = service.findById(id);

        assertEquals(expectedDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(profileEntity);
    }

    @Test
    @DisplayName(value = "поиск по несуществующему id, негативный сценарий")
    public void findByNonExistIdNegativeTest() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(id));

        assertEquals("profile с данным id не найден!", exception.getMessage());
    }

    @Test
    @DisplayName(value = "добавление новой записи, позитивный сценарий")
    public void savePositiveTest() {
        ProfileDto profileDto = userRobDto;
        ProfileEntity profileEntity = userRob;

        when(mapper.toEntity(profileDto)).thenReturn(profileEntity);
        when(repository.save(profileEntity)).thenReturn(profileEntity);
        when(mapper.toDto(profileEntity)).thenReturn(profileDto);

        ProfileDto savedProfileDto = service.save(profileDto);

        assertEquals(profileDto, savedProfileDto);
        verify(mapper, times(1)).toEntity(profileDto);
        verify(repository, times(1)).save(profileEntity);
        verify(mapper, times(1)).toDto(profileEntity);
    }

    @Test
    @DisplayName(value = "добавление новой записи, негативный сценарий")
    public void saveFailsNegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(userRob);
        when(repository.save(userRob)).thenReturn(null);

        ProfileDto savedActualAccountDto = service.save(userRobDto);

        assertNull(savedActualAccountDto);
        verify(mapper, times(1)).toEntity(userRobDto);
        verify(repository, times(1)).save(userRob);
    }

    @Test
    @DisplayName(value = "обновление записи, позитивный сценарий")
    public void updatePositiveTest() {
        ProfileDto profileDto = userRobDto;
        ProfileEntity profileEntityById = userAlice;
        ProfileEntity updatedProfileEntity = userRob;

        when(repository.findById(id)).thenReturn(Optional.of(profileEntityById));
        when(mapper.mergeToEntity(profileDto, profileEntityById)).thenReturn(updatedProfileEntity);
        when(repository.save(updatedProfileEntity)).thenReturn(updatedProfileEntity);
        when(mapper.toDto(updatedProfileEntity)).thenReturn(profileDto);

        ProfileDto updatedProfileDto = service.update(id, profileDto);

        assertEquals(profileDto, updatedProfileDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(profileDto, profileEntityById);
        verify(repository, times(1)).save(updatedProfileEntity);
        verify(mapper, times(1)).toDto(updatedProfileEntity);
    }

    @Test
    @DisplayName(value = "обновление записи, негативный сценарий")
    public void updateByNonExistIdNegativeTest() {
        ProfileDto profileDto = userAliceDto;

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(id, profileDto));

        assertEquals("Обновление невозможно, profile не найден!", exception.getMessage());
    }

    @Test
    @DisplayName(value = "получение всех записей, позитивный сценарий")
    public void findAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<ProfileEntity> profileEntities = Arrays.asList(userRob, userAlice);

        List<ProfileDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(repository.findAllById(ids)).thenReturn(profileEntities);
        when(mapper.toDtoList(profileEntities)).thenReturn(expectedDtoList);

        List<ProfileDto> actualDtoList = service.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(profileEntities);
    }

    @Test
    @DisplayName(value = "получение всех записей, негативный сценарий")
    public void findAllByIdNonExistIdNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(Collections.emptyList());

        List<ProfileDto> actualDtoList = service.findAllById(ids);

        assertTrue(actualDtoList.isEmpty());
        verify(repository, times(1)).findAllById(ids);
    }
}
