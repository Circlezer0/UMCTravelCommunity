package travel.travel_community.entity.baseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {
    @CreationTimestamp //생성 시간
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")// 생성때만 관여, 생성 시간 자동 기록
    private LocalDateTime createdDate;

    @LastModifiedDate
    @UpdateTimestamp //업데이트 되었을때 시간
    @Column(insertable=false) // 수정 시에만 관여
    private LocalDateTime updatedDate;
}
