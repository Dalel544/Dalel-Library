package com.lab.SalpykovDalel.service;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelLoanRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelLoanResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelBook;
import com.lab.SalpykovDalel.entity.SalpykovDalelLoan;
import com.lab.SalpykovDalel.entity.SalpykovDalelUser;
import com.lab.SalpykovDalel.entity.enums.SalpykovDalelLoanStatus;
import com.lab.SalpykovDalel.exception.SalpykovDalelBadRequestException;
import com.lab.SalpykovDalel.exception.SalpykovDalelResourceNotFoundException;
import com.lab.SalpykovDalel.mapper.SalpykovDalelLoanMapper;
import com.lab.SalpykovDalel.repository.SalpykovDalelBookRepository;
import com.lab.SalpykovDalel.repository.SalpykovDalelLoanRepository;
import com.lab.SalpykovDalel.repository.SalpykovDalelUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SalpykovDalelLoanService {

    private final SalpykovDalelLoanRepository loanRepository;
    private final SalpykovDalelUserRepository userRepository;
    private final SalpykovDalelBookRepository bookRepository;
    private final SalpykovDalelLoanMapper loanMapper;
    private final SalpykovDalelNotificationService notificationService;

    public List<SalpykovDalelLoanResponse> getAll() {
        log.info("Fetching all loans");
        return loanRepository.findAll().stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SalpykovDalelLoanResponse> getByUserId(Long userId) {
        log.info("Fetching loans for user {}", userId);
        return loanRepository.findByUserId(userId).stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SalpykovDalelLoanResponse getById(Long id) {
        log.info("Fetching loan with id {}", id);
        return loanMapper.toResponse(findLoanById(id));
    }

    // ===== Issue a book (business logic!) =====
    public SalpykovDalelLoanResponse createLoan(SalpykovDalelLoanRequest request) {
        log.info("Creating loan: userId={}, bookId={}", request.getUserId(), request.getBookId());

        SalpykovDalelUser user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "User with id " + request.getUserId() + " not found"));

        SalpykovDalelBook book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "Book with id " + request.getBookId() + " not found"));

        // business rule: check availability
        if (book.getAvailableCopies() <= 0) {
            throw new SalpykovDalelBadRequestException("No available copies of this book");
        }

        // decrement available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        SalpykovDalelLoan loan = SalpykovDalelLoan.builder()
                .user(user)
                .book(book)
                .loanDate(LocalDate.now())
                .dueDate(request.getDueDate())
                .status(SalpykovDalelLoanStatus.ACTIVE)
                .fineAmount(0.0)
                .build();

        SalpykovDalelLoan saved = loanRepository.save(loan);
        notificationService.sendLoanNotification(
                user.getEmail(),
                book.getTitle(),
                loan.getDueDate().toString()
        );
        return loanMapper.toResponse(saved);
    }

    // ===== Return a book =====
    public SalpykovDalelLoanResponse returnBook(Long loanId) {
        log.info("Returning book for loan {}", loanId);

        SalpykovDalelLoan loan = findLoanById(loanId);

        if (loan.getStatus() != SalpykovDalelLoanStatus.ACTIVE) {
            throw new SalpykovDalelBadRequestException("This loan is not active");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(SalpykovDalelLoanStatus.RETURNED);

        // increment available copies
        SalpykovDalelBook book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        SalpykovDalelLoan saved = loanRepository.save(loan);
        return loanMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting loan with id {}", id);
        SalpykovDalelLoan loan = findLoanById(id);
        loanRepository.delete(loan);
    }

    public SalpykovDalelLoan findLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "Loan with id " + id + " not found"));
    }
}