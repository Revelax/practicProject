package com.bank.authorization.service;

import com.bank.authorization.mapper.AuditMapperImpl;
import com.bank.authorization.repository.AuditRepository;
import collect_entity.AuditsDto;
import collect_entity.AuditsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @Mock
    private AuditRepository auditRepository;

    @Spy
    private AuditMapperImpl auditMapper;

    @InjectMocks
    private AuditServiceImpl auditService;

    @ParameterizedTest
    @EnumSource(AuditsDto.class)
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest(AuditsDto auditsDto) {
        doReturn(Optional.of(AuditsEntity.findAuditEntity(auditsDto.getId())))
                .when(auditRepository).findById(auditsDto.getAuditDto().getId());


        assertThat(auditsDto.getAuditDto())
                .isEqualTo(auditService.findById(auditsDto.getId()));
    }

    @ParameterizedTest
    @EnumSource(AuditsDto.class)
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByIdIfUserNotExistsNegativeTest(AuditsDto auditsDto) {
        doReturn(Optional.empty())
                .when(auditRepository).findById(auditsDto.getId());

        assertThatThrownBy(() -> auditService.findById(auditsDto.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

}