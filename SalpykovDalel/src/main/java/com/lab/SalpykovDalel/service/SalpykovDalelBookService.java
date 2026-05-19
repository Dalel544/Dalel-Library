package com.lab.SalpykovDalel.service;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelBookRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelBookResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelAuthor;
import com.lab.SalpykovDalel.entity.SalpykovDalelBook;
import com.lab.SalpykovDalel.entity.SalpykovDalelCategory;
import com.lab.SalpykovDalel.exception.SalpykovDalelResourceNotFoundException;
import com.lab.SalpykovDalel.mapper.SalpykovDalelBookMapper;
import com.lab.SalpykovDalel.repository.SalpykovDalelAuthorRepository;
import com.lab.SalpykovDalel.repository.SalpykovDalelBookRepository;
import com.lab.SalpykovDalel.repository.SalpykovDalelCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SalpykovDalelBookService {

    private final SalpykovDalelBookRepository bookRepository;
    private final SalpykovDalelCategoryRepository categoryRepository;
    private final SalpykovDalelAuthorRepository authorRepository;
    private final SalpykovDalelBookMapper bookMapper;

    // ===== Pagination + Search + Filter =====
    public Page<SalpykovDalelBookResponse> getAll(String search, Long categoryId, Pageable pageable) {
        log.info("Fetching books with search={}, categoryId={}, pageable={}", search, categoryId, pageable);

        Page<SalpykovDalelBook> books;

        if (search != null && !search.isBlank()) {
            books = bookRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else if (categoryId != null) {
            books = bookRepository.findByCategoryId(categoryId, pageable);
        } else {
            books = bookRepository.findAll(pageable);
        }

        return books.map(bookMapper::toResponse);
    }

    public SalpykovDalelBookResponse getById(Long id) {
        log.info("Fetching book with id {}", id);
        return bookMapper.toResponse(findBookById(id));
    }

    public SalpykovDalelBookResponse create(SalpykovDalelBookRequest request) {
        log.info("Creating new book: {}", request.getTitle());
        SalpykovDalelBook book = bookMapper.toEntity(request);

        // set category
        if (request.getCategoryId() != null) {
            SalpykovDalelCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                            "Category with id " + request.getCategoryId() + " not found"));
            book.setCategory(category);
        }

        // set authors
        if (request.getAuthorIds() != null && !request.getAuthorIds().isEmpty()) {
            Set<SalpykovDalelAuthor> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
            book.setAuthors(authors);
        }

        SalpykovDalelBook saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    public SalpykovDalelBookResponse update(Long id, SalpykovDalelBookRequest request) {
        log.info("Updating book with id {}", id);
        SalpykovDalelBook book = findBookById(id);
        bookMapper.updateEntity(book, request);

        if (request.getCategoryId() != null) {
            SalpykovDalelCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                            "Category with id " + request.getCategoryId() + " not found"));
            book.setCategory(category);
        }

        if (request.getAuthorIds() != null) {
            Set<SalpykovDalelAuthor> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
            book.setAuthors(authors);
        }

        SalpykovDalelBook saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting book with id {}", id);
        SalpykovDalelBook book = findBookById(id);
        bookRepository.delete(book);
    }

    public SalpykovDalelBook findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "Book with id " + id + " not found"));
    }
}