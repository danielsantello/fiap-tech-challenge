package br.com.dalq.fiap_tech_challenge.dtos;

import br.com.dalq.fiap_tech_challenge.context.OnCreate;
import br.com.dalq.fiap_tech_challenge.context.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @Schema(description = "Tipo do usuário, podendo ser RESTAURANTE ou CLIENTE")
        @NotNull(message = "O tipo do usuário não pode ser nulo", groups = {OnCreate.class, OnUpdate.class})
        String tipo,

        @Schema(description = "Nome completo do usuário")
        @NotNull(message = "O nome do usuário não pode ser nulo", groups = {OnCreate.class, OnUpdate.class})
        String nome,

        @Schema(description = "E-mail do usuário")
        @NotNull(message = "O e-mail do usuário não pode ser nulo", groups = {OnCreate.class, OnUpdate .class})
        String email,

        @Schema(description = "Login utilizado para acesso ao sistema")
        @NotNull(message = "O login do usuário não pode ser nulo", groups = {OnCreate.class, OnUpdate .class})
        String login,

        @Schema(description = "Senha utilizada para acesso ao sistema")
        @NotNull(message = "A senha do usuário não pode ser nula", groups = {OnCreate.class})
        String senha
) {
}
