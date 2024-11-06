package it.objectmethod.biblioteca.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationMetadata {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
