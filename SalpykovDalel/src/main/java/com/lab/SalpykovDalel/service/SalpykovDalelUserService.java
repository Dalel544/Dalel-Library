package com.lab.SalpykovDalel.service;

import com.lab.SalpykovDalel.dto.response.SalpykovDalelUserResponse;
import com.lab.SalpykovDalel.entity.SalpykovDalelUser;
import com.lab.SalpykovDalel.exception.SalpykovDalelResourceNotFoundException;
import com.lab.SalpykovDalel.mapper.SalpykovDalelUserMapper;
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
public class SalpykovDalelUserService {

    private final SalpykovDalelUserRepository userRepository;
    private final SalpykovDalelUserMapper userMapper;

    public List<SalpykovDalelUserResponse> getAll() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SalpykovDalelUserResponse getById(Long id) {
        log.info("Fetching user with id {}", id);
        SalpykovDalelUser user = findUserById(id);
        return userMapper.toResponse(user);
    }

    public void delete(Long id) {
        log.info("Deleting user with id {}", id);
        SalpykovDalelUser user = findUserById(id);
        userRepository.delete(user);
    }

    public SalpykovDalelUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new SalpykovDalelResourceNotFoundException(
                        "User with id " + id + " not found"));
    }
}