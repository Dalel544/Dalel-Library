package com.lab.SalpykovDalel.controller;

import com.lab.SalpykovDalel.entity.SalpykovDalelBook;
import com.lab.SalpykovDalel.service.SalpykovDalelBookService;
import com.lab.SalpykovDalel.service.SalpykovDalelFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class SalpykovDalelFileController {

    private final SalpykovDalelFileStorageService fileStorageService;
    private final SalpykovDalelBookService bookService;

    // ===== Upload book cover =====
    @PostMapping("/upload/cover/{bookId}")
    public ResponseEntity<Map<String, String>> uploadBookCover(
            @PathVariable Long bookId,
            @RequestParam("file") MultipartFile file) {

        log.info("Uploading cover for book {}", bookId);

        String fileName = fileStorageService.storeFile(file);

        // update book entity with new cover URL
        SalpykovDalelBook book = bookService.findBookById(bookId);
        book.setCoverImageUrl(fileName);

        return ResponseEntity.ok(Map.of(
                "fileName", fileName,
                "message", "Cover uploaded successfully"
        ));
    }

    // ===== Generic file upload =====
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Uploading file: {}", file.getOriginalFilename());
        String fileName = fileStorageService.storeFile(file);
        return ResponseEntity.ok(Map.of(
                "fileName", fileName,
                "message", "File uploaded successfully"
        ));
    }

    // ===== Download file =====
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        log.info("Downloading file: {}", fileName);

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}