package com.bank.profile.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@DisplayName(value = "Тест маппера AuditMapper ")
@ExtendWith(MockitoExtension.class)
public class AuditMapperTest {
    private AuditMapper mapper;

    private final AuditEntity userRob = new AuditEntity();

    {
        userRob.setId(1L);
        userRob.setEntityType("User");
    }

    private final AuditDto userRobDto = new AuditDto();

    {
        userRobDto.setId(1L);
        userRobDto.setEntityType("User");
    }

    @BeforeEach
    public void setUp() {
        mapper = Mockito.mock(AuditMapper.class);
    }
    @DisplayName(value = "маппинг в Dto, позитивный сценарий")
    @Test
    public void toDto_MapperToDto_PositiveTest() {

        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AuditDto result = mapper.toDto(userRob);

        verify(mapper).toDto(userRob);
        assertEquals(userRobDto, result);
    }
    @DisplayName(value = "маппинг в Dto, негативный сценарий")
    @Test
    public void toDto_MapperToDto_NegativeTest() {

        when(mapper.toDto(userRob)).thenReturn(new AuditDto());

        AuditDto result = mapper.toDto(userRob);

        assertNotEquals(userRobDto, result);
    }
}
