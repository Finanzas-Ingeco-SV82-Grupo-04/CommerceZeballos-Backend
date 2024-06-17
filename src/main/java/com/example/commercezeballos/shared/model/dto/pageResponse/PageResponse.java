package com.example.commercezeballos.shared.model.dto.pageResponse;

import com.example.commercezeballos.products_management.application.dtos.response.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

        private int pageNumber;
        private int totalPages;
        private long totalElements;
        private int currentPage;
        private int pageSize;
        private List<T> content;


}
