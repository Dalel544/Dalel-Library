package com.lab.SalpykovDalel.repository;

import com.lab.SalpykovDalel.entity.SalpykovDalelReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalpykovDalelReviewRepository extends JpaRepository<SalpykovDalelReview, Long> {

    List<SalpykovDalelReview> findByBookId(Long bookId);
}
