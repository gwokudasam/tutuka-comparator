package com.tutuka.transaction.comparator.dal.repository;

import com.tutuka.transaction.comparator.dal.model.AuditTrail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Crud repository for the AuditTrail entity.
 */

@Repository
public interface AuditTrailRepository extends CrudRepository<AuditTrail, String> {
}
