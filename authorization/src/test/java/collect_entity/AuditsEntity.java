package collect_entity;

import com.bank.authorization.entity.AuditEntity;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuditsEntity {
    AUDIT_1(1L, new AuditEntity()),
    AUDIT_2(2L, new AuditEntity());

    private final Long id;
    private final AuditEntity auditEntity;

    AuditsEntity(Long id, AuditEntity auditEntity) {
        this.id = id;

        auditEntity.setId(id);
        this.auditEntity = auditEntity;
    }

    public static AuditEntity findAuditEntity(Long id) {
        return Arrays.stream(AuditsEntity.values())
                .filter(auditsEntity -> auditsEntity.getId() == id)
                .findFirst()
                .orElseGet(() -> AuditsEntity.AUDIT_1)
                .getAuditEntity();
    }
}
