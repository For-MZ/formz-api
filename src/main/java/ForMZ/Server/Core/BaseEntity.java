package ForMZ.Server.Core;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass // 아래 필드를 컬럼으로 인식하게 함
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;

    @Enumerated(EnumType.STRING)
    private ObjectState objectState;

    public enum ObjectState {
        ACT,    // 활성화
        DEL     // 삭제, 비활성화
    }

    //상태 변경
    public void changeState(ObjectState state) {
        this.objectState = state;
    }
}
