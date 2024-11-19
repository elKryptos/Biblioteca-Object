package it.objectmethod.biblioteca.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<TYPE> {
    private String msg;
    private TYPE type;
    @JsonProperty("pagination")
    private PaginationMetadata pagination;


    public ResponseWrapper(String msg) {
        this.msg = msg;
    }

    public ResponseWrapper(TYPE type) {
        this.type = type;
    }

    public ResponseWrapper(String msg, TYPE type) {
        this.msg = msg;
        this.type = type;
    }

    public ResponseWrapper(String msg, TYPE type, PaginationMetadata pagination) {
        this.msg = msg;
        this.type = type;
        this.pagination = pagination;
    }
}
