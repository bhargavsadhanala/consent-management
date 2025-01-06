package com.app.consent.repository;

import com.app.consent.entity.ConsentAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentAuditLogRepository extends JpaRepository<ConsentAuditLog, Long> {

}
