package com.loopin.loopinbackend.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
    private String sortBy;
    private String direction;

    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements, String sort, String direction) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean isLast = (page + 1) >= totalPages;
        return new PageResponse<>(content, page, size, totalElements, totalPages, isLast, sort, direction);
    }
}
