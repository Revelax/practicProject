package com.bank.profile.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
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

@DisplayName(value = "Тест маппера RegistrationMapper ")
@ExtendWith(MockitoExtension.class)
public class RegistrationMapperTest {
    private RegistrationMapper mapper;

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

    @BeforeEach
    public void setUp() {
        mapper = Mockito.mock(RegistrationMapper.class);
    }

    @DisplayName(value = "маппинг в Entity, позитивный сценарий")
    @Test
    public void toEntity_MapperToEntity_PositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);

        RegistrationEntity result = mapper.toEntity(userRobDto);

        verify(mapper).toEntity(userRobDto);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Entity, негативный сценарий")
    @Test
    public void toEntity_MapperToEntity_NegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(new RegistrationEntity());

        RegistrationEntity result = mapper.toEntity(userRobDto);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Dto, позитивный сценарий")
    @Test
    public void toDto_MapperToDto_PositiveTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        RegistrationDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг в Dto, негативный сценарий")
    @Test
    public void toDto_MapperToDto_NegativeTest() {

        when(mapper.toDto(userRob)).thenReturn(new RegistrationDto());

        RegistrationDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, позитивный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_PositiveTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);

        RegistrationEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        verify(mapper).mergeToEntity(userRobDto, userAlice);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, негативный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_NegativeTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(new RegistrationEntity());

        RegistrationEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в List с Dto, позитивный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_PositiveTest() {

        List<RegistrationEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<RegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<RegistrationDto> result = mapper.toDtoList(accountEntities);

        verify(mapper).toDtoList(accountEntities);
        assertEquals(expectedDtoList, result);
    }

    @DisplayName(value = "маппинг в List с Dto, негативный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_NegativeTest() {

        List<RegistrationEntity> accountEntities = Arrays.asList(userRob, userAlice);
        List<RegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(Arrays.asList(new RegistrationDto(), new RegistrationDto()));

        List<RegistrationDto> result = mapper.toDtoList(accountEntities);

        assertNotEquals(expectedDtoList, result);
    }
}
