package com.lab.SalpykovDalel.service;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelReviewRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelReviewResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelBook;
import com.lab.SalpykovDalel.entity.SalpykovDalelReview;
import com.lab.SalpykovDalel.entity.SalpykovDalelUser;
import com.lab.SalpykovDalel.exception.SalpykovDalelResourceNotFoundException;
import com.lab.SalpykovDalel.mapper.SalpykovDalelReviewMapper;
import com.lab.SalpykovDalel.repository.SalpykovDalelBookRepository;
import com.lab.SalpykovDalel.repository.SalpykovDalelReviewRepository;
import com.lab.SalpykovDalel.repository.SalpykovDalelUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SalpykovDalelReviewService {

    private final SalpykovDalelReviewRepository reviewRepository;
    private final SalpykovDalelUserRepository userRepository;
    private final SalpykovDalelBookRepository bookRepository;
    private final SalpykovDalelReviewMapper reviewMapper;

    public List<SalpykovDalelReviewResponse> getAll() {
        log.info("Fetching all reviews");
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SalpykovDalelReviewResponse> getByBookId(Long bookId) {
        log.info("Fetching reviews for book {}", bookId);
        return reviewRepository.findByBookId(bookId).stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SalpykovDalelReviewResponse create(Long userId, SalpykovDalelReviewRequest request) {
        log.info("Creating review: userId={}, bookId={}", userId, request.getBookId());

        SalpykovDalelUser user = userRepository.findById(userId)
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "User with id " + userId + " not found"));

        SalpykovDalelBook book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "Book with id " + request.getBookId() + " not found"));

        SalpykovDalelReview review = SalpykovDalelReview.builder()
                .user(user)
                .book(book)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        SalpykovDalelReview saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting review with id {}", id);
        SalpykovDalelReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "Review with id " + id + " not found"));
        reviewRepository.delete(review);
    }
}