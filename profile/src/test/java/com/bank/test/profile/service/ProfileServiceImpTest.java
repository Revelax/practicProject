package com.bank.test.profile.service;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.impl.ProfileServiceImp;
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
public class ProfileServiceImpTest {

    @Mock
    private ProfileRepository repository;

    @Mock
    private ProfileMapper mapper;

    @InjectMocks
    private ProfileServiceImp profileService;

    private final ProfileEntity userRob = new ProfileEntity();
    {
        userRob.setId(1L);
        userRob.setInn(123L);
    }
    private final ProfileEntity userAlice = new ProfileEntity();
    {
        userRob.setId(2L);
        userRob.setInn(125L);
    }
    private final ProfileDto userRobDto = new ProfileDto();
    {
        userRob.setId(1L);
        userRob.setInn(123L);
    }
    private final ProfileDto userAliceDto = new ProfileDto();
    {
        userRob.setId(2L);
        userRob.setInn(125L);
    }

    private final Long id = 1L;
    @Test
    public void testFindAllById() {
        List<Long> ids = Arrays.asList(1L, 2L);

        List<ProfileEntity> profileEntities = Arrays.asList(userRob, userAlice);

        List<ProfileDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(repository.findAllById(ids)).thenReturn(profileEntities);
        when(mapper.toDtoList(profileEntities)).thenReturn(expectedDtoList);

        List<ProfileDto> actualDtoList = profileService.findAllById(ids);

        assertEquals(expectedDtoList, actualDtoList);
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(profileEntities);
    }

    @Test
    public void testFindById() {

        ProfileEntity profileEntity = userRob;
        ProfileDto expectedDto = userRobDto;

        when(repository.findById(id)).thenReturn(Optional.of(profileEntity));
        when(mapper.toDto(profileEntity)).thenReturn(expectedDto);

        ProfileDto actualDto = profileService.findById(id);

        assertEquals(expectedDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(profileEntity);
    }

    @Test
    public void testFindById_EntityNotFoundException() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> profileService.findById(id));

        assertEquals("profile с данным id не найден!", exception.getMessage());
    }

    @Test
    public void testSave() {
        ProfileDto profileDto = userRobDto;
        ProfileEntity profileEntity = userRob;

        when(mapper.toEntity(profileDto)).thenReturn(profileEntity);
        when(repository.save(profileEntity)).thenReturn(profileEntity);
        when(mapper.toDto(profileEntity)).thenReturn(profileDto);

        ProfileDto savedProfileDto = profileService.save(profileDto);

        assertEquals(profileDto, savedProfileDto);
        verify(mapper, times(1)).toEntity(profileDto);
        verify(repository, times(1)).save(profileEntity);
        verify(mapper, times(1)).toDto(profileEntity);
    }

    @Test
    public void testUpdate() {
        ProfileDto profileDto = userRobDto;
        ProfileEntity profileEntityById = userAlice;
        ProfileEntity updatedProfileEntity = userRob;

        when(repository.findById(id)).thenReturn(Optional.of(profileEntityById));
        when(mapper.mergeToEntity(profileDto, profileEntityById)).thenReturn(updatedProfileEntity);
        when(repository.save(updatedProfileEntity)).thenReturn(updatedProfileEntity);
        when(mapper.toDto(updatedProfileEntity)).thenReturn(profileDto);

        ProfileDto updatedProfileDto = profileService.update(id, profileDto);

        assertEquals(profileDto, updatedProfileDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(profileDto, profileEntityById);
        verify(repository, times(1)).save(updatedProfileEntity);
        verify(mapper, times(1)).toDto(updatedProfileEntity);
    }

    @Test
    public void testUpdate_EntityNotFoundException() {
        ProfileDto profileDto = userAliceDto;

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> profileService.update(id, profileDto));

        assertEquals("Обновление невозможно, profile не найден!", exception.getMessage());
    }

}
