package com.lab.SalpykovDalel.repository;

import com.lab.SalpykovDalel.entity.SalpykovDalelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalpykovDalelCategoryRepository extends JpaRepository<SalpykovDalelCategory, Long> {
}
