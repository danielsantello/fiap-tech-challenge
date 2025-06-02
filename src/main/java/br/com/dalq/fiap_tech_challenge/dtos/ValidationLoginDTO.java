package br.com.dalq.fiap_tech_challenge.dtos;

import br.com.dalq.fiap_tech_challenge.context.OnCreate;
import br.com.dalq.fiap_tech_challenge.context.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ValidationLoginDTO(
        @Schema(description = "Login utilizado para acesso ao sistema")
        @NotNull(message = "O login do usuário não pode ser nulo")
        String login,

        @Schema(description = "Senha utilizada para acesso ao sistema")
        @NotNull(message = "A senha do usuário não pode ser nula")
        String senha
) {
}
