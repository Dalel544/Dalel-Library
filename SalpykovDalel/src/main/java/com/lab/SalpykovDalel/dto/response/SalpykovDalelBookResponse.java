package com.lab.SalpykovDalel.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class SalpykovDalelBookResponse {
    private Long id;
    private String title;
    private String isbn;
    private String description;
    private Integer publicationYear;
    private String publisher;
    private Integer totalCopies;
    private Integer availableCopies;
    private String coverImageUrl;
    private String fileUrl;
    private String categoryName;
    private Set<String> authorNames;
}