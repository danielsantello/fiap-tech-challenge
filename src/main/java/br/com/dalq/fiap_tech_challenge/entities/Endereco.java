package br.com.dalq.fiap_tech_challenge.entities;

import br.com.dalq.fiap_tech_challenge.dtos.EnderecoRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Endereco {
    @Schema(description = "Identificador único do endereço")
    private Long id;
    @Schema(description = "ID do usuário")
    private Long usuarioId;
    @Schema(description = "Nome do logradouro do endereço")
    private String logradouro;
    @Schema(description = "Número do endereço")
    private String numero;
    @Schema(description = "Complemento do endereço")
    private String complemento;
    @Schema(description = "Bairro do endereço")
    private String bairro;
    @Schema(description = "Cidade do endereço")
    private String cidade;
    @Schema(description = "Estado do endereço")
    private String estado;
    @Schema(description = "CEP do endereço")
    private String cep;
    @Schema(description = "Data da última atualização do registro")
    private LocalDateTime ultima_alteracao;

    public Endereco(EnderecoRequestDTO enderecoRequestDTO) {
        this.usuarioId = enderecoRequestDTO.usuarioId();
        this.logradouro = enderecoRequestDTO.logradouro();
        this.numero = enderecoRequestDTO.numero();
        this.complemento = enderecoRequestDTO.complemento();
        this.bairro = enderecoRequestDTO.bairro();
        this.cidade = enderecoRequestDTO.cidade();
        this.estado = enderecoRequestDTO.estado();
        this.cep = enderecoRequestDTO.cep().replace("-", "");
        this.ultima_alteracao = LocalDateTime.now();
    }
}
