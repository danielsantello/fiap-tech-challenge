package br.com.dalq.fiap_tech_challenge.entities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import br.com.dalq.fiap_tech_challenge.dtos.UsuarioRequestDTO;
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
    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDateTime ultima_alteracao;

    public Usuario(UsuarioRequestDTO usuarioRequestDTO) {
        this.nome = usuarioRequestDTO.nome();
        this.email = usuarioRequestDTO.email();
        this.login = usuarioRequestDTO.login();
        this.ultima_alteracao = LocalDateTime.now();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(usuarioRequestDTO.senha().getBytes(StandardCharsets.UTF_8));
            this.senha = String.format("%064x", new BigInteger(1, hashBytes));
        } catch (NoSuchAlgorithmException ignored) {

        }
    }
}
