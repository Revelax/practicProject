package collect_entity;

import com.bank.authorization.dto.AuditDto;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuditsDto {
    AUDIT_1(1L, new AuditDto()),
    AUDIT_2(2L, new AuditDto());

    private final Long id;
    private final AuditDto auditDto;

    AuditsDto(Long id, AuditDto auditDto) {
        this.id = id;

        auditDto.setId(id);
        this.auditDto = auditDto;
    }

    public static AuditDto findAudit(Long id) {
        return Arrays.stream(AuditsDto.values())
                .map(AuditsDto::getAuditDto)
                .findFirst()
                .orElse(AUDIT_1.getAuditDto());
    }
}
