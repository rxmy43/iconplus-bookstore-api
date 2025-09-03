package com.ramy.onlinebookstore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private int code;
    private T data;
    private Map<String, Object> meta;
    private Map<String, String> fieldErrors;
    private String errors;
}
