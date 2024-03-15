package com.bank.account.service;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.mapper.AccountAuditMapperImpl;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.service.common.ExceptionReturner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountAuditServiceImplTest {

    private static final AuditEntity AUDIT_ENTITY = new AuditEntity(
            1L, "arg1", "arg2", "arg3", "arg4",
            Timestamp.valueOf("2022-03-01 12:00:00.000"),
            Timestamp.valueOf("2024-03-02 12:00:00.000"),
            "args5",
            "args6"
    );

    @Mock
    private AccountAuditRepository repository;

    @Spy
    private AccountAuditMapperImpl mapper;

    @Spy
    private ExceptionReturner exceptionReturner;

    @InjectMocks
    private AccountAuditServiceImpl service;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        doReturn(Optional.of(AUDIT_ENTITY)).when(repository).findById(AUDIT_ENTITY.getId());

        AuditDto resultDto = service.findById(AUDIT_ENTITY.getId());

        verify(repository, times(1)).findById(AUDIT_ENTITY.getId());
        verify(mapper, times(1)).toDto(AUDIT_ENTITY);

        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getId()).isEqualTo(AUDIT_ENTITY.getId());
        assertThat(resultDto.getEntityType()).isEqualTo(AUDIT_ENTITY.getEntityType());
        assertThat(resultDto.getOperationType()).isEqualTo(AUDIT_ENTITY.getOperationType());
        assertThat(resultDto.getCreatedBy()).isEqualTo(AUDIT_ENTITY.getCreatedBy());
        assertThat(resultDto.getModifiedBy()).isEqualTo(AUDIT_ENTITY.getModifiedBy());
        assertThat(resultDto.getCreatedAt()).isEqualTo(AUDIT_ENTITY.getCreatedAt());
        assertThat(resultDto.getModifiedAt()).isEqualTo(AUDIT_ENTITY.getModifiedAt());
        assertThat(resultDto.getNewEntityJson()).isEqualTo(AUDIT_ENTITY.getNewEntityJson());
        assertThat(resultDto.getEntityJson()).isEqualTo(AUDIT_ENTITY.getEntityJson());
    }

    @Test
    @SuppressWarnings("ThrowableNotThrown")
    @DisplayName("поиск по id, передан неверный id, негативный сценарий")
    void findByIdWithInvalidIdNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));

        verify(repository, times(1)).findById(1L);
        verify(exceptionReturner, times(1))
                .getEntityNotFoundException("Не существующий id = " + 1L);
    }
}
