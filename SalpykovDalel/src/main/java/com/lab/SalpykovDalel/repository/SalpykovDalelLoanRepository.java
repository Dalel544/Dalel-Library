package com.lab.SalpykovDalel.repository;

import com.lab.SalpykovDalel.entity.SalpykovDalelLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalpykovDalelLoanRepository extends JpaRepository<SalpykovDalelLoan, Long> {

    List<SalpykovDalelLoan> findByUserId(Long userId);
}
