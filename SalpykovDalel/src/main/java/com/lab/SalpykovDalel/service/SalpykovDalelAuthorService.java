package com.lab.SalpykovDalel.service;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelAuthorRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelAuthorResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelAuthor;
import com.lab.SalpykovDalel.exception.SalpykovDalelResourceNotFoundException;
import com.lab.SalpykovDalel.mapper.SalpykovDalelAuthorMapper;
import com.lab.SalpykovDalel.repository.SalpykovDalelAuthorRepository;
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
public class SalpykovDalelAuthorService {

    private final SalpykovDalelAuthorRepository authorRepository;
    private final SalpykovDalelAuthorMapper authorMapper;

    public List<SalpykovDalelAuthorResponse> getAll() {
        log.info("Fetching all authors");
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SalpykovDalelAuthorResponse getById(Long id) {
        log.info("Fetching author with id {}", id);
        SalpykovDalelAuthor author = findAuthorById(id);
        return authorMapper.toResponse(author);
    }

    public SalpykovDalelAuthorResponse create(SalpykovDalelAuthorRequest request) {
        log.info("Creating new author: {} {}", request.getFirstName(), request.getLastName());
        SalpykovDalelAuthor author = authorMapper.toEntity(request);
        SalpykovDalelAuthor saved = authorRepository.save(author);
        return authorMapper.toResponse(saved);
    }

    public SalpykovDalelAuthorResponse update(Long id, SalpykovDalelAuthorRequest request) {
        log.info("Updating author with id {}", id);
        SalpykovDalelAuthor author = findAuthorById(id);
        authorMapper.updateEntity(author, request);
        SalpykovDalelAuthor saved = authorRepository.save(author);
        return authorMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting author with id {}", id);
        SalpykovDalelAuthor author = findAuthorById(id);
        authorRepository.delete(author);
    }

    public SalpykovDalelAuthor findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "Author with id " + id + " not found"));
    }
}