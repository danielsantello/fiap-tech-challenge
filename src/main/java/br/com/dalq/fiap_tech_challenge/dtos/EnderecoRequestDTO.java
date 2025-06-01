package br.com.dalq.fiap_tech_challenge.dtos;

import br.com.dalq.fiap_tech_challenge.context.OnCreate;
import br.com.dalq.fiap_tech_challenge.context.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequestDTO(
        @Schema(description = "ID do usuário")
        @NotNull(message = "O id do usuário não pode ser nulo", groups = OnCreate.class)
        Long usuarioId,

        @Schema(description = "Nome do logradouro do endereço")
        @NotNull(message = "O logradouro não pode ser nulo", groups = {OnCreate.class, OnUpdate .class})
        String logradouro,

        @Schema(description = "Número do endereço")
        @NotNull(message = "O número não pode ser nulo", groups = {OnCreate.class, OnUpdate .class})
        String numero,

        @Schema(description = "Complemento do endereço")
        String complemento,

        @Schema(description = "Bairro do endereço")
        @NotNull(message = "O bairro não pode ser nulo", groups = {OnCreate.class, OnUpdate .class})
        String bairro,

        @Schema(description = "Cidade do endereço")
        @NotNull(message = "A cidade não pode ser nula", groups = {OnCreate.class, OnUpdate .class})
        String cidade,

        @Schema(description = "Estado do endereço")
        @NotNull(message = "O estado não pode ser nulo", groups = {OnCreate.class, OnUpdate .class})
        String estado,

        @Schema(description = "CEP do endereço")
        @NotNull(message = "O cep não pode ser nulo", groups = {OnCreate.class, OnUpdate .class})
        String cep
) {
}
