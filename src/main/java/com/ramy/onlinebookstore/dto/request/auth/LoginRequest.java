package com.ramy.onlinebookstore.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login credentials")
public class LoginRequest {
    @NotBlank()
    @Schema(description = "User's email address", example = "johndoe123@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank()
    @Schema(description = "User's password", example = "johndoe1234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
