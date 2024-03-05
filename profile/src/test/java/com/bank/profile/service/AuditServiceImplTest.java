package com.bank.profile.service;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName(value = "Тест сервиса AuditServiceImpl ")
@ExtendWith(MockitoExtension.class)
public class AuditServiceImplTest {
    @Mock
    private AuditRepository repository;

    @Mock
    private AuditMapper mapper;

    @InjectMocks
    private AuditServiceImpl service;

    private final AuditEntity auditEntity = new AuditEntity();

    {
        auditEntity.setId(1L);
        auditEntity.setEntityType("User");
    }

    private final AuditDto expectedDto = new AuditDto();

    {
        expectedDto.setId(1L);
        expectedDto.setEntityType("User");
    }

    private final Long id = 1L;
    @DisplayName(value = "поиск по id, позитивный сценарий")
    @Test
    public void findById_ReturnsActualDto_PositiveTest() {

        when(repository.findById(id)).thenReturn(Optional.of(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(expectedDto);

        AuditDto actualDto = service.findById(id);

        assertEquals(expectedDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(auditEntity);
    }
    @DisplayName(value = "поиск по несуществующему id, негативный сценарий")
    @Test
    public void findById_ReturnsEntityNotFoundException_NegativeTest() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.findById(id));

        assertEquals("Не найден аудит с ID  1", exception.getMessage());
    }

}
