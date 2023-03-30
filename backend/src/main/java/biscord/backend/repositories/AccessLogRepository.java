package biscord.backend.repositories;

import biscord.backend.entities.AccessLog;
import biscord.backend.entities.AccessLogId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, AccessLogId> {
}
