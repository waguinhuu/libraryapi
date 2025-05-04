package io.github.waguinhuu.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "campo obrigatorio")
        String login,
        @NotBlank(message = "campo obrigatorio")
        @Email (message = "email invalido")
        String email,

        @NotBlank(message = "campo obrigatorio")
        String senha,
        List<String> roles) {
}
