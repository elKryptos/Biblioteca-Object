package it.objectmethod.biblioteca.models.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
public class ResponseWrapper<TYPE> {
    private String msg;
    private TYPE type;
    private PaginationMetadata pagination;

    public ResponseWrapper(String msg) {
        this.msg = msg;
    }

    public ResponseWrapper(TYPE type) {
        this.type = type;
    }

    public ResponseWrapper(String msg, TYPE type) {
        this.msg = msg;
        if (type instanceof Page) {
            Page<?> page = (Page<?>) type;
            this.type = (TYPE) page.getContent(); // Convert Page content to List
            this.pagination = new PaginationMetadata(
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalElements(),
                    page.getTotalPages()
            );
        } else {
            this.type = type;
        }
    }

}
