package br.com.dalq.fiap_tech_challenge.repositories;

import br.com.dalq.fiap_tech_challenge.entities.Usuario;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImp implements UsuarioRepository {

    private final JdbcClient jdbcClient;

    public UsuarioRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Usuario> findAll(int size, int offset) {
        return this.jdbcClient
                .sql("select * from usuarios limit :size offset :offset")
                .param("size", size)
                .param("offset", offset)
                .query(Usuario.class)
                .list();
    }

    @Override
    public Optional<Usuario> findById(long id) {
        return this.jdbcClient
                .sql("select * from usuarios where id = :id")
                .param("id", id)
                .query(Usuario.class)
                .optional();
    }

    @Override
    public Integer save(Usuario usuario) {
        return this.jdbcClient
                .sql("insert into usuarios (nome, email, login, senha, ultima_alteracao) values (:nome, :email, :login, :senha, :ultima_alteracao)")
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("ultima_alteracao", usuario.getUltima_alteracao())
                .update();
    }

    @Override
    public Integer update(Usuario usuario, Long id) {
        return this.jdbcClient
                .sql("update usuarios set nome = :nome, email = :email, login = :login, senha = :senha, ultima_alteracao = :ultima_alteracao where id = :id")
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("ultima_alteracao", usuario.getUltima_alteracao())
                .param("id", id)
                .update();
    }

    @Override
    public Integer delete(Long id) {
        return this.jdbcClient
                .sql("delete from usuarios where id = :id")
                .param("id", id)
                .update();
    }
}
