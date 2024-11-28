package com.devsu.accounts.msaccountstrxs.repository;

import com.devsu.accounts.msaccountstrxs.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Long> {
    EventLog findFirstByOrderByIdDesc();
}
