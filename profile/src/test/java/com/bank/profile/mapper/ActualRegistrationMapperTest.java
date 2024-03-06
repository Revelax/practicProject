package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
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

@DisplayName(value = "Тест маппера ActualRegistrationMapper ")
@ExtendWith(MockitoExtension.class)
public class ActualRegistrationMapperTest {
    private ActualRegistrationMapper mapper;

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

    @BeforeEach
    public void setUp() {
        mapper = Mockito.mock(ActualRegistrationMapper.class);
    }

    @DisplayName(value = "маппинг в Entity, позитивный сценарий")
    @Test
    public void toEntity_MapperToEntity_PositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);

        ActualRegistrationEntity result = mapper.toEntity(userRobDto);

        verify(mapper).toEntity(userRobDto);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Entity, негативный сценарий")
    @Test
    public void toEntity_MapperToEntity_NegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(new ActualRegistrationEntity());

        ActualRegistrationEntity result = mapper.toEntity(userRobDto);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Dto, позитивный сценарий")
    @Test
    public void toDto_MapperToDto_PositiveTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        ActualRegistrationDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг в Dto, негативный сценарий")
    @Test
    public void toDto_MapperToDto_NegativeTest() {

        when(mapper.toDto(userRob)).thenReturn(new ActualRegistrationDto());

        ActualRegistrationDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, позитивный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_PositiveTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);

        ActualRegistrationEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        verify(mapper).mergeToEntity(userRobDto, userAlice);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, негативный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_NegativeTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(new ActualRegistrationEntity());

        ActualRegistrationEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в List с Dto, позитивный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_PositiveTest() {

        List<ActualRegistrationEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<ActualRegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<ActualRegistrationDto> result = mapper.toDtoList(accountEntities);

        verify(mapper).toDtoList(accountEntities);
        assertEquals(expectedDtoList, result);
    }

    @DisplayName(value = "маппинг в List с Dto, негативный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_NegativeTest() {

        List<ActualRegistrationEntity> accountEntities = Arrays.asList(userRob, userAlice);
        List<ActualRegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(Arrays.asList(new ActualRegistrationDto(), new ActualRegistrationDto()));

        List<ActualRegistrationDto> result = mapper.toDtoList(accountEntities);

        assertNotEquals(expectedDtoList, result);
    }
}
