package com.consent.repository;

import com.consent.entity.ConsentAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentAuditLogRepository extends JpaRepository<ConsentAuditLog, Long> {

}
