package br.com.dalq.fiap_tech_challenge.services;

import br.com.dalq.fiap_tech_challenge.dtos.UsuarioRequestDTO;
import br.com.dalq.fiap_tech_challenge.dtos.UsuarioSetPasswordDTO;
import br.com.dalq.fiap_tech_challenge.dtos.ValidationLoginDTO;
import br.com.dalq.fiap_tech_challenge.entities.TipoUsuario;
import br.com.dalq.fiap_tech_challenge.entities.Usuario;
import br.com.dalq.fiap_tech_challenge.repositories.UsuarioRepository;
import br.com.dalq.fiap_tech_challenge.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    public boolean isValidUserType(String tipo) {
        for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
            if (tipoUsuario.name().equalsIgnoreCase(tipo)) {
                return true;
            }
        }
        return false;
    }

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll(int page, int size) {
        int offset = (page - 1) * size;

        return this.usuarioRepository.findAll(size, offset);
    }

    public Optional<Usuario> findById(long id){
        return this.usuarioRepository.findById(id);
    }

    public void save(UsuarioRequestDTO usuarioRequestDTO) {
        var usuario = new Usuario();

        if (!isValidUserType(usuarioRequestDTO.tipo())) {
            throw new ResourceNotFoundException("Tipo de usuário inválido. Informar apenas RESTAURANTE ou CLIENTE");
        }

        usuario.setTipo(usuarioRequestDTO.tipo().toUpperCase());
        usuario.setNome(usuarioRequestDTO.nome());
        usuario.setEmail(usuarioRequestDTO.email());
        usuario.setLogin(usuarioRequestDTO.login());
        usuario.setUltima_alteracao(LocalDateTime.now());

        // Gera um hash da senha informada e armazena o hash no banco de dados
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(usuarioRequestDTO.senha().getBytes(StandardCharsets.UTF_8));
            usuario.setSenha(String.format("%064x", new BigInteger(1, hashBytes)));
        } catch (NoSuchAlgorithmException ignored) {

        }

        var save = this.usuarioRepository.save(usuario);
        Assert.state(save == 1, "Erro ao salvar usuário " + usuario.getNome());
    }

    public void update(UsuarioRequestDTO usuarioRequestDTO, Long id) {
        var usuario = new Usuario();

        if (!isValidUserType(usuarioRequestDTO.tipo())) {
            throw new ResourceNotFoundException("Tipo de usuário inválido. Informar apenas RESTAURANTE ou CLIENTE");
        }

        usuario.setTipo(usuarioRequestDTO.tipo().toUpperCase());
        usuario.setNome(usuarioRequestDTO.nome());
        usuario.setEmail(usuarioRequestDTO.email());
        usuario.setLogin(usuarioRequestDTO.login());
        usuario.setUltima_alteracao(LocalDateTime.now());

        var update = this.usuarioRepository.update(usuario, id);
        if (update == 0) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }

    public void setPassword(UsuarioSetPasswordDTO usuarioSetPasswordDTO, Long id) {
        if (!usuarioSetPasswordDTO.senhaNova().equals(usuarioSetPasswordDTO.senhaNovaConfirmacao())) {
            throw new ResourceNotFoundException("A nova senha e a senha de confirmação devem ser iguais");
        }

        Optional<Usuario> usuario = this.usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        String senhaAtual;
        String senhaNova;

        // Gera um hash da senha informada e armazena o hash no banco de dados
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(usuarioSetPasswordDTO.senhaAtual().getBytes(StandardCharsets.UTF_8));
            senhaAtual = String.format("%064x", new BigInteger(1, hashBytes));

            hashBytes = digest.digest(usuarioSetPasswordDTO.senhaNova().getBytes(StandardCharsets.UTF_8));
            senhaNova = String.format("%064x", new BigInteger(1, hashBytes));
        } catch (NoSuchAlgorithmException ignored) {
            throw new ResourceNotFoundException("Problemas ao gerar o hash da senha");
        }

        if (!usuario.get().getSenha().equals(senhaAtual)) {
            throw new ResourceNotFoundException("A senha atual não confere");
        }

        var update = this.usuarioRepository.setPassword(id, senhaNova);
        if (update == 0) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }

    public void delete(Long id) {
        var delete = this.usuarioRepository.delete(id);
        if (delete == 0) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }

    public void validateLogin(ValidationLoginDTO validationLoginDTO) {
        String login = validationLoginDTO.login();
        String senha;

        // Gera um hash da senha informada e armazena o hash no banco de dados
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(validationLoginDTO.senha().getBytes(StandardCharsets.UTF_8));
            senha = String.format("%064x", new BigInteger(1, hashBytes));
        } catch (NoSuchAlgorithmException ignored) {
            throw new ResourceNotFoundException("Problemas ao gerar o hash da senha");
        }

        Optional<Usuario> usuario = this.usuarioRepository.findByLoginSenha(login, senha);

        if (usuario.isEmpty()) {
            throw new ResourceNotFoundException("Usuário ou senha inválidos");
        }
    }
}
