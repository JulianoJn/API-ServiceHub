
package com.servicehub.servicehub_api.repository;

import com.servicehub.servicehub_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Metodo mágico do Spring que busca pelo e-mail
}