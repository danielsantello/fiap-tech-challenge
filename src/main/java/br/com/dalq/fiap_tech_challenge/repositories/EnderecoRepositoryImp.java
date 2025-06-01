package br.com.dalq.fiap_tech_challenge.repositories;

import br.com.dalq.fiap_tech_challenge.entities.Endereco;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnderecoRepositoryImp implements EnderecoRepository {

    private final JdbcClient jdbcClient;

    public EnderecoRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Endereco> findByUserId(long usuarioId) {
        return this.jdbcClient
                .sql("select * from enderecos where usuario_id = :usuario_id")
                .param("usuario_id", usuarioId)
                .query(Endereco.class)
                .list();
    }

    @Override
    public Optional<Endereco> findById(long id) {
        return this.jdbcClient
                .sql("select * from enderecos where id = :id")
                .param("id", id)
                .query(Endereco.class)
                .optional();
    }

    @Override
    public Integer save(Endereco endereco) {
        return this.jdbcClient
                .sql("insert into enderecos (usuario_id, logradouro, numero, complemento, bairro, cidade, estado, cep, ultima_alteracao)" +
                        "values (:usuario_id, :logradouro, :numero, :complemento, :bairro, :cidade, :estado, :cep, :ultima_alteracao)")
                .param("usuario_id", endereco.getUsuarioId())
                .param("logradouro", endereco.getLogradouro())
                .param("numero", endereco.getNumero())
                .param("complemento", endereco.getComplemento())
                .param("bairro", endereco.getBairro())
                .param("cidade", endereco.getCidade())
                .param("estado", endereco.getEstado())
                .param("cep", endereco.getCep())
                .param("ultima_alteracao", endereco.getUltima_alteracao())
                .update();
    }

    @Override
    public Integer update(Endereco endereco, Long id) {
        return this.jdbcClient
                .sql("update enderecos set logradouro = :logradouro, numero = :numero, complemento = :complemento, bairro = :bairro, cidade = :cidade, estado = :estado, cep = :cep, ultima_alteracao = :ultima_alteracao where id = :id")
                .param("logradouro", endereco.getLogradouro())
                .param("numero", endereco.getNumero())
                .param("complemento", endereco.getComplemento())
                .param("bairro", endereco.getBairro())
                .param("cidade", endereco.getCidade())
                .param("estado", endereco.getEstado())
                .param("cep", endereco.getCep())
                .param("ultima_alteracao", endereco.getUltima_alteracao())
                .param("id", id)
                .update();
    }

    @Override
    public Integer delete(Long id) {
        return this.jdbcClient
                .sql("delete from enderecos where id = :id")
                .param("id", id)
                .update();
    }
}
