package com.lab.SalpykovDalel.repository;

import com.lab.SalpykovDalel.entity.SalpykovDalelAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalpykovDalelAuthorRepository extends JpaRepository<SalpykovDalelAuthor, Long> {
}
