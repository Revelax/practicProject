package com.bank.account.mapper;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AccountAuditMapperTest {

    private static final AuditEntity AUDIT_ENTITY = new AuditEntity(
            1L, "arg1", "arg2", "arg3", "arg4",
            Timestamp.valueOf("2022-03-01 12:00:00.000"),
            Timestamp.valueOf("2024-03-02 12:00:00.000"),
            "args5",
            "args6"
    );

    @Spy
    private AccountAuditMapperImpl mapper;

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        AuditDto auditDto = mapper.toDto(AUDIT_ENTITY);

        assertThat(auditDto).isNotNull();
        assertThat(auditDto.getId()).isEqualTo(AUDIT_ENTITY.getId());
        assertThat(auditDto.getEntityType()).isEqualTo(AUDIT_ENTITY.getEntityType());
        assertThat(auditDto.getOperationType()).isEqualTo(AUDIT_ENTITY.getOperationType());
        assertThat(auditDto.getCreatedBy()).isEqualTo(AUDIT_ENTITY.getCreatedBy());
        assertThat(auditDto.getModifiedBy()).isEqualTo(AUDIT_ENTITY.getModifiedBy());
        assertThat(auditDto.getCreatedAt()).isEqualTo(AUDIT_ENTITY.getCreatedAt());
        assertThat(auditDto.getModifiedAt()).isEqualTo(AUDIT_ENTITY.getModifiedAt());
        assertThat(auditDto.getNewEntityJson()).isEqualTo(AUDIT_ENTITY.getNewEntityJson());
        assertThat(auditDto.getEntityJson()).isEqualTo(AUDIT_ENTITY.getEntityJson());
    }

    @Test
    @DisplayName("маппинг в dto, на вход передан null")
    void toDtoNullTest() {
        AuditDto auditDto = mapper.toDto(null);
        assertThat(auditDto).isNull();
    }
}