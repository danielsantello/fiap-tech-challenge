package br.com.dalq.fiap_tech_challenge.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @Schema(description = "ID da pessoa que alugou o carro")
        @NotNull(message = "O id da pessoa não pode ser nulo")
        String nome,
        @NotNull(message = "O id do veículo não pode ser nulo")
        String email,
        String login,
        String senha
) {
}
