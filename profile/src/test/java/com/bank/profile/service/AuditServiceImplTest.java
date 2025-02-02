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

    private final Long id = 1L;

    @Test
    @DisplayName(value = "поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {

        when(repository.findById(id)).thenReturn(Optional.of(userRob));
        when(mapper.toDto(userRob)).thenReturn(userRobDto);

        AuditDto actualDto = service.findById(id);

        assertEquals(userRobDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(userRob);
    }

    @Test
    @DisplayName(value = "поиск по несуществующему id, негативный сценарий")
    public void findByNonExistIdNegativeTest() {

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.findById(id));

        assertEquals("Не найден аудит с ID  1", exception.getMessage());
    }
}
