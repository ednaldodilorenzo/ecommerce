package br.com.d2s.service.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record PostUserDto(
        @NotBlank(message = "NAME_NOT_BLANK")
        String name,

        @NotBlank(message = "EMAIL_NOT_BLANK")
        String email,

        @NotBlank(message = "PASSWORD_NOT_BLANK")
        String password,

        @NotBlank(message = "CPF_NOT_BLANK")
        String cpf
) {}
