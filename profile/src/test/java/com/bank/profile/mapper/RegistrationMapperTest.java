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

    @Test
    @DisplayName(value = "маппинг в Entity")
    public void toEntityTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);

        RegistrationEntity result = mapper.toEntity(userRobDto);

        verify(mapper).toEntity(userRobDto);
        assertEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в Entity, на вход подан null")
    public void toEntityNullTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(new RegistrationEntity());

        RegistrationEntity result = mapper.toEntity(userRobDto);

        assertNotEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в Dto")
    public void toDtoTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        RegistrationDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }

    @Test
    @DisplayName(value = "маппинг в dto, на вход подан null")
    public void toDtoNullTest() {

        when(mapper.toDto(userRob)).thenReturn(new RegistrationDto());

        RegistrationDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }

    @Test
    @DisplayName(value = "слияние в entity")
    public void mergeToEntityTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);

        RegistrationEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        verify(mapper).mergeToEntity(userRobDto, userAlice);
        assertEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "слияние в entity, на вход подан null")
    public void mergeToEntityNullTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(new RegistrationEntity());

        RegistrationEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        assertNotEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в List с Dto")
    public void toDtoListTest() {

        List<RegistrationEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<RegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<RegistrationDto> result = mapper.toDtoList(accountEntities);

        verify(mapper).toDtoList(accountEntities);
        assertEquals(expectedDtoList, result);
    }

    @Test
    @DisplayName(value = "маппинг в List с Dto, на вход подан null")
    public void toDtoListNullTest() {

        List<RegistrationEntity> accountEntities = Arrays.asList(userRob, userAlice);
        List<RegistrationDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities))
                .thenReturn(Arrays.asList(new RegistrationDto(), new RegistrationDto()));

        List<RegistrationDto> result = mapper.toDtoList(accountEntities);

        assertNotEquals(expectedDtoList, result);
    }
}
