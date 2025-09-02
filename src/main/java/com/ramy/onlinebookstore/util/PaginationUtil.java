package com.ramy.onlinebookstore.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;

public class PaginationUtil {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public static Sort getSort(String sortField, String sortDir) {
        return sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }

    public static class FilterResult {
        public Map<String, Object> filters;
        public int page;
        public int size;
        public String sortField;
        public String sortDir;
    }

    public static PaginationRequest parseQueryParams(Map<String, String> queryParams) {
        Map<String, Object> filters = new HashMap<>(queryParams);
        int page = Integer.parseInt(filters.getOrDefault("page", "0").toString());
        int size = Integer.parseInt(filters.getOrDefault("size", "10").toString());
        String sortField = filters.getOrDefault("sortField", "id").toString();
        String sortDir = filters.getOrDefault("sortDir", "asc").toString();

        filters.remove("page");
        filters.remove("size");
        filters.remove("sortField");
        filters.remove("sortDir");

        Map<String, Object> finalFilters = new HashMap<>();
        filters.forEach((key, v) -> {
            String value = v.toString();
            if (value.contains(",")) {
                String[] parts = value.split(",");
                if (parts.length == 2 && key.endsWith("__between")) {
                    Object start = parseDate(parts[0].trim());
                    Object end = parseDate(parts[1].trim());
                    finalFilters.put(key, List.of(start, end));
                } else {
                    finalFilters.put(key, Arrays.asList(parts));
                }
            } else {
                finalFilters.put(key, parseDate(value.trim()));
            }
        });

        return PaginationRequest.builder()
                .page(page)
                .size(size)
                .sortField(sortField)
                .sortDir(sortDir)
                .filters(finalFilters)
                .build();
    }

    private static Object parseDate(String str) {
        try {
            return LocalDate.parse(str, DATE_FORMAT);
        } catch (Exception e) {
            return str;
        }
    }
}
