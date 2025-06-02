package br.com.dalq.fiap_tech_challenge.repositories;

import br.com.dalq.fiap_tech_challenge.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    List<Usuario> findAll(int size, int offset);

    Optional<Usuario> findById(long id);

    Optional<Usuario> findByLoginSenha(String login, String senha);

    Integer save(Usuario usuario);

    Integer update(Usuario usuario, Long id);

    Integer delete(Long id);

    Integer setPassword(Long id, String password);
}
