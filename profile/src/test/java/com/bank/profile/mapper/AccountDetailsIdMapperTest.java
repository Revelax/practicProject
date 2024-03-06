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

    @DisplayName(value = "маппинг в Entity, позитивный сценарий")
    @Test
    public void toEntity_MapperToEntity_PositiveTest() {

        when(mapper.toEntity(userRobDto)).thenReturn(userRob);

        AccountDetailsIdEntity result = mapper.toEntity(userRobDto);

        verify(mapper).toEntity(userRobDto);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Entity, негативный сценарий")
    @Test
    public void toEntity_MapperToEntity_NegativeTest() {
        when(mapper.toEntity(userRobDto)).thenReturn(new AccountDetailsIdEntity());

        AccountDetailsIdEntity result = mapper.toEntity(userRobDto);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в Dto, позитивный сценарий")
    @Test
    public void toDto_MapperToDto_PositiveTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AccountDetailsIdDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг в Dto, негативный сценарий")
    @Test
    public void toDto_MapperToDto_NegativeTest() {

        when(mapper.toDto(userRob)).thenReturn(new AccountDetailsIdDto());

        AccountDetailsIdDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, позитивный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_PositiveTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(userRob);

        AccountDetailsIdEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        verify(mapper).mergeToEntity(userRobDto, userAlice);
        assertEquals(userRob, result);
    }

    @DisplayName(value = "маппинг слияния записей в Entity, негативный сценарий")
    @Test
    public void mergeToEntity_MapperMergeToEntity_NegativeTest() {

        when(mapper.mergeToEntity(userRobDto, userAlice)).thenReturn(new AccountDetailsIdEntity());

        AccountDetailsIdEntity result = mapper.mergeToEntity(userRobDto, userAlice);

        assertNotEquals(userRob, result);
    }

    @DisplayName(value = "маппинг в List с Dto, позитивный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_PositiveTest() {

        List<AccountDetailsIdEntity> accountEntities = Arrays.asList(userRob, userAlice);

        List<AccountDetailsIdDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(expectedDtoList);

        List<AccountDetailsIdDto> result = mapper.toDtoList(accountEntities);

        verify(mapper).toDtoList(accountEntities);
        assertEquals(expectedDtoList, result);
    }

    @DisplayName(value = "маппинг в List с Dto, негативный сценарий")
    @Test
    public void toDtoList_MapperToDtoList_NegativeTest() {

        List<AccountDetailsIdEntity> accountEntities = Arrays.asList(userRob, userAlice);
        List<AccountDetailsIdDto> expectedDtoList = Arrays.asList(userRobDto, userAliceDto);

        when(mapper.toDtoList(accountEntities)).thenReturn(Arrays.asList(new AccountDetailsIdDto(), new AccountDetailsIdDto()));

        List<AccountDetailsIdDto> result = mapper.toDtoList(accountEntities);

        assertNotEquals(expectedDtoList, result);
    }
}

