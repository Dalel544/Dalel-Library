package com.lab.SalpykovDalel.repository;

import com.lab.SalpykovDalel.entity.SalpykovDalelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalpykovDalelUserRepository extends JpaRepository<SalpykovDalelUser, Long> {

    Optional<SalpykovDalelUser> findByUsername(String username);

    Optional<SalpykovDalelUser> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
