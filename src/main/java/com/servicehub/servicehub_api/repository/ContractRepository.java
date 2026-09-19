package com.servicehub.servicehub_api.repository;

import com.servicehub.servicehub_api.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}