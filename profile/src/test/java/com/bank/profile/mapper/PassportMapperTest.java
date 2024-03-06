package com.bank.profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
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

@DisplayName(value = "Тест маппера PassportMapper ")
@ExtendWith(MockitoExtension.class)
public class PassportMapperTest {
    private PassportMapper mapper;

    private final PassportEntity userRob = new PassportEntity();

    {
        userRob.setId(1L);
        userRob.setFirstName("Rob");
    }

    private final PassportEntity userAlice = new PassportEntity();

    {
        userAlice.setId(2L);
        userAlice.setFirstName("Alice");
    }

    private final PassportDto userRobDto = new PassportDto();

    {
        userRobDto.setId(1L);
        userRobDto.setFirstName("Rob");
    }

    private final PassportDto userAliceDto = new PassportDto();

    {
        userAliceDto.setId(2L);
        userAliceDto.setFirstName("Alice");
    }

    @BeforeEach
    public void setUp() {
        mapper = Mockito.mock(PassportMapper.class);
    }

    @DisplayName(value = "маппинг в Entity, позитивный сценарий")
    @Test
    public void toEntity_MapperToEntity_PositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);

        PassportEntity result = mapper.toEntity(userRobDto);

        verify(mapper).toEntity(userRobDto);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Entity, негативный сценарий")
    @Test
    public void toEntity_MapperToEntity_NegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(new PassportEntity());

        PassportEntity result = mapper.toEntity(userRobDto);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Dto, позитивный сценарий")
    @Test
    public void toDto_MapperToDto_PositiveTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        PassportDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг в Dto, негативный сценарий")
    @Test
    public void toDto_MapperToDto_NegativeTest() {

        when(mapper.toDto(userRob)).thenReturn(new PassportDto());

        PassportDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, позитивный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_PositiveTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);

        PassportEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        verify(mapper).mergeToEntity(userRobDto, userAlice);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, негативный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_NegativeTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(new PassportEntity());

        PassportEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в List с Dto, позитивный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_PositiveTest() {

        List<PassportEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<PassportDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<PassportDto> result = mapper.toDtoList(accountEntities);

        verify(mapper).toDtoList(accountEntities);
        assertEquals(expectedDtoList, result);
    }

    @DisplayName(value = "маппинг в List с Dto, негативный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_NegativeTest() {

        List<PassportEntity> accountEntities = Arrays.asList(userRob, userAlice);
        List<PassportDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(Arrays.asList(new PassportDto(), new PassportDto()));

        List<PassportDto> result = mapper.toDtoList(accountEntities);

        assertNotEquals(expectedDtoList, result);
    }
}
