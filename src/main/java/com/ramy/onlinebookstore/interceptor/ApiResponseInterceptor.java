package com.ramy.onlinebookstore.interceptor;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.ramy.onlinebookstore.annotation.ResponseSuccessMessage;
import com.ramy.onlinebookstore.dto.response.ApiResponse;

@ControllerAdvice
public class ApiResponseInterceptor implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(@Nullable Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {

        // Jika body sudah ApiResponse -> jangan di-wrap lagi
        if (body instanceof ApiResponse<?>) {
            return body;
        }

        // Ambil HTTP status real (default 200 kalau nggak ketemu)
        int status = HttpStatus.OK.value();
        if (response instanceof ServletServerHttpResponse servletResp) {
            status = servletResp.getServletResponse().getStatus();
        }

        // Jangan wrap untuk non-2xx (error/redirect dibiarkan apa adanya)
        if (status < 200 || status >= 300) {
            return body;
        }

        // Response Success Message decorator
        String customMessage = "OK";
        ResponseSuccessMessage annotation = returnType.getMethodAnnotation(ResponseSuccessMessage.class);
        if (annotation != null) {
            customMessage = annotation.value();
        }

        String deletedParam = request.getURI().getQuery();
        boolean deleted = false;
        if (deletedParam != null && deletedParam.contains("deleted=true")) {
            deleted = true;
        }

        if (body instanceof Page<?> page) {
            Map<String, Object> meta = Map.of(
                    "pageNumber", page.getNumber(),
                    "pageSize", page.getSize(),
                    "totalElements", page.getTotalElements(),
                    "totalPages", page.getTotalPages(),
                    "first", page.isFirst(),
                    "last", page.isLast(),
                    "numberOfElements", page.getNumberOfElements(),
                    "sort", page.getSort(),
                    "deleted", deleted
            );

            return ApiResponse.builder()
                    .success(true)
                    .message(customMessage)
                    .code(status)
                    .data(page.getContent())
                    .meta(meta)
                    .build();
        }

        return ApiResponse.builder()
                .success(true)
                .message(customMessage)
                .code(status)
                .data(body)
                .build();
    }
}
