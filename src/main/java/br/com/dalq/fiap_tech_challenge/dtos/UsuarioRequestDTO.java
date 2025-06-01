package br.com.dalq.fiap_tech_challenge.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @Schema(description = "Nome completo do usuário")
        @NotNull(message = "O nome do usuário não pode ser nulo")
        String nome,

        @Schema(description = "E-mail do usuário")
        @NotNull(message = "O e-mail do usuário não pode ser nulo")
        String email,

        @Schema(description = "Login utilizado para acesso ao sistema")
        @NotNull(message = "O login do usuário não pode ser nulo")
        String login,

        @Schema(description = "Senha utilizada para acesso ao sistema")
        @NotNull(message = "A senha do usuário não pode ser nula")
        String senha
) {
}
