package com.lab.SalpykovDalel.service;

import com.lab.SalpykovDalel.dto.request.SalpykovDalelCategoryRequest;
import com.lab.SalpykovDalel.dto.response.SalpykovDalelCategoryResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelCategory;
import com.lab.SalpykovDalel.exception.SalpykovDalelResourceNotFoundException;
import com.lab.SalpykovDalel.mapper.SalpykovDalelCategoryMapper;
import com.lab.SalpykovDalel.repository.SalpykovDalelCategoryRepository;
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
public class SalpykovDalelCategoryService {

    private final SalpykovDalelCategoryRepository categoryRepository;
    private final SalpykovDalelCategoryMapper categoryMapper;

    public List<SalpykovDalelCategoryResponse> getAll() {
        log.info("Fetching all categories");
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SalpykovDalelCategoryResponse getById(Long id) {
        log.info("Fetching category with id {}", id);
        SalpykovDalelCategory category = findCategoryById(id);
        return categoryMapper.toResponse(category);
    }

    public SalpykovDalelCategoryResponse create(SalpykovDalelCategoryRequest request) {
        log.info("Creating new category: {}", request.getName());
        SalpykovDalelCategory category = categoryMapper.toEntity(request);
        SalpykovDalelCategory saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public SalpykovDalelCategoryResponse update(Long id, SalpykovDalelCategoryRequest request) {
        log.info("Updating category with id {}", id);
        SalpykovDalelCategory category = findCategoryById(id);
        categoryMapper.updateEntity(category, request);
        SalpykovDalelCategory saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting category with id {}", id);
        SalpykovDalelCategory category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    // helper method for internal use
    public SalpykovDalelCategory findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "Category with id " + id + " not found"));
    }
}