package com.bank.profile.mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
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

@DisplayName(value = "Тест маппера AccountDetailsIdMapper ")
@ExtendWith(MockitoExtension.class)
public class AccountDetailsIdMapperTest {

    private AccountDetailsIdMapper mapper;

    private final AccountDetailsIdEntity userRob = new AccountDetailsIdEntity();

    {
        userRob.setId(1L);
    }

    private final AccountDetailsIdEntity userAlice = new AccountDetailsIdEntity();

    {
        userAlice.setId(2L);
    }

    private final AccountDetailsIdDto userRobDto = new AccountDetailsIdDto();

    {
        userRobDto.setId(1L);
    }

    private final AccountDetailsIdDto userAliceDto = new AccountDetailsIdDto();

    {
        userAliceDto.setId(2L);
    }

    @BeforeEach
    public void setUp() {
        mapper = Mockito.mock(AccountDetailsIdMapper.class);
    }

    @Test
    @DisplayName(value = "маппинг в Entity")
    public void toEntityTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);

        AccountDetailsIdEntity result = mapper.toEntity(userRobDto);

        verify(mapper).toEntity(userRobDto);
        assertEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в Entity, на вход подан null")
    public void toEntityNullTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(new AccountDetailsIdEntity());

        AccountDetailsIdEntity result = mapper.toEntity(userRobDto);

        assertNotEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в Dto")
    public void toDtoTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AccountDetailsIdDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }

    @Test
    @DisplayName(value = "маппинг в dto, на вход подан null")
    public void toDtoNullTest() {

        when(mapper.toDto(userRob)).thenReturn(new AccountDetailsIdDto());

        AccountDetailsIdDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }

    @Test
    @DisplayName(value = "слияние в entity")
    public void mergeToEntityTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);

        AccountDetailsIdEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        verify(mapper).mergeToEntity(userRobDto, userAlice);
        assertEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "слияние в entity, на вход подан null")
    public void mergeToEntityNullTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(new AccountDetailsIdEntity());

        AccountDetailsIdEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        assertNotEquals(userRob, result);
    }

    @Test
    @DisplayName(value = "маппинг в List с Dto")
    public void toDtoListTest() {

        List<AccountDetailsIdEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<AccountDetailsIdDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<AccountDetailsIdDto> result = mapper.toDtoList(accountEntities);

        verify(mapper).toDtoList(accountEntities);
        assertEquals(expectedDtoList, result);
    }

    @Test
    @DisplayName(value = "маппинг в List с Dto, на вход подан null")
    public void toDtoListNullTest() {

        List<AccountDetailsIdEntity> accountEntities = Arrays.asList(userRob, userAlice);
        List<AccountDetailsIdDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities))
                .thenReturn(Arrays.asList(new AccountDetailsIdDto(), new AccountDetailsIdDto()));

        List<AccountDetailsIdDto> result = mapper.toDtoList(accountEntities);

        assertNotEquals(expectedDtoList, result);
    }
}

