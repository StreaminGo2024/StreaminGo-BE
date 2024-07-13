package com.project.demo.logic.entity.passwordResetRequests;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRequestRepository extends CrudRepository<PasswordResetRequest, Integer> {
}
