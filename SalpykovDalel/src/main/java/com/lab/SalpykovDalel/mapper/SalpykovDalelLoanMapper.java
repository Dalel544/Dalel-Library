package com.lab.SalpykovDalel.mapper;

import com.lab.SalpykovDalel.dto.response.SalpykovDalelLoanResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelLoan;
import org.springframework.stereotype.Component;

@Component
public class SalpykovDalelLoanMapper {

    public SalpykovDalelLoanResponse toResponse(SalpykovDalelLoan entity) {
        SalpykovDalelLoanResponse response = new SalpykovDalelLoanResponse();
        response.setId(entity.getId());
        response.setUserId(entity.getUser().getId());
        response.setUsername(entity.getUser().getUsername());
        response.setBookId(entity.getBook().getId());
        response.setBookTitle(entity.getBook().getTitle());
        response.setLoanDate(entity.getLoanDate());
        response.setDueDate(entity.getDueDate());
        response.setReturnDate(entity.getReturnDate());
        response.setStatus(entity.getStatus());
        response.setFineAmount(entity.getFineAmount());
        return response;
    }
}