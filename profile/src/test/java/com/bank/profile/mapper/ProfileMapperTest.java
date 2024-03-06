package com.bank.profile.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName(value = "Тест маппера ProfileMapper ")
@ExtendWith(MockitoExtension.class)
public class ProfileMapperTest {
    private ProfileMapper mapper;

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

    @BeforeEach
    public void setUp() {
        mapper = Mockito.mock(ProfileMapper.class);
    }

    @DisplayName(value = "маппинг в Entity, позитивный сценарий")
    @Test
    public void toEntity_MapperToEntity_PositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);

        ProfileEntity result = mapper.toEntity(userRobDto);

        verify(mapper).toEntity(userRobDto);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Entity, негативный сценарий")
    @Test
    public void toEntity_MapperToEntity_NegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(new ProfileEntity());

        ProfileEntity result = mapper.toEntity(userRobDto);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Dto, позитивный сценарий")
    @Test
    public void toDto_MapperToDto_PositiveTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        ProfileDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг в Dto, негативный сценарий")
    @Test
    public void toDto_MapperToDto_NegativeTest() {

        when(mapper.toDto(userRob)).thenReturn(new ProfileDto());

        ProfileDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, позитивный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_PositiveTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);

        ProfileEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        verify(mapper).mergeToEntity(userRobDto, userAlice);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, негативный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_NegativeTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(new ProfileEntity());

        ProfileEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в List с Dto, позитивный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_PositiveTest() {

        List<ProfileEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<ProfileDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<ProfileDto> result = mapper.toDtoList(accountEntities);

        verify(mapper).toDtoList(accountEntities);
        assertEquals(expectedDtoList, result);
    }

    @DisplayName(value = "маппинг в List с Dto, негативный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_NegativeTest() {

        List<ProfileEntity> accountEntities = Arrays.asList(userRob, userAlice);
        List<ProfileDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(Arrays.asList(new ProfileDto(), new ProfileDto()));

        List<ProfileDto> result = mapper.toDtoList(accountEntities);

        assertNotEquals(expectedDtoList, result);
    }
}
