package it.objectmethod.biblioteca.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class PaginationMetadata {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
