package br.com.dalq.fiap_tech_challenge.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UsuarioSetPasswordDTO(
        @Schema(description = "Senha atual")
        @NotNull(message = "A senha atual não pode ser nula")
        String senhaAtual,

        @Schema(description = "Senha nova")
        @NotNull(message = "A senha nova não pode ser nula")
        String senhaNova,

        @Schema(description = "Senha de confirmação")
        @NotNull(message = "A senha de confirmação não pode ser nula")
        String senhaNovaConfirmacao
) {
}
