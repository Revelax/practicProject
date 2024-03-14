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

    @Test
    @DisplayName(value = "маппинг в Entity")
    public void toEntityTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);

        PassportEntity result = mapper.toEntity(userRobDto);

        verify(mapper).toEntity(userRobDto);
        assertEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в Entity, на вход подан null")
    public void toEntityNullTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(new PassportEntity());

        PassportEntity result = mapper.toEntity(userRobDto);

        assertNotEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в Dto")
    public void toDtoTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        PassportDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }

    @Test
    @DisplayName(value = "маппинг в dto, на вход подан null")
    public void toDtoNullTest() {

        when(mapper.toDto(userRob)).thenReturn(new PassportDto());

        PassportDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }

    @Test
    @DisplayName(value = "слияние в entity")
    public void mergeToEntityTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);

        PassportEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        verify(mapper).mergeToEntity(userRobDto, userAlice);
        assertEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "слияние в entity, на вход подан null")
    public void mergeToEntityNullTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(new PassportEntity());

        PassportEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        assertNotEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в List с Dto")
    public void toDtoListTest() {

        List<PassportEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<PassportDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<PassportDto> result = mapper.toDtoList(accountEntities);

        verify(mapper).toDtoList(accountEntities);
        assertEquals(expectedDtoList, result);
    }

    @Test
    @DisplayName(value = "маппинг в List с Dto, на вход подан null")
    public void toDtoListNullTest() {

        List<PassportEntity> accountEntities = Arrays.asList(userRob, userAlice);
        List<PassportDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities))
                .thenReturn(Arrays.asList(new PassportDto(), new PassportDto()));

        List<PassportDto> result = mapper.toDtoList(accountEntities);

        assertNotEquals(expectedDtoList, result);
    }
}
