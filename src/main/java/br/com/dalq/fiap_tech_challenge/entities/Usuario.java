package br.com.dalq.fiap_tech_challenge.entities;

import java.time.LocalDateTime;

import br.com.dalq.fiap_tech_challenge.dtos.UsuarioRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Usuario {
    @Schema(description = "Identificador único do usuário")
    private Long id;
    @Schema(description = "Tipo do usuário")
    private String tipo;
    @Schema(description = "Nome completo do usuário")
    private String nome;
    @Schema(description = "E-mail do usuário")
    private String email;
    @Schema(description = "Login utilizado para acesso ao sistema")
    private String login;
    @Schema(description = "Senha utilizada para acesso ao sistema")
    private String senha;
    @Schema(description = "Data da última atualização do registro")
    private LocalDateTime ultima_alteracao;

    public Usuario(UsuarioRequestDTO usuarioRequestDTO) {
        this.tipo = usuarioRequestDTO.tipo();
        this.nome = usuarioRequestDTO.nome();
        this.email = usuarioRequestDTO.email();
        this.login = usuarioRequestDTO.login();
        this.ultima_alteracao = LocalDateTime.now();
    }
}
