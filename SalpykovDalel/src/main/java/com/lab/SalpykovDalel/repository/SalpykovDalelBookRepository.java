package com.lab.SalpykovDalel.repository;

import com.lab.SalpykovDalel.entity.SalpykovDalelBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalpykovDalelBookRepository extends JpaRepository<SalpykovDalelBook, Long> {

    Page<SalpykovDalelBook> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<SalpykovDalelBook> findByCategoryId(Long categoryId, Pageable pageable);
}
