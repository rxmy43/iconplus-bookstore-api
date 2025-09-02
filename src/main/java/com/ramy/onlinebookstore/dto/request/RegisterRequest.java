package com.ramy.onlinebookstore.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Register user information")
public class RegisterRequest {
    @NotBlank()
    @Schema(description = "User's full name", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank()
    @Email()
    @Schema(description = "User's email address", example = "johndoe123@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank()
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(description = "User's secure password", example = "johndoe1234", minLength = 8, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
