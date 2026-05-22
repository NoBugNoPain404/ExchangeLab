package huynguyen.common.common_jpa;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    private Instant createdAt;
    private Instant updatedAt;
}
