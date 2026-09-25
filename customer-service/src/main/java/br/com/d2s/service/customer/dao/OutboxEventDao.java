package br.com.d2s.service.customer.dao;

import br.com.d2s.service.customer.model.OutboxEvent;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OutboxEventDao extends JpaRepository<OutboxEvent, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
         from OutboxEvent event
         where event.status = 'PENDING'
         order by event.createdDate
    """)
    List<OutboxEvent> findPendingEvents(Pageable pageable);
}
