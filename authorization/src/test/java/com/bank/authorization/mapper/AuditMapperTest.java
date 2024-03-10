package com.bank.authorization.mapper;

import collect_entity.AuditsDto;
import collect_entity.AuditsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuditMapperTest {

    @Spy
    private AuditMapperImpl auditMapper;

    @ParameterizedTest
    @EnumSource(AuditsEntity.class)
    @DisplayName("маппинг в Dto")
    void toDtoTest(AuditsEntity auditsEntity) {
        assertThat(auditMapper.toDto(auditsEntity.getAuditEntity()))
                .isNotSameAs(AuditsDto.findAudit(auditsEntity.getId()));
    }

    @Test
    @DisplayName("маппинг в Doto, на вход null")
    void toDtoNullTest() {
        assertThat(auditMapper.toDto(null)).isNull();
    }
}
