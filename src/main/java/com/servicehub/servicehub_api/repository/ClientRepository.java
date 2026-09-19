package com.servicehub.servicehub_api.repository;

import com.servicehub.servicehub_api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}